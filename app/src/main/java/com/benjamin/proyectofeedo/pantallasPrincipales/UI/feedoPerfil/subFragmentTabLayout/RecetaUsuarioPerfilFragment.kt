package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.FragmentRecetaUsuarioPerfilBinding

class RecetaUsuarioPerfilFragment : Fragment() {

    private var _binding: FragmentRecetaUsuarioPerfilBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecetaUsuarioPerfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}