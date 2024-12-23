package com.example.authentication.service;

import com.example.authentication.entity.User;
import com.example.authentication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para manejar la lógica de negocio relacionada con usuarios.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario después de validar los datos.
     * @param user Objeto User con los datos del usuario.
     * @return Usuario registrado.
     * @throws IllegalArgumentException Si el username ya está registrado o los datos son inválidos.
     */
    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encripta la contraseña.
        return userRepository.save(user);
    }

    /**
     * Valida si una contraseña ingresada coincide con la almacenada.
     * @param user Objeto User con la contraseña encriptada.
     * @param rawPassword Contraseña ingresada por el usuario.
     * @return true si la contraseña coincide, false en caso contrario.
     */
    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     * @return Optional con el usuario si existe.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}


