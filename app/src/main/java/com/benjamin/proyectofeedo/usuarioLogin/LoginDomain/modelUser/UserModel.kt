package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser

import kotlinx.serialization.Serializable

@Serializable
data class UserModel (
    val email: String,
    val id: String
)