package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.RecetaUsuarioFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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