package com.chatservice.dto;

import com.chatservice.enums.MessageType;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDto {
    private Long receiverId;
    private String content;
    private MessageType messageType;
}
