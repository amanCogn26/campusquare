package com.authenticationservice.service;

import com.authenticationservice.dto.LoginRequest;
import com.authenticationservice.dto.LoginResponse;
import com.authenticationservice.dto.RegisterRequest;
import com.authenticationservice.dto.RegisterResponse;

public interface AuthService{

    LoginResponse login(LoginRequest loginRequest);
    RegisterResponse register(RegisterRequest registerRequest);
}
