package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaSeccionesMenu

import android.os.Bundle
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initTittle()
        initCollectors()
        initAdapter()
    }

    private fun initTittle() {
        val seccionId = args.seccionId
        val titulo = when (seccionId) {
            1 -> "Clásico Argentino"
            2 -> "Ideal para el Mate"
            3 -> "Modo Saludable"
            4 -> "Exprés"
            5 -> "Modo Ahorro"
            else -> "Sección"
        }

        binding.TituloSeccion.text = titulo
    }

    private fun initCollectors() {
        val seccionId = args.seccionId
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                feedoMenuViewModel.state.collect { stateMap ->
                    when(val state = stateMap[seccionId]){
                        is FeedoMenuState.Error -> errorState(state.message)
                        FeedoMenuState.Loading -> loadingState()
                        is FeedoMenuState.Success -> succesState(state.recetas)
                        null -> feedoMenuViewModel.getRecetasPorSeccion(seccionId)
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        listaSeccionesAdapter = ListaSeccionesAdapter(
            auth = supabaseClient.auth,
            onItemClick = { receta -> navigateToDetalle(receta.recetas.id) },
            onItemSelectedFav = { favoritos ->
                feedoMenuViewModel.addComidasFavoritos(favoritos)
                mensajeFavoritos()
            }
        )

        binding.rvComidasSecciones.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listaSeccionesAdapter
        }
    }

    private fun errorState(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun loadingState(){
        binding.ProgressbarSecciones.isVisible = true
        binding.pantallaSecciones.isVisible = false
    }

    private fun succesState(recetas: List<ComidasSeccionMenuModel>) {
        binding.ProgressbarSecciones.isVisible = false
        binding.pantallaSecciones.isVisible = true
        binding.imgSinResultados.isVisible = false
        binding.tvNoSeencontroComida.isVisible = false

        listaSeccionesAdapter.updateList(recetas)
    }

    private fun mensajeFavoritos(){
        Toast.makeText(requireContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToDetalle(recetaId: Int) {
        findNavController().navigate(
            SeccionesFragmentDirections.actionSeccionesFragmentToDetalleRecetaFragment(recetaId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeccionesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}