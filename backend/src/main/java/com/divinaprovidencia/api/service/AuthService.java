package com.divinaprovidencia.api.service;

import com.divinaprovidencia.api.model.User;
import com.divinaprovidencia.api.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de manejar la lógica de registro
 * e inicio de sesión de los usuarios.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Verifica si un nombre de usuario ya está registrado.
     *
     * @param username nombre de usuario
     * @return true si el usuario existe
     */
    public boolean userExists(String username) {

        String normalizedUsername =
                username.trim();

        return userRepository.existsByUsername(
                normalizedUsername
        );
    }

    /**
     * Registra un nuevo usuario.
     *
     * La contraseña se almacena protegida mediante BCrypt.
     *
     * @param username nombre de usuario
     * @param password contraseña original
     * @return usuario registrado
     */
    public User register(
            String username,
            String password) {

        String normalizedUsername =
                username.trim();

        String passwordHash =
                passwordEncoder.encode(password);

        User user = new User(
                normalizedUsername,
                passwordHash
        );

        return userRepository.save(user);
    }

    /**
     * Valida las credenciales de un usuario.
     *
     * @param username nombre de usuario
     * @param password contraseña ingresada
     * @return true si las credenciales son correctas
     */
    public boolean authenticate(
            String username,
            String password) {

        String normalizedUsername =
                username.trim();

        Optional<User> userOptional =
                userRepository.findByUsername(
                        normalizedUsername
                );

        if (userOptional.isEmpty()) {
            return false;
        }

        User user =
                userOptional.get();

        return passwordEncoder.matches(
                password,
                user.getPasswordHash()
        );
    }
}