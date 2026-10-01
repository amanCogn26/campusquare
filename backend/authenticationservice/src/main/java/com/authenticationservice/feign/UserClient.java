package com.authenticationservice.feign;

import com.authenticationservice.dto.LoginResponse;
import com.authenticationservice.dto.RegisterRequest;
import com.authenticationservice.dto.RegisterResponse;
import com.authenticationservice.dto.UserAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userservice")
public interface UserClient {

    @GetMapping("/user/auth/email/{email}")
    UserAuthResponse findAuthUserByEmail(@PathVariable String email);

    @GetMapping("/user/email/{email}")
    LoginResponse findUserByEmail(@PathVariable String email);

    @PostMapping("/user/register")
    RegisterResponse createUser(@RequestBody RegisterRequest registerRequest);
}
