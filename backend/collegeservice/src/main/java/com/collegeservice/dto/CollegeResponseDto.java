package com.collegeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CollegeResponseDto {
    private Long id;
    private String collegeName;
    private String emailDomain;
    private String logo;
    private String city;
    private String state;
}
