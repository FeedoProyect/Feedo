package com.benjamin.proyectofeedo.settingsFeedo.UI.feedoSetting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.databinding.FragmentSettingViewBinding
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthState
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthUserViewModel
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.homeLogin.InicioAppActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingViewFragment : Fragment() {

    private var _binding: FragmentSettingViewBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthUserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initListener()
    }

    private fun initListener() {
        binding.imgBackSetting.setOnClickListener {
            volverAtras()
        }
        binding.cdModoOscuro.setOnClickListener {
            findNavController().navigate(
                SettingViewFragmentDirections.actionSettingViewFragmentToDarkModeFragment()
            )
        }
        binding.cdQuestion.setOnClickListener {
            findNavController().navigate(
                SettingViewFragmentDirections.actionSettingViewFragmentToInformationOfTeamFragment()
            )
        }
        binding.cdCloseSession.setOnClickListener {
            authViewModel.logout()
            navLoginFragment()
        }
    }

    private fun navLoginFragment(){
        lifecycleScope.launchWhenStarted {
            authViewModel.state.collect { state ->
                when(state) {
                    is AuthState.LoggedOut -> {
                        intentActivitylogout()
                    }
                    is AuthState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun intentActivitylogout(){
        // Lanzar la AuthActivity
        val intent = Intent(requireContext(), InicioAppActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun volverAtras(){
        findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}