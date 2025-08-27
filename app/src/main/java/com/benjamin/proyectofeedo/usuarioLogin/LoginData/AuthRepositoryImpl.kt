package com.benjamin.proyectofeedo.usuarioLogin.LoginData

import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : AuthRepository {

    override suspend fun register(email: String, password: String): UserModel? {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        val user = client.auth.currentUserOrNull()
        return user?.let {
            UserModel(email = it.email ?: "")
        }
    }

    override suspend fun login(email: String, password: String): UserModel? {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        val user = client.auth.currentUserOrNull()
        return user?.let {
            UserModel(email = it.email ?: "")
        }
    }

    override fun getCurrentUser(): UserModel? {
        val user = client.auth.currentUserOrNull()
        return user?.let {
            UserModel(email = it.email ?: "")
        }
    }

    override suspend fun logout() {
        client.auth.signOut()
    }
}