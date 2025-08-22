package com.benjamin.proyectofeedo.UI.feedoPerfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benjamin.proyectofeedo.databinding.FragmentPerfilBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}