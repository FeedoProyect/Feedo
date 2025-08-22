package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoRegisterAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.FragmentRegisterAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}