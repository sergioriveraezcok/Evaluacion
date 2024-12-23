package com.example.authentication.controller;

import com.example.authentication.entity.User;
import com.example.authentication.service.UserService;
import com.example.authentication.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador para manejar las operaciones de autenticación y registro de usuarios.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param user Objeto User con los datos del usuario.
     * @return Respuesta con los datos del usuario registrado o un error en caso de fallo.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Registra al usuario utilizando el servicio.
            User savedUser = userService.register(user);
            return ResponseEntity.ok(Map.of(
                    "id", savedUser.getId(),
                    "username", savedUser.getUsername(),
                    "roles", savedUser.getRoles()
            ));
        } catch (IllegalArgumentException e) {
            // Responde con un error si el nombre de usuario ya está en uso o los datos son inválidos.
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Responde con un error genérico para cualquier otro fallo.
            return ResponseEntity.badRequest().body(Map.of("error", "El registro de usuario falló"));
        }
    }

    /**
     * Inicia sesión y genera un token JWT.
     * @param loginRequest Mapa con las credenciales (username y password).
     * @return Token JWT si las credenciales son válidas o un error en caso de fallo.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        return userService.findByUsername(username)
                .filter(user -> userService.checkPassword(user, password))
                .map(user -> {
                    // Genera un token JWT si las credenciales son válidas.
                    String token = jwtUtils.generateToken(username);
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Usuario o contraseña inválidos")));
    }

    /**
     * Ruta protegida accesible solo para usuarios con rol ADMIN.
     * @return Mensaje de bienvenida al dashboard de admin.
     */
    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> adminDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "¡Bienvenido al Dashboard de Admin!"
        ));
    }

    /**
     * Ruta protegida accesible para usuarios con rol USER o ADMIN.
     * @return Mensaje de bienvenida al dashboard de usuario.
     */
    @GetMapping("/user/dashboard")
    public ResponseEntity<?> userDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "¡Bienvenido al Dashboard de Usuario!"
        ));
    }
}
