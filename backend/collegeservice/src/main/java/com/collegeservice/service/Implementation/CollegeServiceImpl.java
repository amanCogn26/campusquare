package com.collegeservice.service.Implementation;

import com.collegeservice.dto.CollegeResponseDto;
import com.collegeservice.dto.CollegeRequestDto;
import com.collegeservice.entity.College;
import com.collegeservice.exception.DuplicateResourceException;
import com.collegeservice.exception.ResourceNotFoundException;
import com.collegeservice.repository.CollegeRepository;
import com.collegeservice.service.CollegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeResponseDto getCollege(String collegeName) {

        log.info("Get college service is called by {}", collegeName);

        College college=collegeRepository.findByCollegeName(collegeName)
                .orElseThrow(()->new ResourceNotFoundException("College not found with college Name: "+collegeName));

        log.info("College is exist {}",collegeName);

        return mapToFunction(college);
    }

    public CollegeResponseDto getCollegeByDomain(String domain){

        log.info("Get college by domain {}",domain);

        College college=collegeRepository.findByEmailDomain(domain)
                .orElseThrow(()->new ResourceNotFoundException("College is not found with the email Domain: "+domain));

        log.info("College is exist by domain {}",domain);

        return mapToFunction(college);
    }

    public CollegeResponseDto createCollege(CollegeRequestDto collegeRequestDto){

        log.info("Enter the createCollege service {}",collegeRequestDto.getCollegeName());

        if (collegeRepository.findByCollegeName(collegeRequestDto.getCollegeName()).isPresent()) {

            log.warn("College already exist {}",collegeRequestDto.getCollegeName());

            throw new DuplicateResourceException("College already exists: " + collegeRequestDto.getCollegeName());
        }

        College college1=College.builder()
                .collegeName(collegeRequestDto.getCollegeName())
                .emailDomain(collegeRequestDto.getEmailDomain())
                .city(collegeRequestDto.getCity())
                .state(collegeRequestDto.getState())
                .logo(collegeRequestDto.getLogo())
                .build();

        College savedCollege = collegeRepository.save(college1);

        return mapToFunction(savedCollege);
    }

    public List<CollegeResponseDto> getAllColleges() {

        log.info("Get all colleges service is called");

        List<College> colleges=collegeRepository.findAll();

        if (colleges.isEmpty()) {

            log.warn("College list is empty");

            throw new ResourceNotFoundException("No colleges found");
        }

        return colleges.stream()
                .map(college -> CollegeResponseDto.builder()
                        .id(college.getId())
                        .collegeName(college.getCollegeName())
                        .emailDomain(college.getEmailDomain())
                        .city(college.getCity())
                        .state(college.getState())
                        .logo(college.getLogo())
                        .build())
                .toList();
    }

    public CollegeResponseDto mapToFunction(College college){
        return CollegeResponseDto.builder()
                .id(college.getId())
                .emailDomain(college.getEmailDomain())
                .collegeName(college.getCollegeName())
                .logo(college.getLogo())
                .state(college.getState())
                .city(college.getCity())
                .build();
    }

    public void deleteCollege(Long collegeId){
        collegeRepository.deleteById(collegeId);
    }
}
