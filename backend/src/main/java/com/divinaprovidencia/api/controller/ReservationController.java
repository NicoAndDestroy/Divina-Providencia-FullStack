package com.divinaprovidencia.api.controller;

import com.divinaprovidencia.api.dto.ReservationRequest;
import com.divinaprovidencia.api.model.Reservation;
import com.divinaprovidencia.api.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de exponer los servicios
 * relacionados con las reservas de Divina Providencia.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
            ReservationService reservationService) {

        this.reservationService = reservationService;
    }

    /**
     * Obtiene todas las reservas.
     *
     * Endpoint:
     * GET /api/reservations
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {

        return ResponseEntity.ok(
                reservationService.findAll()
        );
    }

    /**
     * Busca una reserva por su identificador.
     *
     * Endpoint:
     * GET /api/reservations/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findById(
            @PathVariable Long id) {

        return reservationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    /**
     * Busca reservas según su estado.
     *
     * Endpoint:
     * GET /api/reservations/status?value=CONFIRMED
     */
    @GetMapping("/status")
    public ResponseEntity<List<Reservation>> findByStatus(
            @RequestParam String value) {

        return ResponseEntity.ok(
                reservationService.findByStatus(value)
        );
    }

    /**
     * Busca reservas asociadas a un alojamiento.
     *
     * Endpoint:
     * GET /api/reservations/accommodation/{accommodationId}
     */
    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<List<Reservation>> findByAccommodationId(
            @PathVariable Long accommodationId) {

        return ResponseEntity.ok(
                reservationService.findByAccommodationId(accommodationId)
        );
    }

    /**
     * Crea una nueva reserva.
     *
     * Endpoint:
     * POST /api/reservations
     */
    @PostMapping
    public ResponseEntity<Reservation> create(
            @Valid @RequestBody ReservationRequest request) {

        return reservationService.create(request)
                .map(reservation ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(reservation)
                )
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    /**
     * Actualiza una reserva existente.
     *
     * Endpoint:
     * PUT /api/reservations/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest request) {

        return reservationService.update(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    /**
     * Elimina una reserva.
     *
     * Endpoint:
     * DELETE /api/reservations/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        boolean deleted =
                reservationService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}