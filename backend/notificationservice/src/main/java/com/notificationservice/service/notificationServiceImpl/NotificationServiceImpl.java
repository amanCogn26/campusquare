package com.notificationservice.service.notificationServiceImpl;

import com.notificationservice.dto.ConnectionRequestCreatedEvent;
import com.notificationservice.dto.NotificationDto;
import com.notificationservice.entity.Notification;
import com.notificationservice.repository.NotificationRepository;
import com.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void createConnectionNotification(ConnectionRequestCreatedEvent event){

        Notification notification = Notification.builder()
                .actorId(event.getSenderId())
                .receiverId(event.getReceiverId())
                .message(event.getSenderName()
                        + " sent you a connection request")
                .type("CONNECTION_REQUEST")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        log.info(
                "SENDING TO {}",
                "/topic/notification/" + event.getReceiverId()
        );

        simpMessagingTemplate.convertAndSend(
                "/topic/notification/" + event.getReceiverId(),
                notification
        );
    }

    public void acceptConnectionNotification(ConnectionRequestCreatedEvent event){

        Notification notification = Notification.builder()
                .actorId(event.getSenderId())
                .receiverId(event.getReceiverId())
                .message(event.getSenderName()
                        + " accepted your connection request")
                .type("CONNECTION_ACCEPT")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notification = notificationRepository.save(notification);

        simpMessagingTemplate.convertAndSend(
                "/topic/notification/" + event.getReceiverId(),
                notification
        );
    }

    @Override
    public List<NotificationDto>getAllNotification(Long userId) {

        List<Notification> notifications = notificationRepository.findAllByReceiverId(userId);

        if (notifications.isEmpty()) {
            return new ArrayList<>();
        }

        return notifications
                .stream()
                .map(notification -> NotificationDto
                        .builder()
                        .id(notification.getId())
                        .actorId(notification.getActorId())
                        .receiverId(notification.getReceiverId())
                        .message(notification.getMessage())
                        .type(notification.getType())
                        .createdAt(notification.getCreatedAt())
                        .isRead(notification.isRead())
                        .build()
                ).toList();
    }

    @Override
    public void markAllAsRead(Long userId) {

        List<Notification> notifications =
                notificationRepository.findAllByReceiverId(userId);

        notifications.forEach(notification ->
                notification.setRead(true));

        notificationRepository.saveAll(notifications);
    }

//    public Long countUnreadNotification(Long userId) {
//
//        List<Notification> notifications =
//                notificationRepository.findAllByReceiverId(userId);
//
//        notifications.forEach(notification ->
//                notification.setRead(true));
//
//        notificationRepository.saveAll(notifications);
//
//        return 5;
//    }
}
