package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjamin.proyectofeedo.databinding.FragmentAddRecetasBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRecetaFragment : Fragment() {

    private var _binding: FragmentAddRecetasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecetasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}