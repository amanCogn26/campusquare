package com.collegeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CollegeRequestDto {
    private String collegeName;
    private String emailDomain;
    private String logo;
    private String city;
    private String state;
}
