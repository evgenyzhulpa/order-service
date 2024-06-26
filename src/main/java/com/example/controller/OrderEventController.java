package com.example.controller;

import com.example.event.OrderEvent;
import com.example.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class OrderEventController {

    @Value("${app.kafka.kafkaMessageTopicToSend}")
    private String topicName;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Order order) {
        kafkaTemplate.send(topicName, new OrderEvent(order.getProduct(), order.getQuantity()));
        return ResponseEntity.ok("The message sent to kafka.");
    }
}
