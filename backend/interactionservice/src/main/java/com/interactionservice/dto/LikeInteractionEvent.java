package com.interactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeInteractionEvent {
    private Long postId;
    private Long userId;
    private String action;
    private Long likeCount;
}