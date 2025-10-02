package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.UserUI
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.useCase.GetUserInformationUseCase
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.usecases.UpdateUserInfoUseCase
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject



@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val sessionRepository: SessionRepository,
    private val supabaseClient: SupabaseClient
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserUI?>(null)
    val userInfo: StateFlow<UserUI?> = _userInfo.asStateFlow()

    private val TAG = "PerfilViewModel"

    fun loadCurrentUserInfo() {
        viewModelScope.launch {
            try {
                val userId = sessionRepository.getUserUuid().firstOrNull()
                if (userId.isNullOrEmpty()) {
                    Log.w(TAG, "No hay userId en prefs")
                    return@launch
                }

                val userDomain = getUserInformationUseCase(userId)
                if (userDomain != null) {
                    _userInfo.value = UserUI(
                        username = userDomain.username ?: "",
                        biografia = userDomain.biografia,
                        imagen_perfil = userDomain.imagen_perfil?.let {
                            // Siempre le añadimos un timestamp
                            "$it?t=${System.currentTimeMillis()}"
                        }
                    )
                } else {
                    Log.w(TAG, "Usuario no encontrado para id=$userId")
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadCurrentUserInfo error", e)
            }
        }
    }

    fun uploadImageAndUpdateUser(
        context: Context,
        imageUri: Uri? = null,
        imageBytes: ByteArray? = null,
        username: String,
        biografia: String
    ) {
        viewModelScope.launch {
            try {
                val userId = sessionRepository.getUserUuid().firstOrNull()
                if (userId.isNullOrEmpty()) {
                    Log.w(TAG, "No hay userId en prefs (uploadImageAndUpdateUser)")
                    return@launch
                }

                var imageUrl: String? = null


                val bytesToUpload: ByteArray? = when {
                    imageBytes != null -> imageBytes
                    imageUri != null -> {
                        val inputStream = context.contentResolver.openInputStream(imageUri)
                        val bmp = BitmapFactory.decodeStream(inputStream)
                        val out = ByteArrayOutputStream()
                        bmp.compress(Bitmap.CompressFormat.JPEG, 85, out)
                        out.toByteArray()
                    }
                    else -> null
                }

                if (bytesToUpload != null) {
                    val bucket = supabaseClient.storage.from("foto_perfil")
                    val filePath = "profile_${userId}.jpg"

                    bucket.upload(filePath, bytesToUpload) {
                        upsert = true
                    }


                    imageUrl = bucket.publicUrl(filePath) + "?t=${System.currentTimeMillis()}"
                }

                val finalImageUrl = imageUrl ?: _userInfo.value?.imagen_perfil

                val success = updateUserInfoUseCase(userId, username, biografia, finalImageUrl)
                if (success) {

                    val refreshed = getUserInformationUseCase(userId)
                    refreshed?.let {
                        _userInfo.value = UserUI(
                            username = it.username ?: "",
                            biografia = it.biografia,
                            imagen_perfil = it.imagen_perfil?.plus("?t=${System.currentTimeMillis()}")
                        )
                    }
                } else {
                    Log.w(TAG, "updateUserInfoUseCase devolvió false")
                }

            } catch (e: Exception) {
                Log.e(TAG, "uploadImageAndUpdateUser error", e)
            }
        }
    }

    fun setUserLocal(user: UserUI) {
        _userInfo.value = user
    }
}





