package com.collegeservice.service;

import com.collegeservice.dto.CollegeResponseDto;
import com.collegeservice.dto.CollegeRequestDto;

import java.util.List;

public interface CollegeService {

    CollegeResponseDto getCollege(String collegeName);

    List<CollegeResponseDto> getAllColleges();

    CollegeResponseDto getCollegeByDomain(String domain);

    CollegeResponseDto createCollege(CollegeRequestDto collegeRequestDto);

    void deleteCollege(Long collegeId);
}
