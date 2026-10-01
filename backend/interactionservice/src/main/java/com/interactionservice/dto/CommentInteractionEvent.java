package com.interactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInteractionEvent {
    private CommentResponseDto commentResponseDto;
    private Long commentCount;
    private String action;
}
