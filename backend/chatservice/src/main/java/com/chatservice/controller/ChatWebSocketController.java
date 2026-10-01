package com.chatservice.controller;

import com.chatservice.dto.ChatMessageRequestDto;
import com.chatservice.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat.send")
    public  void sendMessage(ChatMessageRequestDto chatMessageRequestDto, SimpMessageHeaderAccessor headerAccessor){
        log.info("websocket controller");

        Long userId =
                (Long) Objects.requireNonNull(headerAccessor
                                .getSessionAttributes())
                        .get("userId");

        log.info("Sender Id {}", userId);

        chatService.sendMessage(chatMessageRequestDto,userId);
    }
}
