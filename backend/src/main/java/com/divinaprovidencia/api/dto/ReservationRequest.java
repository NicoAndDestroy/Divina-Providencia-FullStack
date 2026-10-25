package com.divinaprovidencia.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO utilizado para recibir los datos necesarios
 * para crear o actualizar una reserva.
 */
public class ReservationRequest {

    @NotBlank(message = "El nombre del huésped es obligatorio.")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres.")
    private String guestName;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres.")
    private String guestEmail;

    @NotNull(message = "La fecha de entrada es obligatoria.")
    private LocalDate checkIn;

    @NotNull(message = "La fecha de salida es obligatoria.")
    private LocalDate checkOut;

    @NotNull(message = "El número de huéspedes es obligatorio.")
    @Min(value = 1, message = "Debe existir al menos un huésped.")
    private Integer numberOfGuests;

    @NotBlank(message = "El estado de la reserva es obligatorio.")
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres.")
    private String status;

    @NotNull(message = "El identificador del alojamiento es obligatorio.")
    private Long accommodationId;

    public ReservationRequest() {
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }
}