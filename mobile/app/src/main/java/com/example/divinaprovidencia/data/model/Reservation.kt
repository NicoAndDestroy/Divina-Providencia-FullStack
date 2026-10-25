package com.example.divinaprovidencia.data.model

data class Reservation(
    val id: Long,
    val guestName: String,
    val guestEmail: String,
    val checkIn: String,
    val checkOut: String,
    val numberOfGuests: Int,
    val status: String,
    val accommodation: Accommodation
)