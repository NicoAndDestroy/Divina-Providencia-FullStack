package com.example.divinaprovidencia.data.repository

import com.example.divinaprovidencia.data.model.Accommodation
import com.example.divinaprovidencia.data.model.AuthRequest
import com.example.divinaprovidencia.data.model.Reservation
import com.example.divinaprovidencia.data.model.ReservationRequest
import com.example.divinaprovidencia.data.remote.ApiService
import com.google.gson.JsonObject
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ReservationRepositoryTest {

    @Test
    fun createReservation_returnsSuccess_whenApiCreatesReservation() = runTest {

        val responseBody =
            JsonObject().apply {
                addProperty(
                    "message",
                    "Reserva creada correctamente."
                )
            }

        val apiService =
            FakeApiService(
                createReservationResponse =
                    Response.success(
                        responseBody
                    )
            )

        val repository =
            ReservationRepository(
                apiService
            )

        val request =
            ReservationRequest(
                guestName = "Nicolas Morales",
                guestEmail = "prueba@divinaprovidencia.com",
                checkIn = "2026-11-10",
                checkOut = "2026-11-12",
                numberOfGuests = 2,
                status = "PENDING",
                accommodationId = 1L
            )

        val result =
            repository.createReservation(
                request
            )

        assertTrue(
            result.isSuccess
        )

        assertEquals(
            "Reserva creada correctamente.",
            result.getOrNull()
        )
    }

    @Test
    fun createReservation_returnsFailure_whenApiReturnsError() = runTest {

        val errorBody =
            """{"error":"Bad Request"}"""
                .toResponseBody(
                    "application/json"
                        .toMediaType()
                )

        val apiService =
            FakeApiService(
                createReservationResponse =
                    Response.error(
                        400,
                        errorBody
                    )
            )

        val repository =
            ReservationRepository(
                apiService
            )

        val request =
            ReservationRequest(
                guestName = "Nicolas Morales",
                guestEmail = "correo-invalido",
                checkIn = "2026-11-10",
                checkOut = "2026-11-12",
                numberOfGuests = 2,
                status = "PENDING",
                accommodationId = 1L
            )

        val result =
            repository.createReservation(
                request
            )

        assertTrue(
            result.isFailure
        )

        assertTrue(
            result.exceptionOrNull()
                ?.message
                ?.contains("400")
                    == true
        )
    }

    @Test
    fun getReservations_returnsList_whenApiIsSuccessful() = runTest {

        val apiService =
            FakeApiService(
                reservationsResponse =
                    Response.success(
                        emptyList()
                    )
            )

        val repository =
            ReservationRepository(
                apiService
            )

        val result =
            repository.getReservations()

        assertTrue(
            result.isSuccess
        )

        assertEquals(
            0,
            result.getOrNull()?.size
        )
    }

    private class FakeApiService(

        private val createReservationResponse:
        Response<JsonObject> =
            Response.success(
                JsonObject()
            ),

        private val reservationsResponse:
        Response<List<Reservation>> =
            Response.success(
                emptyList()
            )

    ) : ApiService {

        override suspend fun register(
            request: AuthRequest
        ): Response<JsonObject> {
            throw UnsupportedOperationException()
        }

        override suspend fun login(
            request: AuthRequest
        ): Response<JsonObject> {
            throw UnsupportedOperationException()
        }

        override suspend fun getAccommodations():
                Response<List<Accommodation>> {

            throw UnsupportedOperationException()
        }

        override suspend fun getReservations():
                Response<List<Reservation>> {

            return reservationsResponse
        }

        override suspend fun createReservation(
            request: ReservationRequest
        ): Response<JsonObject> {

            return createReservationResponse
        }
    }
}