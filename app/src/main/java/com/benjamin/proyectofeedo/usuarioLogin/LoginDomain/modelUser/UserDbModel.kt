package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser

import kotlinx.serialization.Serializable

@Serializable
data class UserDbModel(
    val id_usuario: String,
    val correo: String,
    val username: String
)