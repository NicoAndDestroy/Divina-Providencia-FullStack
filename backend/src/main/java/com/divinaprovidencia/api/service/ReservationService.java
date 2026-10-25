package com.divinaprovidencia.api.service;

import com.divinaprovidencia.api.dto.ReservationRequest;
import com.divinaprovidencia.api.model.Accommodation;
import com.divinaprovidencia.api.model.Reservation;
import com.divinaprovidencia.api.repository.AccommodationRepository;
import com.divinaprovidencia.api.repository.ReservationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de manejar la lógica de negocio
 * relacionada con las reservas de Divina Providencia.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            AccommodationRepository accommodationRepository) {

        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
    }

    /**
     * Obtiene todas las reservas.
     *
     * @return lista de reservas
     */
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /**
     * Busca una reserva por su identificador.
     *
     * @param id identificador de la reserva
     * @return reserva encontrada, si existe
     */
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    /**
     * Busca reservas según su estado.
     *
     * @param status estado de la reserva
     * @return lista de reservas
     */
    public List<Reservation> findByStatus(String status) {
        return reservationRepository.findByStatus(status);
    }

    /**
     * Busca reservas asociadas a un alojamiento.
     *
     * @param accommodationId identificador del alojamiento
     * @return lista de reservas
     */
    public List<Reservation> findByAccommodationId(Long accommodationId) {
        return reservationRepository.findByAccommodationId(accommodationId);
    }

    /**
     * Crea una nueva reserva.
     *
     * @param request datos de la reserva
     * @return reserva creada
     */
    public Optional<Reservation> create(ReservationRequest request) {

        Optional<Accommodation> accommodationOptional =
                accommodationRepository.findById(
                        request.getAccommodationId()
                );

        if (accommodationOptional.isEmpty()) {
            return Optional.empty();
        }

        Accommodation accommodation =
                accommodationOptional.get();

        Reservation reservation = new Reservation(
                request.getGuestName(),
                request.getGuestEmail(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getNumberOfGuests(),
                request.getStatus(),
                accommodation
        );

        return Optional.of(
                reservationRepository.save(reservation)
        );
    }

    /**
     * Actualiza una reserva existente.
     *
     * @param id identificador de la reserva
     * @param request nuevos datos
     * @return reserva actualizada, si existe
     */
    public Optional<Reservation> update(
            Long id,
            ReservationRequest request) {

        Optional<Reservation> reservationOptional =
                reservationRepository.findById(id);

        if (reservationOptional.isEmpty()) {
            return Optional.empty();
        }

        Optional<Accommodation> accommodationOptional =
                accommodationRepository.findById(
                        request.getAccommodationId()
                );

        if (accommodationOptional.isEmpty()) {
            return Optional.empty();
        }

        Reservation existingReservation =
                reservationOptional.get();

        existingReservation.setGuestName(
                request.getGuestName()
        );

        existingReservation.setGuestEmail(
                request.getGuestEmail()
        );

        existingReservation.setCheckIn(
                request.getCheckIn()
        );

        existingReservation.setCheckOut(
                request.getCheckOut()
        );

        existingReservation.setNumberOfGuests(
                request.getNumberOfGuests()
        );

        existingReservation.setStatus(
                request.getStatus()
        );

        existingReservation.setAccommodation(
                accommodationOptional.get()
        );

        return Optional.of(
                reservationRepository.save(existingReservation)
        );
    }

    /**
     * Elimina una reserva.
     *
     * @param id identificador de la reserva
     * @return true si fue eliminada; false si no existe
     */
    public boolean delete(Long id) {

        if (!reservationRepository.existsById(id)) {
            return false;
        }

        reservationRepository.deleteById(id);

        return true;
    }
}