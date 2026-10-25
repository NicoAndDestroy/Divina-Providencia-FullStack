package com.divinaprovidencia.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Entidad que representa una reserva realizada
 * para un alojamiento de Divina Providencia.
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del huésped es obligatorio.")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres.")
    @Column(name = "guest_name", nullable = false, length = 150)
    private String guestName;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres.")
    @Column(name = "guest_email", nullable = false, length = 150)
    private String guestEmail;

    @NotNull(message = "La fecha de entrada es obligatoria.")
    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @NotNull(message = "La fecha de salida es obligatoria.")
    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @NotNull(message = "El número de huéspedes es obligatorio.")
    @Min(value = 1, message = "Debe existir al menos un huésped.")
    @Column(name = "number_of_guests", nullable = false)
    private Integer numberOfGuests;

    @NotBlank(message = "El estado de la reserva es obligatorio.")
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres.")
    @Column(nullable = false, length = 30)
    private String status;

    /**
     * Cada reserva pertenece a un alojamiento.
     */
    @NotNull(message = "El alojamiento es obligatorio.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Reservation() {
    }

    /**
     * Constructor utilizado para crear reservas.
     */
    public Reservation(
            String guestName,
            String guestEmail,
            LocalDate checkIn,
            LocalDate checkOut,
            Integer numberOfGuests,
            String status,
            Accommodation accommodation) {

        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.accommodation = accommodation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}