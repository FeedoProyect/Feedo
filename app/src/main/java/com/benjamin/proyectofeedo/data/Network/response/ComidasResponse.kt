package com.benjamin.proyectofeedo.data.Network.response

import com.benjamin.proyectofeedo.domain.model.ComidasModel
import com.google.gson.annotations.SerializedName

/* Esta clase es el modelo de datos que va agarrar la apiservice de al base de datos, ignora
   descripcion y demas, solo queremos el nombre y la imagen para pintar el listado */

data class ComidasResponse(
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagen") val imagen: String,
) {
    fun toDomain(): ComidasModel{
        return ComidasModel(
            titulo = titulo,
            imagen = imagen,
        )
    }
}