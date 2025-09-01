package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.benjamin.proyectofeedo.databinding.FragmentDetalleRecetaBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleRecetaFragment : Fragment() {

    private var _binding: FragmentDetalleRecetaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetalleRecetaViewModel by viewModels()
    private val args: DetalleRecetaFragmentArgs by navArgs()

    private var mediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCerrar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.viewPage2Detalle.adapter = DetallePagerAdapter(this, arrayListOf(), arrayListOf())
        mediator = TabLayoutMediator(binding.tabLayoutDetalle, binding.viewPage2Detalle) { tab, pos ->
            tab.text = if (pos == 0) "Ingredientes" else "Preparación"
        }.also { it.attach() }

        viewModel.load(args.recetaId)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is DetalleRecetaState.Loading -> loadingState()
                        is DetalleRecetaState.Error -> errorState()
                        is DetalleRecetaState.Success -> successState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBarDetalle.isVisible = true
        binding.cardDetalleReceta.isVisible = false
        binding.ivReceta.isVisible = false
    }

    private fun errorState() {
        binding.progressBarDetalle.isVisible = false
        binding.cardDetalleReceta.isVisible = true
        binding.ivReceta.isVisible = true
        binding.tvTituloReceta.text = "Ocurrió un error"
        binding.ivReceta.setImageDrawable(null)
    }

    private fun successState(state: DetalleRecetaState.Success) {
        val receta = state.receta

        binding.progressBarDetalle.isVisible = false
        binding.cardDetalleReceta.isVisible = true
        binding.ivReceta.isVisible = true

        binding.tvTituloReceta.text = receta.titulo
        Glide.with(this)
            .load(receta.imagen)
            .into(binding.ivReceta)

        binding.viewPage2Detalle.adapter = DetallePagerAdapter(
            this,
            ArrayList(receta.ingredientes),
            ArrayList(receta.pasos)
        )

        mediator?.detach()
        mediator = TabLayoutMediator(binding.tabLayoutDetalle, binding.viewPage2Detalle) { tab, pos ->
            tab.text = if (pos == 0) "Ingredientes" else "Instrucciones"
        }.also { it.attach() }
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleRecetaBinding.inflate(inflater, container, false)
        return binding.root
    }
}

