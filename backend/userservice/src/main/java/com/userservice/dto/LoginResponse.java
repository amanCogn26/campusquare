package com.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long id;
    private String name;
    private String username;
    private String studentId;
    private String collegeEmail;
    private Long collegeId;
    private String collegeName;
    private String profileImage;
    private String role;

}
