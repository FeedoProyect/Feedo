package com.benjamin.proyectofeedo.settingsFeedo.UI.feedoInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.databinding.FragmentInformationOfTeamBinding
import com.benjamin.proyectofeedo.settingsFeedo.UI.darkMode.DarkModeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InformationOfTeamFragment : Fragment() {

    private var _binding: FragmentInformationOfTeamBinding? = null
    private val binding get() = _binding!!

    // Inyectar el ViewModel de DarkMode
    private val darkModeViewModel by activityViewModels<DarkModeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationOfTeamBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        observeDarkMode()
        initListeners()
    }

    private fun observeDarkMode() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                darkModeViewModel.isDarkMode.collect { darkMode ->
                    if (darkMode.darkMode) {
                        // Mostrar fondo dark
                        binding.fondoSobreNosotrosDark.visibility = View.VISIBLE
                        binding.fondoSobreNosotros.visibility = View.GONE
                    } else {
                        // Mostrar fondo light
                        binding.fondoSobreNosotros.visibility = View.VISIBLE
                        binding.fondoSobreNosotrosDark.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.tvPronto.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}