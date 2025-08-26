package com.benjamin.proyectofeedo.usuarioLogin.LoginUI

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: UserModel) : AuthState()
    data class Error(val message: String) : AuthState()
}
