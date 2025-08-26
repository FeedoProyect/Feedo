package com.benjamin.proyectofeedo.settingsFeedo.UI.darkMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjamin.proyectofeedo.settingsFeedo.domain.model.DarkModeModel
import com.benjamin.proyectofeedo.settingsFeedo.domain.useCase.GetDarkModeUseCase
import com.benjamin.proyectofeedo.settingsFeedo.domain.useCase.SaveDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DarkModeViewModel @Inject constructor(
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val saveDarkModeUseCase: SaveDarkModeUseCase
) : ViewModel() {

    val isDarkMode: StateFlow<DarkModeModel> = getDarkModeUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = DarkModeModel(false)
    )

    fun toggleDarkMode(enabled: DarkModeModel){
        viewModelScope.launch {
            saveDarkModeUseCase(enabled)
        }
    }
}