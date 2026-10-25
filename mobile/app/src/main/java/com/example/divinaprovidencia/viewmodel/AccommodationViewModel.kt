package com.example.divinaprovidencia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divinaprovidencia.data.model.Accommodation
import com.example.divinaprovidencia.data.remote.RetrofitClient
import com.example.divinaprovidencia.data.repository.AccommodationRepository
import kotlinx.coroutines.launch

class AccommodationViewModel : ViewModel() {

    private val repository =
        AccommodationRepository(RetrofitClient.apiService)

    private val _accommodations =
        MutableLiveData<List<Accommodation>>()

    val accommodations: LiveData<List<Accommodation>>
        get() = _accommodations

    private val _error =
        MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    private val _loading =
        MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading

    fun loadAccommodations() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getAccommodations()
                .onSuccess {
                    _accommodations.value = it
                }
                .onFailure {
                    _error.value =
                        it.message ?: "Error al consultar alojamientos"
                }

            _loading.value = false
        }
    }
}