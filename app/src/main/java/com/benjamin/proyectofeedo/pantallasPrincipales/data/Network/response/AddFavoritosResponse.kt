package com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.response

import com.google.gson.annotations.SerializedName

data class AddFavoritosResponse (
    @SerializedName("usuario_id") val userId: String,
    @SerializedName("receta_id") val recetaId: Int
)