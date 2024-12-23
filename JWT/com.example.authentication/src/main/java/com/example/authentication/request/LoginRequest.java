package com.example.authentication.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Clase que representa la solicitud de login.
 * Contiene las credenciales enviadas por el cliente.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio.") // Validación para evitar valores vacíos.
    private String username;

    @NotBlank(message = "La contraseña es obligatoria.") // Validación para evitar valores vacíos.
    private String password;
}

