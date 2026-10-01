package com.connectionservice.feignClient;

import com.connectionservice.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice")
public interface UserClient {

    @GetMapping("/user/{id}")
    LoginResponse findUserById(@PathVariable Long id);
}
