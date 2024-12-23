package com.example.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase de respuesta para autenticación.
 * Contiene el token JWT generado.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}

