package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel


interface AuthRepository {
    suspend fun register(email: String, password: String): UserModel?
    suspend fun login(email: String, password: String): UserModel?
    suspend fun logout()
    fun getCurrentUser(): UserModel?
}