package com.authenticationservice.controller;

import com.authenticationservice.dto.LoginRequest;
import com.authenticationservice.dto.LoginResponse;
import com.authenticationservice.dto.RegisterRequest;
import com.authenticationservice.dto.RegisterResponse;
import com.authenticationservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
            ){

        log.info("Received login request");

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest
            ){

        log.info("Received Register request");

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }


}
