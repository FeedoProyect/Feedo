package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.LoginUseCase

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): UserModel? {
        return repository.login(email, password)
    }
}