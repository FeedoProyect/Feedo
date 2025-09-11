package com.benjamin.proyectofeedo.usuarioLogin.LoginDomain

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getUserUuid(): Flow<String?>
    suspend fun saveUserUuid(uuid: String)
    suspend fun clearUserUuid()
}

