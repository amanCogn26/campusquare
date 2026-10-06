package com.notificationservice.service;

import com.notificationservice.dto.ConnectionRequestCreatedEvent;
import com.notificationservice.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    void createConnectionNotification(ConnectionRequestCreatedEvent event);

    void acceptConnectionNotification(ConnectionRequestCreatedEvent event);

    List<NotificationDto>getAllNotification(Long userId);

    void markAllAsRead(Long userId);
}
