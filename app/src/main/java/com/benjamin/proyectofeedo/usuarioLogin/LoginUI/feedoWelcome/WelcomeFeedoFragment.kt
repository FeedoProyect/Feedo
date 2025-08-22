package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoWelcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.FragmentWelcomeFeedoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFeedoFragment : Fragment() {

    private var _binding: FragmentWelcomeFeedoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initListener()
    }

    private fun initListener() {
        binding.botonGoApp.setOnClickListener {
            findNavController().navigate(
                WelcomeFeedoFragmentDirections.actionWelcomeFeedoFragmentToLoginUserFragment()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeFeedoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}