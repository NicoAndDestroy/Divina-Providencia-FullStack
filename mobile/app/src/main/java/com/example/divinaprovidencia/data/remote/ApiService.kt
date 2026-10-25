package com.example.divinaprovidencia.data.remote

import com.example.divinaprovidencia.data.model.Accommodation
import com.example.divinaprovidencia.data.model.AuthRequest
import com.example.divinaprovidencia.data.model.Reservation
import com.example.divinaprovidencia.data.model.ReservationRequest
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: AuthRequest
    ): Response<JsonObject>

    @POST("auth/login")
    suspend fun login(
        @Body request: AuthRequest
    ): Response<JsonObject>

    @GET("accommodations")
    suspend fun getAccommodations(): Response<List<Accommodation>>

    @GET("reservations")
    suspend fun getReservations(): Response<List<Reservation>>

    @POST("reservations")
    suspend fun createReservation(
        @Body request: ReservationRequest
    ): Response<JsonObject>
}