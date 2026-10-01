package com.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
    private Long id;
    private String username;
    private String collegeEmail;
    private String password;
    private String role;
//    private Long collegeId;
}
