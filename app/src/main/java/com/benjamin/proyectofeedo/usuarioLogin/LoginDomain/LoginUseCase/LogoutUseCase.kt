package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.LoginUseCase

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}