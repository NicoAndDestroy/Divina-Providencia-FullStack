package com.divinaprovidencia.api.controller;

import com.divinaprovidencia.api.model.Accommodation;
import com.divinaprovidencia.api.service.AccommodationService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de exponer los servicios
 * relacionados con los alojamientos de Divina Providencia.
 */
@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    /**
     * Constructor utilizado por Spring para inyectar
     * el servicio de alojamientos.
     *
     * @param accommodationService servicio de alojamientos
     */
    public AccommodationController(
            AccommodationService accommodationService) {

        this.accommodationService = accommodationService;
    }

    /**
     * Obtiene todos los alojamientos registrados.
     *
     * Endpoint:
     * GET /api/accommodations
     *
     * @return lista de alojamientos
     */
    @GetMapping
    public ResponseEntity<List<Accommodation>> findAll() {

        return ResponseEntity.ok(
                accommodationService.findAll()
        );
    }

    /**
     * Obtiene únicamente los alojamientos disponibles.
     *
     * Endpoint:
     * GET /api/accommodations/available
     *
     * @return lista de alojamientos disponibles
     */
    @GetMapping("/available")
    public ResponseEntity<List<Accommodation>> findAvailable() {

        return ResponseEntity.ok(
                accommodationService.findAvailable()
        );
    }

    /**
     * Busca un alojamiento por su identificador.
     *
     * Endpoint:
     * GET /api/accommodations/{id}
     *
     * @param id identificador del alojamiento
     * @return alojamiento encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> findById(
            @PathVariable Long id) {

        return accommodationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    /**
     * Registra un nuevo alojamiento.
     *
     * Endpoint:
     * POST /api/accommodations
     *
     * @param accommodation datos del alojamiento
     * @return alojamiento creado
     */
    @PostMapping
    public ResponseEntity<Accommodation> create(
            @Valid @RequestBody Accommodation accommodation) {

        Accommodation createdAccommodation =
                accommodationService.create(accommodation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAccommodation);
    }

    /**
     * Actualiza un alojamiento existente.
     *
     * Endpoint:
     * PUT /api/accommodations/{id}
     *
     * @param id identificador del alojamiento
     * @param accommodation nuevos datos
     * @return alojamiento actualizado o 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<Accommodation> update(
            @PathVariable Long id,
            @Valid @RequestBody Accommodation accommodation) {

        return accommodationService.update(id, accommodation)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    /**
     * Elimina un alojamiento.
     *
     * Endpoint:
     * DELETE /api/accommodations/{id}
     *
     * @param id identificador del alojamiento
     * @return 204 si se elimina o 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        boolean deleted =
                accommodationService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}