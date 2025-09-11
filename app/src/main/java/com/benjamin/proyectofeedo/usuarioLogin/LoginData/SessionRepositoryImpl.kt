package com.benjamin.proyectofeedo.usuarioLogin.LoginData

import androidx.datastore.preferences.core.stringPreferencesKey
import com.benjamin.proyectofeedo.core.datastore.Local.DataStorePreferences
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dataStore: DataStorePreferences
) : SessionRepository {

    companion object {
        private val USER_UUID = stringPreferencesKey("user_uuid")
    }

    override fun getUserUuid(): Flow<String?> =
        dataStore.getString(USER_UUID)

    override suspend fun saveUserUuid(uuid: String) {
        dataStore.saveString(USER_UUID, uuid)
    }

    override suspend fun clearUserUuid() {
        dataStore.clear(USER_UUID)
    }
}