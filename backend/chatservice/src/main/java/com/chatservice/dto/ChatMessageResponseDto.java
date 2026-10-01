package com.chatservice.dto;

import com.chatservice.enums.MessageStatus;
import com.chatservice.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseDto {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private MessageType messageType;
    private MessageStatus messageStatus;
    private LocalDateTime sentAt;
}
