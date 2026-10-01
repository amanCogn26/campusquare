package com.postservice.service;

import com.postservice.dto.PostRequestDto;
import com.postservice.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto postMethod(PostRequestDto postRequestDto,Long userId,Long collegeId);

    void deletePost(Long postId,Long userId);

    List<PostResponseDto>getAllPost();

    List<PostResponseDto>getAllPostByUserId(Long userId);

    PostResponseDto getPostByPostId(Long postId,Long collegeId);

    boolean exist(Long postId);

    List<PostResponseDto> getFeed(Long collegeId,Long userId);

    //like services
    void incrementLikeCount(Long postId);

    void decrementLikeCount(Long postId);

    //comment services
    void incrementCommentCount(Long postId);

    void decrementCommentCount(Long postId);
}

