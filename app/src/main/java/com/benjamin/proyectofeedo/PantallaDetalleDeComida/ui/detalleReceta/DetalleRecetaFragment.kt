package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.benjamin.proyectofeedo.databinding.FragmentDetalleRecetaBinding
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta.DetalleRecetaViewModel
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

        binding.viewPager.adapter = DetallePagerAdapter(this, arrayListOf(), arrayListOf())
        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = if (pos == 0) "Ingredientes" else "Preparación"
        }.also { it.attach() }

        Log.d("DetalleRecetaFragment", "Voy a cargar receta con id=${args.recetaId}")

        viewModel.load(args.recetaId)


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { receta ->
                    Log.d("DetalleRecetaFragment", "StateFlow emitió: $receta")

                    if (receta == null) {
                        Log.d("DetalleRecetaFragment", "Receta aún es null, esperando datos…")
                        return@collect
                    }

                    // Tenemos receta ✅
                    Log.d("DetalleRecetaFragment", "Receta cargada: ${receta.titulo} con ${receta.ingredientes.size} ingredientes y ${receta.pasos.size} pasos")

                    binding.tvTituloReceta.text = receta.titulo
                    Glide.with(this@DetalleRecetaFragment)
                        .load(receta.imagen)
                        .into(binding.ivReceta)

                    // Reemplazamos adapter con datos reales
                    binding.viewPager.adapter = DetallePagerAdapter(
                        this@DetalleRecetaFragment,
                        ArrayList(receta.ingredientes),
                        ArrayList(receta.pasos)
                    )

                    // Re-adjuntamos mediator
                    mediator?.detach()
                    mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
                        tab.text = if (pos == 0) "Ingredientes" else "Preparación"
                    }.also { it.attach() }
                }
            }
        }
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