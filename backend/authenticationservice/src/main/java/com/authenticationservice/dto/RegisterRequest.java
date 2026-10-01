package com.authenticationservice.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class RegisterRequest {

    @NonNull
    private String collegeEmail;
    @NonNull
    private String password;
    @NonNull
    private String studentId;
    @NonNull
    private String name;
    private Long collegeId;
    private String collegeName;
}
