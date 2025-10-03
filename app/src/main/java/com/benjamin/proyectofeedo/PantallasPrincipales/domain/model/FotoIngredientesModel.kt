package com.benjamin.proyectofeedo.PantallasPrincipales.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@Parcelize
data class FotoIngredientesModel(
    val id: Int,
    val nombre: String,
    val imagen: String
) : Parcelable

@Serializable
@Parcelize
data class IngredienteItem(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val imagen: String? = null
) : Parcelable