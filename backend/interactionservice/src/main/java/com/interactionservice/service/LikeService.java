package com.interactionservice.service;

public interface LikeService {
    void likePost(Long postId,Long userId);
    void disLikePost(Long postId,Long userId);

    Boolean isLikedByUser(Long postId,Long userId);
}
