package com.benjamin.proyectofeedo.UI.feedoPerfil

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PerfilViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _bio = MutableLiveData<String>()
    val bio: LiveData<String> get() = _bio

    private val _photoUri = MutableLiveData<Uri?>()
    val photoUri: LiveData<Uri?> get() = _photoUri

    fun updateProfile(name: String, bio: String, photoUri: Uri?) {
        _name.value = name
        _bio.value = bio
        _photoUri.value = photoUri
    }
}
