package com.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollegeDto {
    private Long id;
    private String collegeName;
    private String emailDomain;
    private String logo;
    private String city;
    private String state;
}
