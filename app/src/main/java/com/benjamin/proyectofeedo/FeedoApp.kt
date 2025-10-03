package com.benjamin.proyectofeedo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.benjamin.proyectofeedo.settingsFeedo.domain.ThemeRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FeedoApp : Application() {

    @Inject
    lateinit var themeRepository: ThemeRepository

    override fun onCreate() {
        super.onCreate()

        // Corremos en Main porque afecta la UI
        CoroutineScope(Dispatchers.Main).launch {
            themeRepository.getDarkMode().collect { darkMode ->
                val mode = if (darkMode.darkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}
