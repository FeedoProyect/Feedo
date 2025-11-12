package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjamin.proyectofeedo.databinding.FragmentPasosBinding

class PasosFragment : Fragment() {

    private var _binding: FragmentPasosBinding? = null
    private val binding get() = _binding!!
    private var totalPasos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            totalPasos = it.getInt(ARG_PASOS, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPasos.text = "$totalPasos pasos"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PASOS = "pasos"

        fun newInstance(totalPasos: Int) = PasosFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PASOS, totalPasos)
            }
        }
    }
}