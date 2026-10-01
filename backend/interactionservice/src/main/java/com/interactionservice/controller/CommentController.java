package com.interactionservice.controller;

import com.interactionservice.dto.CommentRequestDto;
import com.interactionservice.dto.CommentResponseDto;
import com.interactionservice.service.CommentService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //create a comment
    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<CommentResponseDto>addComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @RequestHeader("X-User-Id") Long userId
    ){
        return ResponseEntity.ok(commentService.addComment(commentRequestDto,userId));
    }

    //delete the comment
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<Void>deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("X-User-Id")Long userId
    ){
        commentService.deleteComment(commentId,userId);
        return ResponseEntity.noContent().build();
    }


    //have to implement
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deleteCommentsByPostId(
            @PathVariable Long postId
    ){
//        commentService.deleteCommentsByPostId(postId);
        return ResponseEntity.noContent().build();
    }

    //    get all comment of a post
    @GetMapping("/{postId}")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<List<CommentResponseDto>>getCommentsByPostId(
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}
