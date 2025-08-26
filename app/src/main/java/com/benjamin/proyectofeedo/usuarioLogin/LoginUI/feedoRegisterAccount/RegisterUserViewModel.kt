package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoRegisterAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.LoginUseCase.GetCurrentUserUseCase
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.LoginUseCase.LoginUseCase
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.LoginUseCase.LogoutUseCase
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.LoginUseCase.RegisterUseCase
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val user = registerUseCase(email, password)
                if (user != null) {
                    _state.value = AuthState.Success(user)
                } else {
                    _state.value = AuthState.Error("No se pudo registrar")
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val user = loginUseCase(email, password)
                if (user != null) {
                    _state.value = AuthState.Success(user)
                } else {
                    _state.value = AuthState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _state.value = AuthState.Idle
        }
    }

    fun getCurrentUser() {
        val user = getCurrentUserUseCase()
        if (user != null) {
            _state.value = AuthState.Success(user)
        }
    }
}