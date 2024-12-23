package com.example.events.controller;

import com.example.events.model.Notification;
import com.example.events.repository.NotificationRepository;
import com.example.events.service.NotificationProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationProducer producer;
    private final NotificationRepository repository;

    // Constructor con inyección de dependencias para desacoplar el controlador de los servicios
    public NotificationController(NotificationProducer producer, NotificationRepository repository) {
        this.producer = producer;
        this.repository = repository;
    }

    /**
     * Endpoint para crear una nueva notificación.
     * - Envía la notificación al topic de Kafka.
     * - La guarda en la base de datos con estado PENDING.
     *
     * @param notification el objeto Notification enviado en la solicitud.
     * @return ResponseEntity con mensaje de éxito.
     */
    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
        try {
            notification.setStatus("PENDING");
            producer.sendNotification(notification);
            repository.save(notification);
            return ResponseEntity.ok("Notification sent and saved as PENDING!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to create notification: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener todas las notificaciones guardadas en la base de datos.
     *
     * @return ResponseEntity con lista de notificaciones.
     */
    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notifications = repository.findAll();
        return ResponseEntity.ok(notifications);
    }
}
