package com.example.events.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

/**
 * Modelo de datos para las notificaciones.
 * Implementa Serializable para soportar la serialización en Kafka.
 */
@Entity
@Data
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String recipient;
    private String status; // Ejemplo: PENDING, PROCESSED
    private String timestamp;
}
