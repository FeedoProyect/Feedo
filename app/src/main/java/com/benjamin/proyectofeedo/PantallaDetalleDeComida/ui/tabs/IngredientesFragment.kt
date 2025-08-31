// com/benjamin/proyectofeedo/pantallasPrincipales/UI/detalleReceta/tabs/IngredientesFragment.kt
package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.databinding.FragmentIngredientesBinding

class IngredientesFragment : Fragment() {

    private var _binding: FragmentIngredientesBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_INGREDIENTES = "key_ingredientes"

        fun newInstance(items: ArrayList<String>) = IngredientesFragment().apply {
            arguments = Bundle().apply { putStringArrayList(KEY_INGREDIENTES, items) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = arguments?.getStringArrayList(KEY_INGREDIENTES) ?: arrayListOf()

        binding.recyclerIngredientes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = IngredientesAdapter(data)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

