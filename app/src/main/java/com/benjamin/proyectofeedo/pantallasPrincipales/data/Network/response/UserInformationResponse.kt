package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response

import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.UserInformationModel
import com.google.gson.annotations.SerializedName

class UserInformationResponse (
    @SerializedName("id_usuario") val idUser: String,
    @SerializedName("username") val userName: String
) {
    fun toDomain() : UserInformationModel {
        return UserInformationModel(
            uuid = idUser,
            userName = userName
        )
    }
}