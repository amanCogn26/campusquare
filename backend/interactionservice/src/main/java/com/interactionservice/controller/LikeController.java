package com.interactionservice.controller;

import com.interactionservice.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@Slf4j
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<?> likePost(
            @PathVariable Long postId, @RequestHeader("X-User-Id") Long userId) {

        log.info("log from the like post controller");

        likeService.likePost(
                postId,
                userId
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> disLikePost(
            @PathVariable Long postId, @RequestHeader("X-User-Id") Long userId) {

        log.info("log from the dis like post");

        likeService.disLikePost(
                postId,
                userId
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deleteLikesByPostId(
            @PathVariable Long postId
    ){
//        likeService.deleteLikesByPostId(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('SERVICE')")
    public Boolean isLikedByUser(
            @RequestParam Long postId,
            @RequestParam Long userId) {

        return likeService.isLikedByUser(postId,userId);
    }
}
