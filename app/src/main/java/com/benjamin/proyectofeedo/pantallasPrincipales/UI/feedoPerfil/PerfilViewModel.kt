package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.useCase.GetUserInformationUseCase
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel@Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val sessionRepository: SessionRepository
): ViewModel() {

    private val _username = MutableStateFlow("Cargando...")
    val username: StateFlow<String> = _username

    init {
        viewModelScope.launch {
            sessionRepository.getUserUuid().collect { uuid ->
                if (!uuid.isNullOrEmpty()) {
                    val user = getUserInformationUseCase(uuid)
                    _username.value = user?.userName ?: "Invitado"
                } else {
                    _username.value = "Sin sesión"
                }
            }
        }
    }
}