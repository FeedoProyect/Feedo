package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benjamin.proyectofeedo.databinding.FragmentTiempoBinding

class TiempoFragment : Fragment() {

    private var _binding: FragmentTiempoBinding? = null
    private val binding get() = _binding!!
    private var tiempo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tiempo = it.getString(ARG_TIEMPO, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTiempoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTiempo.text = tiempo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TIEMPO = "tiempo"

        fun newInstance(tiempo: String) = TiempoFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TIEMPO, tiempo)
            }
        }
    }
}