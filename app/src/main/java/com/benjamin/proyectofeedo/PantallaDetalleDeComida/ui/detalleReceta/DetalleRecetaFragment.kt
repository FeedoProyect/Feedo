package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.FragmentDetalleRecetaBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetalleRecetaFragment : Fragment() {

    private var _binding: FragmentDetalleRecetaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetalleRecetaViewModel by viewModels()
    private val args: DetalleRecetaFragmentArgs by navArgs()
    private var mediator: TabLayoutMediator? = null

    @Inject
    lateinit var supabase: SupabaseClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleRecetaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heartImage: ImageView = binding.imgFavDetalle

        viewLifecycleOwner.lifecycleScope.launch {
            val user = supabase.auth.currentUserOrNull()
            user?.let {
                viewModel.setUserId(it.id)
                viewModel.load(args.recetaId)
            }
        }

        var previousFav: Boolean? = null
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavorite.collect { isFav ->
                    if (isFav == null) {
                        binding.imgFavDetalle.visibility = View.INVISIBLE
                        binding.btnFavDetalle.isEnabled = false
                        return@collect
                    }

                    binding.imgFavDetalle.visibility = View.VISIBLE
                    binding.btnFavDetalle.isEnabled = true

                    val drawableRes = if (isFav)
                        R.drawable.avd_heart_fill
                    else
                        R.drawable.avd_heart_unfill

                    val drawable = ContextCompat.getDrawable(requireContext(), drawableRes)
                    heartImage.setImageDrawable(drawable)

                    if (previousFav != null && previousFav != isFav) {
                        ObjectAnimator.ofFloat(heartImage, View.SCALE_X, 1f, 1.2f, 1f).apply {
                            duration = 180; start()
                        }
                        ObjectAnimator.ofFloat(heartImage, View.SCALE_Y, 1f, 1.2f, 1f).apply {
                            duration = 180; start()
                        }
                    }
                    previousFav = isFav
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isProcessing.collect { processing ->
                    binding.btnFavDetalle.isEnabled =
                        !processing && (viewModel.isFavorite.value != null)
                }
            }
        }

        binding.btnFavDetalle.setOnClickListener {
            if (!viewModel.isProcessing.value) {
                viewModel.toggleFavorite(args.recetaId)
            }
        }

        binding.btnCerrar.setOnClickListener { findNavController().popBackStack() }

        binding.viewPage2Detalle.adapter = DetallePagerAdapter(this, arrayListOf(), arrayListOf())
        mediator = TabLayoutMediator(binding.tabLayoutDetalle, binding.viewPage2Detalle) { tab, pos ->
            tab.text = if (pos == 0) "Ingredientes" else "Preparación"
        }.also { it.attach() }

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
        binding.imgReceta.isVisible = false
    }

    private fun errorState() {
        binding.progressBarDetalle.isVisible = false
        binding.cardDetalleReceta.isVisible = true
        binding.imgReceta.isVisible = true
        binding.tvTituloReceta.text = "Ocurrió un error"
        binding.imgReceta.setImageDrawable(null)
    }

    private fun successState(state: DetalleRecetaState.Success) {
        val receta = state.receta
        binding.progressBarDetalle.isVisible = false
        binding.cardDetalleReceta.isVisible = true
        binding.imgReceta.isVisible = true

        binding.tvTituloReceta.text = receta.titulo
        Picasso.get().load(receta.imagen).error(R.drawable.img_error).into(binding.imgReceta)

        val pasosList = receta.pasos?.mapIndexed { index, paso ->
            "${index + 1}. ${paso.trim()}"
        }?.filter { it.isNotEmpty() } ?: emptyList()

        binding.viewPage2Detalle.adapter = DetallePagerAdapter(
            this, ArrayList(receta.ingredientes), ArrayList(pasosList)
        )

        mediator?.detach()
        mediator = TabLayoutMediator(binding.tabLayoutDetalle, binding.viewPage2Detalle) { tab, pos ->
            tab.text = if (pos == 0) "Ingredientes" else "Instrucciones"
        }.also { it.attach() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        _binding = null
    }
}








