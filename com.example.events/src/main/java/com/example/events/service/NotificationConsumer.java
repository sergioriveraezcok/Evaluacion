package com.example.events.service;

import com.example.events.model.Notification;
import com.example.events.repository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Servicio para procesar las notificaciones recibidas desde Kafka.
 */
@Service
public class NotificationConsumer {

    private final NotificationRepository repository;

    // Constructor con inyección de dependencias
    public NotificationConsumer(NotificationRepository repository) {
        this.repository = repository;
    }

    /**
     * Escucha los mensajes del topic de Kafka y los procesa.
     * - Cambia el estado de la notificación a PROCESSED.
     * - Actualiza el timestamp.
     * - Guarda los cambios en la base de datos.
     *
     * @param notification el mensaje recibido de Kafka.
     */
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void processNotification(Notification notification) {
        notification.setStatus("PROCESSED");
        notification.setTimestamp(String.valueOf(System.currentTimeMillis()));
        repository.save(notification);
    }
}
