package com.interactionservice.feignClient;

import com.interactionservice.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name ="userservice")
public interface UserClient {

    @GetMapping("/user/{id}")
    LoginResponse findUserById(@PathVariable Long id);
}
