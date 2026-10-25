package com.divinaprovidencia.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para recibir las credenciales
 * de registro e inicio de sesión.
 */
public class AuthRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    @Size(
        min = 3,
        max = 100,
        message = "El nombre de usuario debe tener entre 3 y 100 caracteres."
    )
    private String username;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(
        min = 6,
        max = 100,
        message = "La contraseña debe tener entre 6 y 100 caracteres."
    )
    private String password;

    public AuthRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}