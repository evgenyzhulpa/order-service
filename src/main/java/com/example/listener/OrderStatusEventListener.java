package com.example.listener;

import com.example.event.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatusEventListener {

    @KafkaListener(topics = "${app.kafka.kafkaMessageTopicToAccept}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listen(@Payload OrderStatusEvent message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        log.info("Received message: {}", message);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }
}
