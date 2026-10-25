package com.example.divinaprovidencia.data.model

data class Accommodation(
    val id: Long,
    val name: String,
    val description: String,
    val capacity: Int,
    val pricePerNight: Double,
    val available: Boolean
)