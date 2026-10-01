package com.userservice.controller;

import com.userservice.dto.*;
import com.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/email/{email}")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<UserAuthResponse> findAuthUserByEmail(@PathVariable String email){

        log.info("enter the findAuthUserByEmail by {}",email);

        return ResponseEntity.ok(userService.findAuthUserByEmail(email));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public ResponseEntity<LoginResponse> findUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public ResponseEntity<LoginResponse> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<RegisterResponse> createUser(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.createUser(registerRequest));
    }

    @GetMapping("/alluser")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUser(){

        return ResponseEntity.ok(userService.getAllUser());

    }
    @GetMapping("/collegeid")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUserByCollegeId(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-College-Id") Long collegeId){

        log.info("get All User By CollegeId controller");

        return ResponseEntity.ok(userService.getAllUserByCollegeId(userId,collegeId));

    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> getAllUserByCollegeId(
            @PathVariable Long userId)
    {

        log.info("delete controller of user for user id {}",userId);

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();

    }


//    @PutMapping("/updateprofile/{id}")
//    public ResponseEntity<UserDto> updateProfile(
//            @PathVariable Long id,
//            @RequestBody UserDto userDto
//    ){
//        return ResponseEntity.ok(userService.updateProfile(userDto));
//    }
}
