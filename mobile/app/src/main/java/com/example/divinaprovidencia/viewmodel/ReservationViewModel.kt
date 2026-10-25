package com.example.divinaprovidencia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divinaprovidencia.data.model.Reservation
import com.example.divinaprovidencia.data.model.ReservationRequest
import com.example.divinaprovidencia.data.remote.RetrofitClient
import com.example.divinaprovidencia.data.repository.ReservationRepository
import kotlinx.coroutines.launch

class ReservationViewModel : ViewModel() {

    private val repository =
        ReservationRepository(
            RetrofitClient.apiService
        )

    private val _reservations =
        MutableLiveData<List<Reservation>>()

    val reservations: LiveData<List<Reservation>>
        get() = _reservations

    private val _successMessage =
        MutableLiveData<String?>()

    val successMessage: LiveData<String?>
        get() = _successMessage

    private val _error =
        MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    private val _loading =
        MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading

    fun loadReservations() {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            repository.getReservations()
                .onSuccess {
                    _reservations.value = it
                }
                .onFailure {
                    _error.value =
                        it.message
                            ?: "Error al consultar reservas."
                }

            _loading.value = false
        }
    }

    fun createReservation(
        request: ReservationRequest
    ) {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null
            _successMessage.value = null

            repository.createReservation(
                request
            )
                .onSuccess {
                    _successMessage.value = it
                }
                .onFailure {
                    _error.value =
                        it.message
                            ?: "Error al crear la reserva."
                }

            _loading.value = false
        }
    }

    fun clearMessages() {
        _successMessage.value = null
        _error.value = null
    }
}