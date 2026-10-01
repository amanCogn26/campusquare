package com.postservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "interactionservice")
public interface InteractionClient {

    @GetMapping("/like/status")
    Boolean isLikedByUser(@RequestParam Long postId, @RequestParam Long userId);

    @DeleteMapping("/like/delete/{postId}")
    void deleteLikesByPostId(@PathVariable Long postId);

    @DeleteMapping("/comment/delete/{postId}")
    void deleteCommentsByPostId(@PathVariable Long postId);
}
