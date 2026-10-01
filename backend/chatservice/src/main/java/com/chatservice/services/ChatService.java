package com.chatservice.services;

import com.chatservice.dto.ChatMessageRequestDto;
import com.chatservice.dto.ChatMessageResponseDto;

import java.security.Principal;
import java.util.List;

public interface ChatService {

    void sendMessage(ChatMessageRequestDto chatMessageRequestDto, Long userId);

    List<ChatMessageResponseDto> getMessages(Long userId, Long receiverId);
}
