package com.example.divinaprovidencia.data.model

data class ReservationRequest(
    val guestName: String,
    val guestEmail: String,
    val checkIn: String,
    val checkOut: String,
    val numberOfGuests: Int,
    val status: String,
    val accommodationId: Long
)