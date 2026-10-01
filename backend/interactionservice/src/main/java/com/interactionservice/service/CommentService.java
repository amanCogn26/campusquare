package com.interactionservice.service;

import com.interactionservice.dto.CommentRequestDto;
import com.interactionservice.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto addComment(CommentRequestDto commentRequestDto, Long userId);
    void deleteComment(Long commentId,Long userId);

    List<CommentResponseDto>getCommentsByPostId(Long postId);
}
