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

class AuthRepositoryTest {

    @Test
    fun login_returnsSuccess_whenApiAcceptsCredentials() = runTest {

        val body =
            JsonObject().apply {
                addProperty(
                    "success",
                    true
                )
                addProperty(
                    "message",
                    "Autenticación correcta."
                )
            }

        val apiService =
            FakeApiService(
                loginResponse =
                    Response.success(body)
            )

        val repository =
            AuthRepository(apiService)

        val result =
            repository.login(
                username = "testuser",
                password = "123456"
            )

        assertTrue(
            result.isSuccess
        )

        assertEquals(
            "Autenticación correcta.",
            result.getOrNull()
        )
    }

    @Test
    fun login_returnsFailure_whenApiRejectsCredentials() = runTest {

        val errorBody =
            """{"error":"Unauthorized"}"""
                .toResponseBody(
                    "application/json"
                        .toMediaType()
                )

        val apiService =
            FakeApiService(
                loginResponse =
                    Response.error(
                        401,
                        errorBody
                    )
            )

        val repository =
            AuthRepository(apiService)

        val result =
            repository.login(
                username = "testuser",
                password = "incorrecta"
            )

        assertTrue(
            result.isFailure
        )

        assertTrue(
            result.exceptionOrNull()
                ?.message
                ?.contains("401")
                    == true
        )
    }

    @Test
    fun register_returnsSuccess_whenApiCreatesUser() = runTest {

        val body =
            JsonObject().apply {
                addProperty(
                    "success",
                    true
                )
                addProperty(
                    "message",
                    "Usuario registrado correctamente."
                )
            }

        val apiService =
            FakeApiService(
                registerResponse =
                    Response.success(body)
            )

        val repository =
            AuthRepository(apiService)

        val result =
            repository.register(
                username = "nuevoUsuario",
                password = "123456"
            )

        assertTrue(
            result.isSuccess
        )

        assertEquals(
            "Usuario registrado correctamente.",
            result.getOrNull()
        )
    }

    private class FakeApiService(
        private val loginResponse:
        Response<JsonObject> =
            Response.success(
                JsonObject()
            ),

        private val registerResponse:
        Response<JsonObject> =
            Response.success(
                JsonObject()
            )
    ) : ApiService {

        override suspend fun register(
            request: AuthRequest
        ): Response<JsonObject> {

            return registerResponse
        }

        override suspend fun login(
            request: AuthRequest
        ): Response<JsonObject> {

            return loginResponse
        }

        override suspend fun getAccommodations():
                Response<List<Accommodation>> {

            throw UnsupportedOperationException()
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