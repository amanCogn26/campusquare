package com.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;
    private Long actorId;  //who did
    private Long receiverId; //who receive
    private String message;
    private String type;
    private boolean isRead;
    private LocalDateTime createdAt;
}
