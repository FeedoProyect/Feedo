package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.DialogRecetasBinding
import com.benjamin.proyectofeedo.databinding.FragmentAddRecetasBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.FragmentPageAddRecetaAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FragmentPageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRecetaFragment : Fragment() {

    private var _binding: FragmentAddRecetasBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FragmentPageAddRecetaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initTab()
        initListeners()
    }

    private fun initListeners() {
        binding.tvAgregarTituloReceta.setOnClickListener { initDialog() }
    }

    private fun initDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogRecetasBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.botonAddNameComida.setOnClickListener {
            val comidaNombre = dialogBinding.etAddComida.text.toString()

            if (comidaNombre.isNotBlank()) {
                // Poner el texto en el TextView del fragment
                binding.tvAgregarTituloReceta.text = comidaNombre

                // Cerrar el dialog
                dialog.dismiss()
            } else {
                // Opcional: mostrar un error si está vacío
                dialogBinding.etAddComida.error = "Escribe un nombre"
            }
        }

        dialog.show()
    }

    private fun initTab() {
        adapter = FragmentPageAddRecetaAdapter(childFragmentManager, lifecycle)
        binding.viewPage2AgregarComida.adapter = adapter

        val tabTitle = listOf("Ingredientes", "Instrucciones")

        TabLayoutMediator(
            binding.tabLayoutAgregarReceta,
            binding.viewPage2AgregarComida
        ) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecetasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}