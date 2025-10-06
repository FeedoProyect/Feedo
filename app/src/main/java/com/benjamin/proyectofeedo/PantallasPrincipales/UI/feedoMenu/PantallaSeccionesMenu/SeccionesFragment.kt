package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaSeccionesMenu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.FeedoMenuState
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.FeedoMenuViewModel
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaSeccionesMenu.listaDeComidasSecciones.ListaSeccionesAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.databinding.FragmentSeccionesBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SeccionesFragment : Fragment() {

    private var _binding: FragmentSeccionesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private val feedoMenuViewModel by viewModels<FeedoMenuViewModel>()

    private lateinit var listaSeccionesAdapter: ListaSeccionesAdapter

    private val args: SeccionesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeccionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FeedoDebug", "🟢 SeccionesFragment creado (onViewCreated)")
        initUI()
    }

    private fun initUI() {
        Log.d("FeedoDebug", "⚙️ initUI() iniciado")
        initTitle()
        initCollectors()
        initAdapter()
    }

    private fun initTitle() {
        val seccionId = args.seccionId
        val titulo = when (seccionId) {
            1 -> "Clásico Argentino"
            2 -> "Ideal para el Mate"
            3 -> "Modo Saludable"
            4 -> "Exprés"
            5 -> "Modo Ahorro"
            else -> "Sección"
        }
        Log.d("FeedoDebug", "📌 Título de sección: $titulo (id=$seccionId)")
        binding.TituloSeccion.text = titulo
    }

    private fun initCollectors() {
        val seccionId = args.seccionId
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                feedoMenuViewModel.state.collect { stateMap ->
                    when (val state = stateMap[seccionId]) {
                        is FeedoMenuState.Error -> {
                            Log.e("FeedoDebug", "❌ ErrorState: ${state.message}")
                            errorState(state.message)
                        }
                        FeedoMenuState.Loading -> {
                            Log.d("FeedoDebug", "⏳ LoadingState (sección $seccionId)")
                            loadingState()
                        }
                        is FeedoMenuState.Success -> {
                            Log.d("FeedoDebug", "✅ SuccessState con ${state.recetas.size} recetas")
                            successState(state.recetas)
                        }
                        null -> {
                            Log.w("FeedoDebug", "⚠️ No hay estado previo, llamando a getRecetasPorSeccion($seccionId)")
                            feedoMenuViewModel.getRecetasPorSeccion(seccionId)
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        Log.d("FeedoDebug", "🎨 Inicializando adapter")
        listaSeccionesAdapter = ListaSeccionesAdapter(
            auth = supabaseClient.auth,
            onItemClick = { receta ->
                Log.d("FeedoDebug", "👉 Click en receta id=${receta.recetas.id}")
                navigateToDetalle(receta.recetas.id)
            },
            onItemSelectedFav = { favoritos, nuevoEstado ->
                Log.d(
                    "FeedoDebug",
                    "❤️ Toggle favorito: recetaId=${favoritos.recetaId}, usuarioId=${favoritos.usuarioId}, nuevoEstado=$nuevoEstado"
                )

                if (nuevoEstado) {
                    Log.d("FeedoDebug", "🟢 Agregando a favoritos en ViewModel")
                    feedoMenuViewModel.addComidasFavoritos(favoritos)
                    showToast("Agregado a favoritos")
                } else {
                    Log.d("FeedoDebug", "🔴 Eliminando de favoritos en ViewModel")
                    feedoMenuViewModel.deleteComidasFavoritos(favoritos)
                    showToast("Eliminado de favoritos")
                }
            }
        )

        binding.rvComidasSecciones.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = listaSeccionesAdapter
        }
    }

    private fun errorState(msg: String) {
        binding.ProgressbarSecciones.isVisible = false
        binding.pantallaSecciones.isVisible = false
        showToast(msg)
    }

    private fun loadingState() {
        binding.ProgressbarSecciones.isVisible = true
        binding.pantallaSecciones.isVisible = false
    }

    private fun successState(recetas: List<ComidasSeccionMenuModel>) {
        Log.d("FeedoDebug", "📦 Actualizando adapter con ${recetas.size} recetas")
        binding.ProgressbarSecciones.isVisible = false
        binding.pantallaSecciones.isVisible = true

        binding.imgSinResultados.isVisible = recetas.isEmpty()
        binding.tvNoSeencontroComida.isVisible = recetas.isEmpty()

        listaSeccionesAdapter.updateList(recetas)
    }

    private fun showToast(mensaje: String) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToDetalle(recetaId: Int) {
        Log.d("FeedoDebug", "➡️ Navegando al detalle de receta $recetaId")
        findNavController().navigate(
            SeccionesFragmentDirections.actionSeccionesFragmentToDetalleRecetaFragment(recetaId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
