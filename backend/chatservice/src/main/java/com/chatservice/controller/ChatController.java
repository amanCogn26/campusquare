package com.chatservice.controller;

import com.chatservice.dto.ChatMessageResponseDto;
import com.chatservice.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{receiverId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getConversation(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long receiverId
    ){
        log.info("get message controller called by userId {}",userId);
        return ResponseEntity.ok(chatService.getMessages(userId,receiverId));
    }

//    public ResponseEntity<?> getUnreadCount(){
//        return ResponseEntity.ok();
//    }
}
