package com.postservice.controller;

import com.postservice.dto.PostRequestDto;
import com.postservice.dto.PostResponseDto;
import com.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<PostResponseDto>postMethod(
            @Valid @RequestBody PostRequestDto postRequestDto,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-College-Id") Long collegeId
            ){
        log.info("Create post controller");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.postMethod(postRequestDto,userId,collegeId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void>deletePost(
            @PathVariable Long postId,
            @RequestHeader("X-User-Id")Long userId
    ){

        postService.deletePost(postId,userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto>getPostByPostId(
            @PathVariable Long postId,
            @RequestHeader("X-College-Id")Long collegeId
            ){
        return ResponseEntity.ok(postService.getPostByPostId(postId,collegeId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PostResponseDto>>getAllPost(){
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<PostResponseDto>>getAllPostByUserId(
            @RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.ok(postService.getAllPostByUserId(userId));
    }

    @GetMapping("/exist/{postId}")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<Boolean>postExist(@PathVariable Long postId){

        log.info("post exist controller {}",postId);

        return ResponseEntity.ok(postService.exist(postId));
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponseDto>>getFeed(
            @RequestHeader("X-College-Id")Long collegeId,
            @RequestHeader("X-User-Id") Long userId
    ){
        log.info("get feed controller is called");
        return ResponseEntity.ok(postService.getFeed(collegeId,userId));
    }


    //like controllers

    @PutMapping("/{postId}/like/increment")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<Void>incrementLikeCount(@PathVariable Long postId){

        postService.incrementLikeCount(postId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/like/decrement")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<Void>decrementLikeCount(@PathVariable Long postId

    ){

        postService.decrementLikeCount(postId);

        return ResponseEntity.noContent().build();
    }

    //comment controllers

    @PutMapping("/{postId}/comment/increment")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<Void>incrementCommentCount(@PathVariable Long postId){

        postService.incrementCommentCount(postId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/comment/decrement")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<Void>decrementCommentCount(@PathVariable Long postId){

        postService.decrementCommentCount(postId);

        return ResponseEntity.noContent().build();
    }
}
