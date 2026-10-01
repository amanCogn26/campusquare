package com.collegeservice.controller;

import com.collegeservice.dto.CollegeResponseDto;
import com.collegeservice.dto.CollegeRequestDto;
import com.collegeservice.service.CollegeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/colleges")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SERVICE')")
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping("")
    public ResponseEntity<List<CollegeResponseDto>>getAllColleges(){
        return ResponseEntity.ok(collegeService.getAllColleges());
    }

    @GetMapping("/{collegeName}")
    public ResponseEntity<CollegeResponseDto>getCollege(@PathVariable String collegeName){
        return ResponseEntity.ok(collegeService.getCollege(collegeName));
    }

    @GetMapping("/domain/{domain}")
    public ResponseEntity<CollegeResponseDto>getCollegeByDomain(@PathVariable String domain){
        return ResponseEntity.ok(collegeService.getCollegeByDomain(domain));
    }

    @PostMapping("/create")
    public ResponseEntity<CollegeResponseDto>createCollege(
            @Valid @RequestBody CollegeRequestDto collegeRequestDto
    ){
        return ResponseEntity.ok(collegeService.createCollege(collegeRequestDto));
    }

    @DeleteMapping("/{collegeId}")
    public ResponseEntity<Void>deleteCollege(
            @PathVariable Long collegeId
    ){
        collegeService.deleteCollege(collegeId);

        return ResponseEntity.noContent().build();
    }
}
