package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.ListaAddIngredienteAdapter.AddIngredientAdapter
import com.benjamin.proyectofeedo.databinding.DialogRecetasBinding
import com.benjamin.proyectofeedo.databinding.FragmentAgregarIngredienteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddIngredienteFragment : Fragment() {

    private var _binding: FragmentAgregarIngredienteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterAddIngrediente: AddIngredientAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initAdapters()
    }

    private fun initAdapters() {
        adapterAddIngrediente = AddIngredientAdapter()

        binding.rvAddIngredientes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterAddIngrediente
        }

        val listaVacia = binding.rvAddIngredientes

        if(listaVacia.isEmpty()){
            binding.parentIngrediente.setOnClickListener { initDialogIngrediente() }
        } else {
            binding.tvAddIngrediente.setOnClickListener { initDialogIngrediente() }
            binding.imgAddIngrediente.setOnClickListener { initDialogIngrediente() }
        }
    }

    private fun initDialogIngrediente(){
        val dialogAddIngrediente = Dialog(requireContext())
        val dialogBindingAddIngrediente = DialogRecetasBinding.inflate(layoutInflater)
        dialogAddIngrediente.setContentView(dialogBindingAddIngrediente.root)

        dialogBindingAddIngrediente.botonAddNameComida.setOnClickListener {
            val ingrediente = dialogBindingAddIngrediente.etAddComida.text.toString()

            if(ingrediente.isNotEmpty()){
                adapterAddIngrediente.addIngrediente(ingrediente)
                dialogAddIngrediente.dismiss()
            }
        }
        dialogAddIngrediente.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarIngredienteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}