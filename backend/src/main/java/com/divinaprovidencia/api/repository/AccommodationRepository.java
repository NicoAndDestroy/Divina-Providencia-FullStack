package com.divinaprovidencia.api.repository;

import com.divinaprovidencia.api.model.Accommodation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio encargado del acceso a datos
 * de los alojamientos.
 */
@Repository
public interface AccommodationRepository
        extends JpaRepository<Accommodation, Long> {

    /**
     * Obtiene los alojamientos según su estado
     * de disponibilidad.
     *
     * @param available estado de disponibilidad
     * @return lista de alojamientos
     */
    List<Accommodation> findByAvailable(Boolean available);
}