package com.example.events.service;

import com.example.events.model.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Servicio para enviar notificaciones al topic de Kafka.
 */
@Service
public class NotificationProducer {

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    // Constructor con inyección de dependencias
    public NotificationProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Envía una notificación al topic de Kafka.
     *
     * @param notification el objeto Notification que será enviado.
     */
    public void sendNotification(Notification notification) {
        kafkaTemplate.send(topicName, notification);
    }
}
