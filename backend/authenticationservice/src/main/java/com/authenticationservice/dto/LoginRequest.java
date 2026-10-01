package com.authenticationservice.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequest {

    @NonNull
    private String collegeEmail;
    @NonNull
    private String password;
}
