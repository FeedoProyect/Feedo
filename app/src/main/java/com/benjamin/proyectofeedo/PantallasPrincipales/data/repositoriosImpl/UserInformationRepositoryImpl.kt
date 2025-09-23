package com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl

import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.UserInformationModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.UserInformationRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class UserInformationRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : UserInformationRepository {

    override suspend fun getUserInfo(uuid: String): UserInformationModel? {
        return try {
            val response = client.postgrest["usuarios2"]
                .select(){
                    filter { eq("id_usuario", uuid) }
                }.decodeList<UserInformationModel>()

            response.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}