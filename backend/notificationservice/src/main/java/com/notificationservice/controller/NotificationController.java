package com.notificationservice.controller;

import com.notificationservice.dto.NotificationDto;
import com.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDto>>getAllNotification(
           @RequestHeader("X-User-Id") Long userId){

        log.info("Notification controller get all notification method called by userid {}"
                ,userId);

        return ResponseEntity.ok(notificationService.getAllNotification(userId));
    }

    @PutMapping("/mark-all-read")
    public ResponseEntity<Void>markAllAsRead(
            @RequestHeader("X-User-Id") Long userId){

        notificationService.markAllAsRead(userId);

        return ResponseEntity.noContent().build();
    }
}
