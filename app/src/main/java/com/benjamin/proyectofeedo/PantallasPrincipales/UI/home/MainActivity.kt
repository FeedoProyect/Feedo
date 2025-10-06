package com.benjamin.proyectofeedo.PantallasPrincipales.UI.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ActivityMainBinding
import com.benjamin.proyectofeedo.settingsFeedo.UI.darkMode.DarkModeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val darkModeViewModel by viewModels<DarkModeViewModel>()

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
        observeDarkMode()
    }

    private fun observeDarkMode() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                darkModeViewModel.isDarkMode.collect { darkMode ->
                    withContext(Dispatchers.Main) {
                        if (darkMode.darkMode) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                    }
                }
            }
        }
    }

    private fun configurationNav() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.menuFragment,
                R.id.addRecetasFragment,
                R.id.perfilFragment -> {
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
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController

        binding.bottomBar.setupWithNavController(navController)

    }
}