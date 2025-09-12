package com.benjamin.proyectofeedo.usuarioLogin.LoginUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.SessionRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@HiltViewModel
class AuthUserViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state


    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email
    private val _password = MutableStateFlow("")
    val password = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val user = withContext(Dispatchers.IO) {
                authRepository.register(email, password, username)
            }
            _state.value = if (user != null) {
                AuthState.Success(user)
            } else {
                AuthState.Error("No se pudo registrar")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val user = withContext(Dispatchers.IO) {
                    authRepository.login(email, password)
                }
                if (user != null) {
                    // ✅ Guardar sesión localmente
                    sessionRepository.saveUserUuid(user.id)

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
            _state.value = AuthState.Loading
            val success = withContext(Dispatchers.IO) {
                authRepository.logout()
            }
            if (success) {
                // 🔑 Limpiar también la sesión local
                sessionRepository.clearUserUuid()
                _state.value = AuthState.LoggedOut
            } else {
                _state.value = AuthState.Error("No se pudo cerrar sesión")
            }
        }
    }


}
