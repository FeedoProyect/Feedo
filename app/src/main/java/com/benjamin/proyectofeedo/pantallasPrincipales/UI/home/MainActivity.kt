package com.benjamin.proyectofeedo.pantallasPrincipales.UI.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initNavegation()
        configurationNav()
    }

    private fun configurationNav() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.menuFragment,
                R.id.catalogosListComidasFragment,
                R.id.buscadorPrincipalFragment,
                R.id.perfilFragment,
                R.id.addRecetasFragment -> {
                    binding.bottomBar.visibility = View.VISIBLE
                }
                else -> {
                    // para fragments como settings, darkMode, etc.
                    binding.bottomBar.visibility = View.GONE
                }
            }
        }
    }

    private fun initNavegation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController

        binding.bottomBar.setupWithNavController(navController)

    }
}