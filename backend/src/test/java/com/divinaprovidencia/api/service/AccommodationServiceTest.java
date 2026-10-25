package com.divinaprovidencia.api.service;

import com.divinaprovidencia.api.model.Accommodation;
import com.divinaprovidencia.api.repository.AccommodationRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccommodationService accommodationService;

    @Test
    void findAllShouldReturnAllAccommodations() {

        Accommodation accommodation =
                mock(Accommodation.class);

        when(accommodationRepository.findAll())
                .thenReturn(List.of(accommodation));

        List<Accommodation> result =
                accommodationService.findAll();

        assertEquals(1, result.size());
        assertSame(accommodation, result.get(0));
    }

    @Test
    void findByIdShouldReturnAccommodationWhenItExists() {

        Accommodation accommodation =
                mock(Accommodation.class);

        when(accommodationRepository.findById(1L))
                .thenReturn(Optional.of(accommodation));

        Optional<Accommodation> result =
                accommodationService.findById(1L);

        assertTrue(result.isPresent());
        assertSame(accommodation, result.get());
    }

    @Test
    void findAvailableShouldReturnAvailableAccommodations() {

        Accommodation accommodation =
                mock(Accommodation.class);

        when(accommodationRepository.findByAvailable(true))
                .thenReturn(List.of(accommodation));

        List<Accommodation> result =
                accommodationService.findAvailable();

        assertEquals(1, result.size());

        verify(accommodationRepository)
                .findByAvailable(true);
    }

    @Test
    void createShouldResetIdAndSaveAccommodation() {

        Accommodation accommodation =
                mock(Accommodation.class);

        Accommodation savedAccommodation =
                mock(Accommodation.class);

        when(accommodationRepository.save(accommodation))
                .thenReturn(savedAccommodation);

        Accommodation result =
                accommodationService.create(accommodation);

        verify(accommodation)
                .setId(null);

        verify(accommodationRepository)
                .save(accommodation);

        assertSame(savedAccommodation, result);
    }

    @Test
    void updateShouldSaveWhenAccommodationExists() {

        Accommodation existingAccommodation =
                mock(Accommodation.class);

        Accommodation updatedAccommodation =
                mock(Accommodation.class);

        when(accommodationRepository.findById(1L))
                .thenReturn(Optional.of(existingAccommodation));

        when(accommodationRepository.save(existingAccommodation))
                .thenReturn(existingAccommodation);

        Optional<Accommodation> result =
                accommodationService.update(
                        1L,
                        updatedAccommodation
                );

        assertTrue(result.isPresent());
        assertSame(
                existingAccommodation,
                result.get()
        );

        verify(accommodationRepository)
                .save(existingAccommodation);
    }

    @Test
    void updateShouldReturnEmptyWhenAccommodationDoesNotExist() {

        Accommodation updatedAccommodation =
                mock(Accommodation.class);

        when(accommodationRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Accommodation> result =
                accommodationService.update(
                        99L,
                        updatedAccommodation
                );

        assertTrue(result.isEmpty());

        verify(accommodationRepository, never())
                .save(any());
    }

    @Test
    void deleteShouldReturnTrueWhenAccommodationExists() {

        when(accommodationRepository.existsById(1L))
                .thenReturn(true);

        boolean result =
                accommodationService.delete(1L);

        assertTrue(result);

        verify(accommodationRepository)
                .deleteById(1L);
    }

    @Test
    void deleteShouldReturnFalseWhenAccommodationDoesNotExist() {

        when(accommodationRepository.existsById(99L))
                .thenReturn(false);

        boolean result =
                accommodationService.delete(99L);

        assertFalse(result);

        verify(accommodationRepository, never())
                .deleteById(anyLong());
    }
}