package com.divinaprovidencia.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración encargada de proporcionar
 * el codificador de contraseñas BCrypt.
 */
@Configuration
public class PasswordConfig {

    /**
     * Crea el componente utilizado para proteger
     * y verificar las contraseñas de los usuarios.
     *
     * @return codificador BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}