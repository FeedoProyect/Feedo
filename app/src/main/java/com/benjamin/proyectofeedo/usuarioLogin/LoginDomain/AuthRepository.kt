package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain

import android.app.Activity
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel


interface AuthRepository {
    suspend fun register(email: String, password: String, username: String): UserModel?
    suspend fun login(email: String, password: String): UserModel?
    suspend fun logout(): Boolean
    suspend fun loginWithGoogle(activity: Activity): UserModel?
}