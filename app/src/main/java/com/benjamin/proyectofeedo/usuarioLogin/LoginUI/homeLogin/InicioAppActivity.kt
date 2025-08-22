package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.homeLogin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ActivityInicioAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InicioAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInicioAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}