package com.benjamin.proyectofeedo.settingsFeedo.UI.darkMode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.databinding.FragmentDarkModeBinding
import com.benjamin.proyectofeedo.settingsFeedo.domain.model.DarkModeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DarkModeFragment : Fragment() {

    private var _binding: FragmentDarkModeBinding? = null
    private val binding get() = _binding!!

    private val darkModeViewModel by viewModels<DarkModeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initUI()
    }

    private fun observeViewModel() {
        darkModeViewModel.isDarkMode
            .onEach { darkMode ->
                // Remueve temporalmente el listener para evitar loop
                binding.SwDarkMode.setOnCheckedChangeListener(null)
                binding.SwDarkMode.isChecked = darkMode.darkMode
                binding.SwDarkMode.setOnCheckedChangeListener { _, value ->
                    onSwitchChanged(value)
                }

                // Aplica el modo oscuro al iniciar
                applyDarkMode(darkMode.darkMode)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onSwitchChanged(value: Boolean) {
        applyDarkMode(value)
        darkModeViewModel.toggleDarkMode(DarkModeModel(value))
    }

    private fun applyDarkMode(enabled: Boolean) {
        if(enabled) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
    }


    private fun initUI() {
        initListener()
    }

    private fun initListener() {
        binding.imgBackWindowDarkMode.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDarkModeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
