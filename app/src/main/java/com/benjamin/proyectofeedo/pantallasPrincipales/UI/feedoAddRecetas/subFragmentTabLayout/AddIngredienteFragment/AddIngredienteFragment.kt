package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjamin.proyectofeedo.databinding.FragmentAgregarIngredienteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddIngredienteFragment : Fragment() {

    private var _binding: FragmentAgregarIngredienteBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        //
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarIngredienteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}