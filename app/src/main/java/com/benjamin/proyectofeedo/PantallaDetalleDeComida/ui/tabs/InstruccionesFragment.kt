// com/benjamin/proyectofeedo/pantallasPrincipales/UI/detalleReceta/tabs/InstruccionesFragment.kt
package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.databinding.FragmentListaSimpleBinding

class InstruccionesFragment : Fragment() {

    private var _binding: FragmentListaSimpleBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_PASOS = "key_pasos"
        fun newInstance(items: ArrayList<String>) = InstruccionesFragment().apply {
            arguments = Bundle().apply { putStringArrayList(KEY_PASOS, items) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListaSimpleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = arguments?.getStringArrayList(KEY_PASOS) ?: arrayListOf()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SimpleTextAdapter(data)
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
