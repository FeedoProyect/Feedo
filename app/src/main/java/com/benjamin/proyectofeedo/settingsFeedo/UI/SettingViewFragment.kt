package com.benjamin.proyectofeedo.settingsFeedo.UI

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.databinding.FragmentSettingViewBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingViewFragment : Fragment() {

    private var _binding: FragmentSettingViewBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun volverAtras(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}