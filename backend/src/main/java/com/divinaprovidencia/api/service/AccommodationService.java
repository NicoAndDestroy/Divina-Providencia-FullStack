package com.divinaprovidencia.api.service;

import com.divinaprovidencia.api.model.Accommodation;
import com.divinaprovidencia.api.repository.AccommodationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de manejar la lógica de negocio
 * relacionada con los alojamientos de Divina Providencia.
 */
@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    /**
     * Constructor utilizado por Spring para inyectar
     * el repositorio de alojamientos.
     *
     * @param accommodationRepository repositorio de alojamientos
     */
    public AccommodationService(
            AccommodationRepository accommodationRepository) {

        this.accommodationRepository = accommodationRepository;
    }

    /**
     * Obtiene todos los alojamientos registrados.
     *
     * @return lista de alojamientos
     */
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    /**
     * Busca un alojamiento por su identificador.
     *
     * @param id identificador del alojamiento
     * @return alojamiento encontrado, si existe
     */
    public Optional<Accommodation> findById(Long id) {
        return accommodationRepository.findById(id);
    }

    /**
     * Obtiene los alojamientos disponibles.
     *
     * @return lista de alojamientos disponibles
     */
    public List<Accommodation> findAvailable() {
        return accommodationRepository.findByAvailable(true);
    }

    /**
     * Guarda un nuevo alojamiento.
     *
     * @param accommodation alojamiento que será almacenado
     * @return alojamiento guardado
     */
    public Accommodation create(Accommodation accommodation) {
        accommodation.setId(null);

        return accommodationRepository.save(accommodation);
    }

    /**
     * Actualiza un alojamiento existente.
     *
     * @param id identificador del alojamiento
     * @param updatedAccommodation nuevos datos
     * @return alojamiento actualizado, si existe
     */
    public Optional<Accommodation> update(
            Long id,
            Accommodation updatedAccommodation) {

        return accommodationRepository.findById(id)
                .map(existingAccommodation -> {

                    existingAccommodation.setName(
                            updatedAccommodation.getName()
                    );

                    existingAccommodation.setDescription(
                            updatedAccommodation.getDescription()
                    );

                    existingAccommodation.setCapacity(
                            updatedAccommodation.getCapacity()
                    );

                    existingAccommodation.setPricePerNight(
                            updatedAccommodation.getPricePerNight()
                    );

                    existingAccommodation.setAvailable(
                            updatedAccommodation.getAvailable()
                    );

                    return accommodationRepository.save(
                            existingAccommodation
                    );
                });
    }

    /**
     * Elimina un alojamiento por su identificador.
     *
     * @param id identificador del alojamiento
     * @return true si fue eliminado; false si no existe
     */
    public boolean delete(Long id) {

        if (!accommodationRepository.existsById(id)) {
            return false;
        }

        accommodationRepository.deleteById(id);

        return true;
    }
}