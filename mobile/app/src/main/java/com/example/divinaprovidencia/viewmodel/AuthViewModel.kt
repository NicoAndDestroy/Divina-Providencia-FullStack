package com.example.divinaprovidencia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divinaprovidencia.data.remote.RetrofitClient
import com.example.divinaprovidencia.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository =
        AuthRepository(
            RetrofitClient.apiService
        )

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

    fun register(
        username: String,
        password: String
    ) {
        if (
            username.isBlank() ||
            password.isBlank()
        ) {
            _error.value =
                "Usuario y contraseña son obligatorios."
            return
        }

        viewModelScope.launch {

            _loading.value = true
            _error.value = null
            _successMessage.value = null

            repository.register(
                username,
                password
            )
                .onSuccess {
                    _successMessage.value = it
                }
                .onFailure {
                    _error.value =
                        it.message
                            ?: "Error al registrar usuario."
                }

            _loading.value = false
        }
    }

    fun login(
        username: String,
        password: String
    ) {
        if (
            username.isBlank() ||
            password.isBlank()
        ) {
            _error.value =
                "Usuario y contraseña son obligatorios."
            return
        }

        viewModelScope.launch {

            _loading.value = true
            _error.value = null
            _successMessage.value = null

            repository.login(
                username,
                password
            )
                .onSuccess {
                    _successMessage.value = it
                }
                .onFailure {
                    _error.value =
                        it.message
                            ?: "Error en la autenticación."
                }

            _loading.value = false
        }
    }

    fun clearMessages() {
        _successMessage.value = null
        _error.value = null
    }
}