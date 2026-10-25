package com.example.divinaprovidencia.data.repository

import com.example.divinaprovidencia.data.model.Accommodation
import com.example.divinaprovidencia.data.remote.ApiService

class AccommodationRepository(
    private val apiService: ApiService
) {

    suspend fun getAccommodations(): Result<List<Accommodation>> {
        return try {
            val response = apiService.getAccommodations()

            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure(
                    Exception("Error al consultar alojamientos: ${response.code()}")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}