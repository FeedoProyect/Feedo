package com.benjamin.proyectofeedo.usuarioLogin.LoginData

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val supabaseClient: SupabaseClient
) : AuthRepository {

    override suspend fun register(email: String, password: String): UserModel? {
        return try {
            val userInfo: UserInfo? = auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }


            if (userInfo != null) {
                // Inserción simple en tabla usuarios
                supabaseClient.postgrest["usuarios2"].insert(
                    mapOf(
                        "id_usuario" to userInfo.id,
                        "correo" to (userInfo.email ?: email)
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

}

//    override suspend fun loginWithGoogle(): UserModel? {
//        return try {
//            auth.signInWith(Google)
//
//            val user = auth.currentUserOrNull()
//            if (user != null) {
//                UserModel(
//                    email = user.email ?: "",
//                    id = user.id
//                )
//            } else null
//        } catch (e: Exception) {
//            null
//        }
//    }