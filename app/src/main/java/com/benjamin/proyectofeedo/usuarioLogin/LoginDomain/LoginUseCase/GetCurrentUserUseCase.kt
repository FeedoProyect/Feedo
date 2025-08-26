package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.LoginUseCase

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): UserModel? {
        return repository.getCurrentUser()
    }
}