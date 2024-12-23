package com.example.events.repository;

import com.example.events.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para interactuar con la base de datos de notificaciones.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
