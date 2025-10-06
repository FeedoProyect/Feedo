package com.benjamin.proyectofeedo.utils

import android.content.Context
import android.net.Uri

fun Uri.toByteArray(context: Context): ByteArray {
    return context.contentResolver.openInputStream(this)?.use { inputStream ->
        inputStream.readBytes()
    } ?: throw IllegalStateException("No se pudo leer la imagen")
}