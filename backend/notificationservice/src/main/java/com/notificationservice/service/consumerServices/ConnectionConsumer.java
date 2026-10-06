package com.notificationservice.service.consumerServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.dto.ConnectionRequestCreatedEvent;
import com.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionConsumer {

    private final NotificationService notificationService;

    // Jackson library class.
    // // ObjectMapper as a translator.
    // Used for:
    // Java Object <-> JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "connection-request-created",
            groupId = "notification-group"
    )
    //runs only when the message is arrieved
    public void consumeConnectionCreated(String json) throws Exception {
//    public void consume(ConnectionRequestCreatedEvent event) throws Exception {

        ConnectionRequestCreatedEvent event =
                objectMapper.readValue(
                        json,
                        ConnectionRequestCreatedEvent.class
                );

        System.out.println(event);

        notificationService.createConnectionNotification(event);
    }

    @KafkaListener(
            topics = "connection-request-accept",
            groupId = "notification-group"
    )
    public void consumeConnectionAccepted(String json) throws Exception {

        ConnectionRequestCreatedEvent event = objectMapper.readValue(
                json,
                ConnectionRequestCreatedEvent.class
        );

        notificationService.acceptConnectionNotification(event);
    }
}
