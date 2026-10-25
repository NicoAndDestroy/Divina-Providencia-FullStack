package com.example.divinaprovidencia.data.repository

import com.example.divinaprovidencia.data.model.Reservation
import com.example.divinaprovidencia.data.model.ReservationRequest
import com.example.divinaprovidencia.data.remote.ApiService
import com.google.gson.JsonObject
import retrofit2.Response

class ReservationRepository(
    private val apiService: ApiService
) {

    suspend fun getReservations(): Result<List<Reservation>> {
        return try {

            val response =
                apiService.getReservations()

            if (response.isSuccessful) {
                Result.success(
                    response.body().orEmpty()
                )
            } else {
                Result.failure(
                    Exception(
                        "Error al consultar reservas: ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createReservation(
        request: ReservationRequest
    ): Result<String> {
        return try {

            val response =
                apiService.createReservation(
                    request
                )

            handleCreateResponse(
                response
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun handleCreateResponse(
        response: Response<JsonObject>
    ): Result<String> {

        if (!response.isSuccessful) {
            return Result.failure(
                Exception(
                    "No fue posible crear la reserva: ${response.code()}"
                )
            )
        }

        val body =
            response.body()

        val message =
            body
                ?.get("message")
                ?.takeUnless {
                    it.isJsonNull
                }
                ?.asString
                ?: "Reserva creada correctamente."

        return Result.success(
            message
        )
    }
}