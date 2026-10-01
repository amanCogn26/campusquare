package com.userservice.dto;

import com.userservice.enums.Gender;
import com.userservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String name;

    private String collegeEmail;

    private String personalEmail;

    private String phone;

    private String profileImage;

    private Gender gender;

    private Role role;

    private Long collegeId;

    private String studentId;

    private String collegeName;

    private Integer graduationYear;

    private Date createdAt;
}
