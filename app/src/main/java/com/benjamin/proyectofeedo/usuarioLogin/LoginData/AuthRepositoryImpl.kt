package com.benjamin.proyectofeedo.usuarioLogin.LoginData

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val supabaseClient: SupabaseClient
) : AuthRepository {

    override suspend fun register(email: String, password: String, username: String): UserModel? {
        return try {
            val userInfo: UserInfo? = auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            if (userInfo != null) {
                // Inserción en tabla usuarios
                supabaseClient.postgrest["usuarios2"].insert(
                    mapOf(
                        "id_usuario" to userInfo.id,
                        "correo" to (userInfo.email ?: email),
                        "username" to username
                    )
                )

                UserModel(
                    email = userInfo.email ?: email,
                    id = userInfo.id
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun login(email: String, password: String): UserModel? {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            val user = auth.currentUserOrNull()
            if (user != null) {
                UserModel(
                    email = user.email ?: email,
                    id = user.id
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace() // 👈 te va a mostrar en Logcat si es credencial inválida u otro problema
            null
        }
    }

    override suspend fun logout(): Boolean {
        return try {
            auth.signOut()   // Esto cierra sesión en Supabase
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun loginWithGoogle(): UserModel? {
        return try {
            // Iniciamos el flujo de autenticación con Google
            auth.signInWith(Google)

            // Obtenemos el usuario actual después de la autenticación
            val user = auth.currentUserOrNull()

            if (user != null) {
                // Verificamos si el usuario ya existe en nuestra tabla usuarios2
                val existingUser = supabaseClient.postgrest["usuarios2"]
                    .select {
                        filter {
                            eq("id_usuario", user.id)
                        }
                    }.decodeSingleOrNull<Map<String, Any>>()

                // Si es la primera vez que inicia sesión, lo insertamos en la BD
                if (existingUser == null) {
                    supabaseClient.postgrest["usuarios2"].insert(
                        mapOf(
                            "id_usuario" to user.id,
                            "correo" to (user.email ?: ""),
                            "username" to (user.email?.substringBefore("@") ?: "user_${user.id.take(8)}")
                        )
                    )
                }

                UserModel(
                    email = user.email ?: "",
                    id = user.id
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}