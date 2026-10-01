package com.connectionservice.service;

import com.connectionservice.dto.ConnectionRequestCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionEventProducer {

    //kafkaTemplate send kafka messages
    private final KafkaTemplate<String , ConnectionRequestCreatedEvent> kafkaTemplate;

    public void publish(String topic,ConnectionRequestCreatedEvent event){
        kafkaTemplate.send(topic,event);
    }
}
