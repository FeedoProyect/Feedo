package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos


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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.databinding.FragmentCatalogosListBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.listaComidaDestacadaCatalogoAdapter.ComidaDestacadaCatalogoAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.listaDeComidasCatalogosAdapter.CatalogosListComidasAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.CatalogosModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class CatalogosListComidasFragment : Fragment() {

    private var _binding: FragmentCatalogosListBinding? = null
    private val binding get() = _binding!!

    private val catalogosListComidasViewModel by viewModels<CatalogosListComidasViewModel>()

    private lateinit var elAdapter: CatalogosListComidasAdapter
    private lateinit var adapterComidaDestacadaCatalogo: ComidaDestacadaCatalogoAdapter

    private val args: CatalogosListComidasFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initUI()
        catalogosListComidasViewModel.getComidass(args.type.id)
    }

    private fun initUI() {
        initUIState()
        tituloDeCatalogo(args.type)
        initList()
        initAdapters()
        filtradorDecomida()
    }

    private fun filtradorDecomida() {

        val editTextFlow = callbackFlow {
            binding.etSearchFeedCatalogo.doOnTextChanged { text, _, _, _ ->
                trySend(text.toString())
            }
            awaitClose {}
        }

        lifecycleScope.launch {
            editTextFlow
                .debounce(300) // espera 300ms después de la última letra
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        catalogosListComidasViewModel.getComidasBuscadorCatalogo(query)
                        binding.tituloDestacadoCatalogo.isVisible = false
                    } else {
                        catalogosListComidasViewModel.getComidass(args.type.id) // 👈 recargo el catálogo original
                        binding.tituloDestacadoCatalogo.isVisible = true
                    }
                }
        }
    }

    private fun initAdapters() {
        // Adapter para comidas destacadas (horizontal)
        binding.rvComidaDestacadaCatalogo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterComidaDestacadaCatalogo
        }

        // Adapter para comida de abajo
        binding.rvComidasCatalogo.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = elAdapter
        }
    }

    private fun initList() {
        // Adapter para la lista destacada
        adapterComidaDestacadaCatalogo = ComidaDestacadaCatalogoAdapter()


        // Adapter para la lista de comidas del catálogo
        elAdapter = CatalogosListComidasAdapter(
            onItemClick = { receta ->
                // 👇 Navegamos al detalle de la receta
                val action = CatalogosListComidasFragmentDirections
                    .actionCatalogosListComidasFragmentToDetalleRecetaFragment(receta.id)

                findNavController().navigate(action)

            }, onItemSelectedFav = { favoritos ->
                catalogosListComidasViewModel.addComidasFavoritos(favoritos)
            }
        )
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catalogosListComidasViewModel.state.collect {
                    when (it) {
                        is CatalogosListComidasState.Error -> errorState()
                        CatalogosListComidasState.Loading -> loadingState()
                        is CatalogosListComidasState.Success -> succesState(it)
                        CatalogosListComidasState.Empty -> emptyState()
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.ProgressbarCatalogos.isVisible = true
        binding.pantallaCatalogo.isVisible = false
    }

    private fun emptyState(){
        binding.ProgressbarCatalogos.isVisible = false
        binding.pantallaCatalogo.isVisible = true
        binding.rvComidasCatalogo.isVisible = false
        binding.rvComidaDestacadaCatalogo.isVisible = false
        binding.imgSinResultados.isVisible = true
        binding.tvNoSeencontroComida.isVisible = true
    }

    private fun errorState() {
        binding.ProgressbarCatalogos.isVisible = false
    }

    private fun succesState(success: CatalogosListComidasState.Success) {
        binding.ProgressbarCatalogos.isVisible = false
        binding.LayoutBuscadorErroneo.isVisible = true
        binding.imgSinResultados.isVisible = false
        binding.pantallaCatalogo.isVisible = true
        binding.rvComidasCatalogo.isVisible = true
        binding.rvComidaDestacadaCatalogo.isVisible = true
        binding.tvNoSeencontroComida.isVisible = false

        elAdapter.updateList(success.comidas ?: emptyList())
        adapterComidaDestacadaCatalogo.updateListComidaDestacadaCatalogo(success.comidasDestacadas)
    }


    private fun tituloDeCatalogo(catalogosModel: CatalogosModel) {
        binding.TituloCatalogo.text = catalogosModel.titulo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogosListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
