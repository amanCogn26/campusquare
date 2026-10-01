package com.userservice.service;

import com.userservice.dto.*;

import java.util.List;

public interface UserService {

    UserAuthResponse findAuthUserByEmail(String email);

    LoginResponse findUserByEmail(String email);

    LoginResponse findUserById(Long id);

    RegisterResponse createUser(RegisterRequest registerRequest);

    List<UserDto> getAllUser();

    List<UserDto> getAllUserByCollegeId(Long userId,Long collegeId);

    void deleteUser(Long userId);

//    UserDto updateProfile(UserDto userDto);
}
