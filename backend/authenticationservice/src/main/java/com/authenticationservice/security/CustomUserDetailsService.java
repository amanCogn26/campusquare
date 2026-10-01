package com.authenticationservice.security;

import com.authenticationservice.dto.UserAuthResponse;
import com.authenticationservice.feign.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("enter the loaduserbyusername");

        try {
            UserAuthResponse user = userClient.findAuthUserByEmail(email);

            if (user == null) {

                log.info("user not found with email {}",email);

                throw new UsernameNotFoundException("User not found with email: " + email);
            }

            log.info("role from UserAuthResponse {}",user.getRole());

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getCollegeEmail())
                    .password(user.getPassword())
                    .authorities(List.of(authority))
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error communicating with User Service", e);
        }
    }
}
