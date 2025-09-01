package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.databinding.FragmentEditarPerfilBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditarPerfilFragment : Fragment() {

    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {

            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }
}
