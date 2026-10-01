package com.authenticationservice.feign;

import com.authenticationservice.dto.CollegeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "collegeservice")
public interface CollegeClient {

    @GetMapping("/colleges/domain/{domain}")
    CollegeDto getCollegeByDomain(@PathVariable String domain);
}
