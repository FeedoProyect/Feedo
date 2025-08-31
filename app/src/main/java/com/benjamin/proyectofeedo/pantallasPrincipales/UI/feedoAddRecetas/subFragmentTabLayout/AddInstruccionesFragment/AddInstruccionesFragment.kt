package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddInstruccionesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjamin.proyectofeedo.databinding.FragmentAgregarInstruccionesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddInstruccionesFragment : Fragment() {

    private var _binding: FragmentAgregarInstruccionesBinding? = null
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
        _binding = FragmentAgregarInstruccionesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}