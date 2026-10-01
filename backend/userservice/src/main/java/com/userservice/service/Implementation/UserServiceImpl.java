package com.userservice.service.Implementation;

import com.userservice.dto.*;
import com.userservice.entity.User;
import com.userservice.enums.Role;
import com.userservice.exception.DuplicateResourceException;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserAuthResponse findAuthUserByEmail(String email){

        log.info("find auth user by email found");

        User user=userRepository.findByCollegeEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found with email: "+email));

        return UserAuthResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .collegeEmail(user.getCollegeEmail())
//                .collegeId(user.getCollegeId())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
    }

    public LoginResponse findUserByEmail(String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .studentId(user.getStudentId())
                .collegeEmail(user.getCollegeEmail())
                .collegeName(user.getCollegeName())
                .collegeId(user.getCollegeId())
                .profileImage(user.getProfileImage())
                .role(user.getRole().toString())
                .build();
    }

    public LoginResponse findUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + id));

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .studentId(user.getStudentId())
                .collegeEmail(user.getCollegeEmail())
                .collegeName(user.getCollegeName())
                .collegeId(user.getCollegeId())
                .profileImage(user.getProfileImage())
                .role(user.getRole().toString())
                .build();
    }

    public RegisterResponse createUser(RegisterRequest registerRequest){

        if(userRepository.existsByCollegeEmail(registerRequest.getCollegeEmail())){
            log.info("Email already exists");
            throw new DuplicateResourceException("Email already exist: "+registerRequest.getCollegeEmail());
        }

        User user=User.builder()
                .collegeEmail(registerRequest.getCollegeEmail())
                .collegeId(registerRequest.getCollegeId())
                .studentId(registerRequest.getStudentId())
                .password(registerRequest.getPassword())
                .collegeName(registerRequest.getCollegeName())
                .name(registerRequest.getName())
                .build();

        log.info("Creating user with email: {}",
                registerRequest.getCollegeEmail());

        if(user.getRole()==null){
            user.setRole(Role.STUDENT);
        }

        user=userRepository.save(user);

        return RegisterResponse.builder()
                .name(user.getName())
                .collegeEmail(user.getCollegeEmail())
                .studentId(user.getStudentId())
                .build();
    }

    public List<UserDto> getAllUser(){

        log.info("Getting all the User list");

        return userRepository.findAll()
                .stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .name(user.getName())
                        .collegeEmail(user.getCollegeEmail())
                        .personalEmail(user.getPersonalEmail())
                        .phone(user.getPhone())
                        .profileImage(user.getProfileImage())
                        .gender(user.getGender())
                        .collegeId(user.getCollegeId())
                        .role(user.getRole())
                        .studentId(user.getStudentId())
                        .collegeName(user.getCollegeName())
                        .graduationYear(user.getGraduationYear())
                        .createdAt(user.getCreatedAt())
                        .build())
                .toList();
    }

    public List<UserDto> getAllUserByCollegeId(Long userId,Long collegeId){

        log.info("Getting all the User list by college id {}",collegeId);

        List<User> users = userRepository.findAllByCollegeId(collegeId);

        log.info("found list of users from same college");

        return users.stream()
                .filter(user -> !user.getId().equals(userId))
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .username(user.getUsername())
                        .collegeEmail(user.getCollegeEmail())
                        .personalEmail(user.getPersonalEmail())
                        .phone(user.getPhone())
                        .profileImage(user.getProfileImage())
                        .gender(user.getGender())
                        .role(user.getRole())
                        .collegeId(user.getCollegeId())
                        .studentId(user.getStudentId())
                        .collegeName(user.getCollegeName())
                        .graduationYear(user.getGraduationYear())
                        .createdAt(user.getCreatedAt())
                        .build())
                .toList();
        }

    public void deleteUser(Long userId){

        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User is not Exist");
        }

        userRepository.deleteById(userId);
    }

//    public UserDto updateProfile(UserDto userDto){
//
//        if(!userRepository.existsByCollegeEmail(userDto.getCollegeEmail())){
//
//            log.info("Email doest not exists {}",userDto.getCollegeEmail());
//
//            throw new ResourceNotFoundException("Email doest not exist: "+userDto.getCollegeEmail());
//        }
//
//
//    }
}
