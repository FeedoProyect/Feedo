package com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl

import android.util.Log
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.UserInformationModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.UserInformationRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Inject

class UserInformationRepositoryImpl @Inject constructor(
    private val client: SupabaseClient,
    private val storage: Storage
) : UserInformationRepository {

    override suspend fun getUserInfo(idUsuario: String): UserInformationModel? {
        return try {
            val response = client.postgrest["usuarios2"]
                .select {
                    filter { eq("id_usuario", idUsuario) }
                }
                .decodeList<UserInformationModel>()

            response.firstOrNull()
        } catch (e: Exception) {
            Log.e("UserInfoRepo", "Error en getUserInfo", e)
            null
        }
    }

    override suspend fun updateUserInfo(
        idUsuario: String,
        username: String,
        biografia: String,
        imagen_Perfil: String?
    ): Boolean {
        return try {
            // Mantener la imagen actual si no se pasa una nueva
            val current = getUserInfo(idUsuario)
            val finalImageUrl = imagen_Perfil ?: current?.imagen_perfil

            val updated = client.postgrest["usuarios2"].update(
                {
                    set("username", username)
                    set("biografia", biografia)
                    set("imagen_perfil", finalImageUrl)
                }
            ) {
                filter { eq("id_usuario", idUsuario) }
                select() // 👈 muy importante: fuerza a devolver el registro actualizado
            }.decodeList<UserInformationModel>()

            updated.isNotEmpty()
        } catch (e: Exception) {
            Log.e("UserInfoRepo", "Error en updateUserInfo", e)
            false
        }
    }
}


