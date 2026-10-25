package com.divinaprovidencia.api.service;

import com.divinaprovidencia.api.dto.ReservationRequest;
import com.divinaprovidencia.api.model.Accommodation;
import com.divinaprovidencia.api.model.Reservation;
import com.divinaprovidencia.api.repository.AccommodationRepository;
import com.divinaprovidencia.api.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void findAllShouldReturnReservations() {

        Reservation reservation =
                mock(Reservation.class);

        when(reservationRepository.findAll())
                .thenReturn(List.of(reservation));

        List<Reservation> result =
                reservationService.findAll();

        assertEquals(1, result.size());
        assertSame(reservation, result.get(0));
    }

    @Test
    void findByIdShouldReturnReservationWhenItExists() {

        Reservation reservation =
                mock(Reservation.class);

        when(reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        Optional<Reservation> result =
                reservationService.findById(1L);

        assertTrue(result.isPresent());
        assertSame(reservation, result.get());
    }

    @Test
    void findByStatusShouldReturnMatchingReservations() {

        Reservation reservation =
                mock(Reservation.class);

        when(reservationRepository.findByStatus("CONFIRMED"))
                .thenReturn(List.of(reservation));

        List<Reservation> result =
                reservationService.findByStatus("CONFIRMED");

        assertEquals(1, result.size());

        verify(reservationRepository)
                .findByStatus("CONFIRMED");
    }

    @Test
    void findByAccommodationIdShouldReturnReservations() {

        Reservation reservation =
                mock(Reservation.class);

        when(reservationRepository.findByAccommodationId(2L))
                .thenReturn(List.of(reservation));

        List<Reservation> result =
                reservationService.findByAccommodationId(2L);

        assertEquals(1, result.size());

        verify(reservationRepository)
                .findByAccommodationId(2L);
    }

    @Test
    void createShouldSaveReservationWhenAccommodationExists() {

        ReservationRequest request =
                createRequestMock();

        Accommodation accommodation =
                mock(Accommodation.class);

        Reservation savedReservation =
                mock(Reservation.class);

        when(accommodationRepository.findById(2L))
                .thenReturn(Optional.of(accommodation));

        when(reservationRepository.save(
                any(Reservation.class)
        )).thenReturn(savedReservation);

        Optional<Reservation> result =
                reservationService.create(request);

        assertTrue(result.isPresent());

        assertSame(
                savedReservation,
                result.get()
        );

        verify(reservationRepository)
                .save(any(Reservation.class));
    }

    @Test
void createShouldReturnEmptyWhenAccommodationDoesNotExist() {

    ReservationRequest request =
            mock(ReservationRequest.class);

    when(request.getAccommodationId())
            .thenReturn(2L);

    when(accommodationRepository.findById(2L))
            .thenReturn(Optional.empty());

    Optional<Reservation> result =
            reservationService.create(request);

    assertTrue(result.isEmpty());

    verify(reservationRepository, never())
            .save(any(Reservation.class));
}

    @Test
    void updateShouldSaveReservationWhenDataExists() {

        ReservationRequest request =
                createRequestMock();

        Reservation existingReservation =
                mock(Reservation.class);

        Accommodation accommodation =
                mock(Accommodation.class);

        when(reservationRepository.findById(1L))
                .thenReturn(Optional.of(existingReservation));

        when(accommodationRepository.findById(2L))
                .thenReturn(Optional.of(accommodation));

        when(reservationRepository.save(existingReservation))
                .thenReturn(existingReservation);

        Optional<Reservation> result =
                reservationService.update(
                        1L,
                        request
                );

        assertTrue(result.isPresent());

        assertSame(
                existingReservation,
                result.get()
        );

        verify(reservationRepository)
                .save(existingReservation);
    }

    @Test
void updateShouldReturnEmptyWhenReservationDoesNotExist() {

    ReservationRequest request =
            mock(ReservationRequest.class);

    when(reservationRepository.findById(99L))
            .thenReturn(Optional.empty());

    Optional<Reservation> result =
            reservationService.update(
                    99L,
                    request
            );

    assertTrue(result.isEmpty());

    verify(accommodationRepository, never())
            .findById(anyLong());

    verify(reservationRepository, never())
            .save(any());
}

    @Test
void updateShouldReturnEmptyWhenAccommodationDoesNotExist() {

    ReservationRequest request =
            mock(ReservationRequest.class);

    Reservation existingReservation =
            mock(Reservation.class);

    when(request.getAccommodationId())
            .thenReturn(2L);

    when(reservationRepository.findById(1L))
            .thenReturn(
                    Optional.of(existingReservation)
            );

    when(accommodationRepository.findById(2L))
            .thenReturn(Optional.empty());

    Optional<Reservation> result =
            reservationService.update(
                    1L,
                    request
            );

    assertTrue(result.isEmpty());

    verify(reservationRepository, never())
            .save(any());
}

    @Test
    void deleteShouldReturnTrueWhenReservationExists() {

        when(reservationRepository.existsById(1L))
                .thenReturn(true);

        boolean result =
                reservationService.delete(1L);

        assertTrue(result);

        verify(reservationRepository)
                .deleteById(1L);
    }

    @Test
    void deleteShouldReturnFalseWhenReservationDoesNotExist() {

        when(reservationRepository.existsById(99L))
                .thenReturn(false);

        boolean result =
                reservationService.delete(99L);

        assertFalse(result);

        verify(reservationRepository, never())
                .deleteById(anyLong());
    }

    /**
     * Crea una solicitud simulada para reutilizarla
     * en las pruebas de creación y actualización.
     */
    private ReservationRequest createRequestMock() {

        ReservationRequest request =
                mock(ReservationRequest.class);

        when(request.getGuestName())
                .thenReturn("Jhoan Nicolás Morales");

        when(request.getGuestEmail())
                .thenReturn("unit.test@example.com");

        when(request.getCheckIn())
                .thenReturn(
                        LocalDate.of(2026, 12, 10)
                );

        when(request.getCheckOut())
                .thenReturn(
                        LocalDate.of(2026, 12, 13)
                );

        when(request.getNumberOfGuests())
                .thenReturn(2);

        when(request.getStatus())
                .thenReturn("CONFIRMED");

        when(request.getAccommodationId())
                .thenReturn(2L);

        return request;
    }
}