package com.divinaprovidencia.api.dto;

/**
 * DTO utilizado para devolver mensajes
 * relacionados con autenticación.
 */
public class AuthResponse {

    private String message;

    public AuthResponse() {
    }

    public AuthResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}