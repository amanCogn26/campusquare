package com.authenticationservice.service.implementation;

import com.authenticationservice.dto.*;
import com.authenticationservice.exception.ResourceNotFoundException;
import com.authenticationservice.feign.CollegeClient;
import com.authenticationservice.feign.UserClient;
import com.authenticationservice.security.JwtProvider;
import com.authenticationservice.service.AuthService;
import feign.FeignException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CollegeClient collegeClient;
    private final AuthenticationManager authenticationManager;
    private final UserClient userClient;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest){

        log.info("Login attempt for email: {}",loginRequest.getCollegeEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCollegeEmail(), loginRequest.getPassword())
        );


        if(!authentication.isAuthenticated()){
            throw new BadCredentialsException("Invalid Credentials");
        }

        log.info("User is authenticate");

        LoginResponse loginResponse=userClient.findUserByEmail(loginRequest.getCollegeEmail());

        String token = jwtProvider.generateToken(
                loginResponse.getId(),
                loginResponse.getCollegeId(),
                loginResponse.getRole(),
                loginResponse.getCollegeEmail()
        );

        log.info("Token is generated {}",token);

        loginResponse.setToken(token);

        return loginResponse;
    }

    public RegisterResponse register(RegisterRequest registerRequest){

        log.info("Register attempt for email {}",registerRequest.getCollegeEmail());

        String emailTrimmed=registerRequest.getCollegeEmail().trim()
                .substring(registerRequest.getCollegeEmail().indexOf("@")+1);

        log.info("Email Domain is {}",emailTrimmed);

        CollegeDto collegeDto;

        try{

            collegeDto=collegeClient.getCollegeByDomain(emailTrimmed);

        }catch (FeignException.NotFound ex){

            log.warn("Invalid College Domain {}",emailTrimmed);

            throw new ResourceNotFoundException("College not found for email: "+registerRequest.getCollegeEmail());
        }

        log.info("college is found {}",collegeDto.getCollegeName());

        registerRequest.setCollegeId(collegeDto.getId());
        registerRequest.setCollegeName(collegeDto.getCollegeName());

        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        log.info("password is hashed");

        try {
            return userClient.createUser(registerRequest);
        }
        catch (FeignException ex) {
            throw new ResourceNotFoundException(ex.contentUTF8());
        }
    }
}
