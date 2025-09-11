package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.homeLogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ActivityInicioAppBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.home.MainActivity
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.SessionRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InicioAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioAppBinding

    @Inject lateinit var sessionRepository: SessionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            val uuid = sessionRepository.getUserUuid().first()
            if (uuid != null) {
                startActivity(Intent(this@InicioAppActivity, MainActivity::class.java))
                finish()
            } else {
                // Mostrar tu pantalla de bienvenida/login
                binding = ActivityInicioAppBinding.inflate(layoutInflater)
                setContentView(binding.root)
            }
        }
    }
}