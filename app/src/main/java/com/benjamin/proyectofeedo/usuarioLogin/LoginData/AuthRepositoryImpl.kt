package com.benjamin.proyectofeedo.usuarioLogin.LoginData

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserDbModel
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.modelUser.UserModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
            e.printStackTrace()
            null
        }
    }

    override suspend fun logout(): Boolean {
        return try {
            auth.signOut()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun loginWithGoogle(activity: Activity): UserModel? {
        return try {
            val credentialManager = CredentialManager.create(activity)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId("225489054641-7rnqhgc1g9j32m8otpdqukvdnhiu4koj.apps.googleusercontent.com")
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = withContext(Dispatchers.Main) {
                credentialManager.getCredential(
                    request = request,
                    context = activity,
                )
            }

            val credential = result.credential
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                auth.signInWith(IDToken) {
                    this.idToken = idToken
                    provider = Google
                }

                val user = auth.currentUserOrNull()

                if (user != null) {
                    val existingUser = supabaseClient.postgrest["usuarios2"]
                        .select { filter { eq("id_usuario", user.id) } }
                        .decodeSingleOrNull<UserDbModel>()

                    if (existingUser == null) {
                        val autoUsername =
                            user.email?.substringBefore("@") ?: "user_${user.id.take(8)}"

                        supabaseClient.postgrest["usuarios2"].insert(
                            mapOf(
                                "id_usuario" to user.id,
                                "correo" to (user.email ?: ""),
                                "username" to autoUsername
                            )
                        )
                    }

                    return UserModel(email = user.email ?: "", id = user.id)

                } else null
            } else null
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}