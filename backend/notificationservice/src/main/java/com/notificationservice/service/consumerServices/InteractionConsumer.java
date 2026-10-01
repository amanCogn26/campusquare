//package com.notificationservice.service.consumerServices;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.notificationservice.dto.ConnectionRequestCreatedEvent;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class InteractionConsumer {
//
//    // Jackson library class.
//    // // ObjectMapper as a translator.
//    // Used for:
//    // Java Object <-> JSON
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @KafkaListener(
//            topics = "",
//            groupId = "interaction-group"
//    )
//
//    //runs only when the message is arrieved
//    public void consume(String json) throws Exception {
////    public void consume(ConnectionRequestCreatedEvent event) throws Exception {
//
////        ConnectionRequestCreatedEvent event =
////                objectMapper.readValue(
////                        json,
////                        ConnectionRequestCreatedEvent.class
////                );
//
////        System.out.println(event);
//
////        Notification notification = Notification.builder()
////                .userId(event.getReceiverId())
////                .message(event.getSenderName() + " sent you a connection request")
////                .build();
////
////        notificationRepository.save(notification);
//    }
//}
