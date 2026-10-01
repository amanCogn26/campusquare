package com.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
    private Long id;
    private String username;
    private String collegeEmail;
    private String password;
    private String role;
}
