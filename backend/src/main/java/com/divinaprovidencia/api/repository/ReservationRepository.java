package com.divinaprovidencia.api.repository;

import com.divinaprovidencia.api.model.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio encargado del acceso a datos
 * de las reservas.
 */
@Repository
public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    /**
     * Busca las reservas asociadas a un alojamiento.
     *
     * @param accommodationId identificador del alojamiento
     * @return lista de reservas
     */
    List<Reservation> findByAccommodationId(Long accommodationId);

    /**
     * Busca reservas según su estado.
     *
     * @param status estado de la reserva
     * @return lista de reservas
     */
    List<Reservation> findByStatus(String status);
}