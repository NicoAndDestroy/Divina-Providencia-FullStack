package com.example.divinaprovidencia.data.repository

import com.example.divinaprovidencia.data.model.AuthRequest
import com.example.divinaprovidencia.data.remote.ApiService
import com.google.gson.JsonObject
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService
) {

    suspend fun register(
        username: String,
        password: String
    ): Result<String> {
        return try {
            val response = apiService.register(
                AuthRequest(
                    username = username,
                    password = password
                )
            )

            handleAuthResponse(
                response = response,
                defaultSuccessMessage = "Usuario registrado correctamente."
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(
        username: String,
        password: String
    ): Result<String> {
        return try {
            val response = apiService.login(
                AuthRequest(
                    username = username,
                    password = password
                )
            )

            handleAuthResponse(
                response = response,
                defaultSuccessMessage = "Autenticación satisfactoria."
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun handleAuthResponse(
        response: Response<JsonObject>,
        defaultSuccessMessage: String
    ): Result<String> {

        if (!response.isSuccessful) {
            return Result.failure(
                Exception("Solicitud rechazada: ${response.code()}")
            )
        }

        val body = response.body()

        val success =
            body
                ?.get("success")
                ?.takeUnless { it.isJsonNull }
                ?.asBoolean
                ?: true

        val message =
            body
                ?.get("message")
                ?.takeUnless { it.isJsonNull }
                ?.asString
                ?: defaultSuccessMessage

        return if (success) {
            Result.success(message)
        } else {
            Result.failure(Exception(message))
        }
    }
}