package com.divinaprovidencia.api.repository;

import com.divinaprovidencia.api.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio encargado del acceso a datos
 * de los usuarios registrados.
 */
@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre.
     *
     * @param username nombre de usuario
     * @return usuario encontrado, si existe
     */
    Optional<User> findByUsername(String username);

    /**
     * Verifica si un nombre de usuario ya existe.
     *
     * @param username nombre de usuario
     * @return true si ya está registrado
     */
    boolean existsByUsername(String username);
}