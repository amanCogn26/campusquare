package com.interactionservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "postservice")
public interface PostClient {

    @GetMapping("/post/exist/{postId}")
    boolean postExist(@PathVariable("postId") Long postId);

    @PutMapping("/post/{postId}/like/increment")
    void incrementLikeCount(@PathVariable("postId") Long postId);

    @PutMapping("/post/{postId}/like/decrement")
    void decrementLikeCount(@PathVariable("postId") Long postId);

    @PutMapping("/post/{postId}/comment/increment")
    void incrementCommentCount(@PathVariable("postId") Long postId);

    @PutMapping("/post/{postId}/comment/decrement")
    void decrementCommentCount(@PathVariable("postId") Long postId);
}
