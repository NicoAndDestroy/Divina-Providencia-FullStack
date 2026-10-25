package com.divinaprovidencia.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Entidad que representa un alojamiento disponible
 * dentro del proyecto Divina Providencia.
 */
@Entity
@Table(name = "accommodations")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del alojamiento es obligatorio.")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres.")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull(message = "La capacidad es obligatoria.")
    @Min(value = 1, message = "La capacidad debe ser mínimo de 1 huésped.")
    @Column(nullable = false)
    private Integer capacity;

    @NotNull(message = "El precio por noche es obligatorio.")
    @DecimalMin(
        value = "0.0",
        inclusive = false,
        message = "El precio por noche debe ser mayor que cero."
    )
    @Column(name = "price_per_night", nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerNight;

    @NotNull(message = "El estado de disponibilidad es obligatorio.")
    @Column(nullable = false)
    private Boolean available;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Accommodation() {
    }

    /**
     * Constructor utilizado para crear alojamientos.
     */
    public Accommodation(
            String name,
            String description,
            Integer capacity,
            BigDecimal pricePerNight,
            Boolean available) {

        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}