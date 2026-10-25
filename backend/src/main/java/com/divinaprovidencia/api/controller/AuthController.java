package com.divinaprovidencia.api.controller;

import com.divinaprovidencia.api.dto.AuthRequest;
import com.divinaprovidencia.api.dto.AuthResponse;
import com.divinaprovidencia.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado del registro
 * e inicio de sesión de usuarios.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registra un nuevo usuario.
     *
     * Endpoint:
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AuthRequest request) {

        String username =
                request.getUsername().trim();

        if (authService.userExists(username)) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            new AuthResponse(
                                    "El usuario ya se encuentra registrado."
                            )
                    );
        }

        authService.register(
                username,
                request.getPassword()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new AuthResponse(
                                "Usuario registrado correctamente."
                        )
                );
    }

    /**
     * Valida las credenciales de un usuario.
     *
     * Endpoint:
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {

        boolean authenticated =
                authService.authenticate(
                        request.getUsername(),
                        request.getPassword()
                );

        if (authenticated) {

            return ResponseEntity.ok(
                    new AuthResponse(
                            "Autenticación satisfactoria."
                    )
            );
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new AuthResponse(
                                "Error en la autenticación."
                        )
                );
    }
}