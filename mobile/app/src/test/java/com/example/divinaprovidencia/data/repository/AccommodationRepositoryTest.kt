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

class AccommodationRepositoryTest {

    @Test
    fun getAccommodations_returnsList_whenApiIsSuccessful() = runTest {

        val expectedAccommodation =
            Accommodation(
                id = 1L,
                name = "Suite Campestre",
                description = "Alojamiento confortable",
                capacity = 4,
                pricePerNight = 290000.0,
                available = true
            )

        val apiService =
            FakeApiService(
                accommodationResponse =
                    Response.success(
                        listOf(expectedAccommodation)
                    )
            )

        val repository =
            AccommodationRepository(apiService)

        val result =
            repository.getAccommodations()

        assertTrue(
            result.isSuccess
        )

        assertEquals(
            1,
            result.getOrNull()?.size
        )

        assertEquals(
            "Suite Campestre",
            result.getOrNull()
                ?.first()
                ?.name
        )
    }

    @Test
    fun getAccommodations_returnsFailure_whenApiReturnsError() = runTest {

        val errorBody =
            """{"error":"Server error"}"""
                .toResponseBody(
                    "application/json"
                        .toMediaType()
                )

        val apiService =
            FakeApiService(
                accommodationResponse =
                    Response.error(
                        500,
                        errorBody
                    )
            )

        val repository =
            AccommodationRepository(apiService)

        val result =
            repository.getAccommodations()

        assertTrue(
            result.isFailure
        )

        assertTrue(
            result.exceptionOrNull()
                ?.message
                ?.contains("500")
                    == true
        )
    }

    private class FakeApiService(
        private val accommodationResponse:
        Response<List<Accommodation>>
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

            return accommodationResponse
        }

        override suspend fun getReservations():
                Response<List<Reservation>> {

            throw UnsupportedOperationException()
        }

        override suspend fun createReservation(
            request: ReservationRequest
        ): Response<JsonObject> {

            throw UnsupportedOperationException()
        }
    }
}