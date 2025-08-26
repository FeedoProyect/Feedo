package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import com.benjamin.proyectofeedo.databinding.FragmentPerfilBinding
import com.benjamin.proyectofeedo.settingsFeedo.UI.home.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initListener()
    }

    private fun initListener() {
        navSetting()
    }

    private fun navSetting() {
        binding.imgSettings.setOnClickListener {
            startRotationAnimation(binding.imgSettings)
        }
    }

    private fun startRotationAnimation(view: View) {
        view.animate()
            .setDuration(500)
            .setInterpolator(LinearInterpolator())
            .rotationBy(360f)
            .withEndAction { intentSetting() }
            .start()
    }


    fun intentSetting(){
        val intent = Intent(requireContext(), SettingActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}