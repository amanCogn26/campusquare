package com.chatservice.services.Implemantation;

import com.chatservice.dto.ChatMessageRequestDto;
import com.chatservice.dto.ChatMessageResponseDto;
import com.chatservice.entity.ChatMessage;
import com.chatservice.enums.MessageStatus;
import com.chatservice.feignClient.ConnectionClient;
import com.chatservice.repository.ChatRepository;
import com.chatservice.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConnectionClient connectionClient;

    public void sendMessage(ChatMessageRequestDto dto, Long senderId){

        log.info("This is send Message service imple");

        log.info("the user id is {}",senderId);

        if(!connectionClient.areConnected(senderId,dto.getReceiverId())){
            throw new AccessDeniedException(
                    "Users are not connected");
        }

        ChatMessage message =
                ChatMessage.builder()
                        .senderId(senderId)
                        .receiverId(dto.getReceiverId())
                        .content(dto.getContent())
                        .messageType(dto.getMessageType())
                        .messageStatus(MessageStatus.SENT)
                        .sentAt(LocalDateTime.now())
                        .build();

        ChatMessage savedMessage = chatRepository.save(message);

        ChatMessageResponseDto chatMessageResponseDto = mapToResponse(savedMessage);

        simpMessagingTemplate.convertAndSend(
                "/topic/chat/"+ chatMessageResponseDto.getReceiverId()
                ,chatMessageResponseDto
        );

        simpMessagingTemplate.convertAndSend(
                "/topic/chat/" + senderId,
                chatMessageResponseDto
        );
    }

    @Override
    public List<ChatMessageResponseDto> getMessages(Long userId, Long receiverId) {

        List<ChatMessage> chatMessages = chatRepository.getConversation(userId,receiverId);

        if(chatMessages.isEmpty()){
            return new ArrayList<>();
        }

        return chatMessages.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ChatMessageResponseDto mapToResponse(ChatMessage chatMessage){

        return ChatMessageResponseDto.builder()
                .id(chatMessage.getId())
                .content(chatMessage.getContent())
                .senderId(chatMessage.getSenderId())
                .receiverId(chatMessage.getReceiverId())
                .sentAt(chatMessage.getSentAt())
                .messageStatus(chatMessage.getMessageStatus())
                .messageType(chatMessage.getMessageType())
                .build();
    }
}
