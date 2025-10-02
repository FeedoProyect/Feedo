package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInformationModel(
    @SerialName("id_usuario") val uuid: String,
    @SerialName("username") val username: String,
    @SerialName("biografia") val biografia: String? = null,
    @SerialName("imagen_perfil") val imagen_perfil: String? = null
)
