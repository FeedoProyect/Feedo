package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.databinding.FragmentMenuBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaEspecialMate.ListaEspecialMateAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaModoAhorro.ListaModoAhorroAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos.ListaClasicoArgentinoAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaComidaExpres.ListaExpresAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaModoSaludable.ListaModoSaludableAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaDeCatalogosAdapter.ListaCatalogosAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.CatalogoInfo
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.CatalogosModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val feedoMenuViewModel by viewModels<FeedoMenuViewModel>()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaCatalogosAdapter: ListaCatalogosAdapter

    private lateinit var listaClasicoArgentinoAdapter: ListaClasicoArgentinoAdapter
    private lateinit var listaEspecialMateAdapter: ListaEspecialMateAdapter
    private lateinit var listaModoSaludableAdapter: ListaModoSaludableAdapter
    private lateinit var listaExpresAdapter: ListaExpresAdapter
    private lateinit var listaModoAhorroAdapter: ListaModoAhorroAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initAdapters()
        initCollectors()
        initBuscador()
        initUIState()
        initList()
    }

    private fun initAdapters() {
        // 🔥 Paso el onItemClick a cada adapter
        listaClasicoArgentinoAdapter = ListaClasicoArgentinoAdapter { receta ->
            navigateToDetalle(receta.id)
        }
        listaEspecialMateAdapter = ListaEspecialMateAdapter { receta ->
            navigateToDetalle(receta.id)
        }
        listaModoSaludableAdapter = ListaModoSaludableAdapter { receta ->
            navigateToDetalle(receta.id)
        }
        listaExpresAdapter = ListaExpresAdapter { receta ->
            navigateToDetalle(receta.id)
        }
        listaModoAhorroAdapter = ListaModoAhorroAdapter { receta ->
            navigateToDetalle(receta.id)
        }

        binding.rvClasicosArgentinos.adapter = listaClasicoArgentinoAdapter
        binding.rvIdealParaElMate.adapter = listaEspecialMateAdapter
        binding.rvModoSaludable.adapter = listaModoSaludableAdapter
        binding.rvExpres.adapter = listaExpresAdapter
        binding.rvModoAhorro.adapter = listaModoAhorroAdapter
    }

    private fun initCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                feedoMenuViewModel.state.collect { stateMap ->

                    launch {
                        val hasError = stateMap.values.any { it is FeedoMenuState.Error }
                        val isLoading = stateMap.values.all { it is FeedoMenuState.Loading }

                        when {
                            hasError -> errorState()
                            isLoading -> loadingState()
                            else -> {
                                successState()
                                stateMap.forEach { (seccionId, state) ->
                                    if (state is FeedoMenuState.Success) {
                                        when (seccionId) {
                                            1 -> listaClasicoArgentinoAdapter.updateListClasicoArgentino(state.recetas)
                                            2 -> listaEspecialMateAdapter.updateListEspecialMate(state.recetas)
                                            3 -> listaModoSaludableAdapter.updateListModoSaludable(state.recetas)
                                            4 -> listaExpresAdapter.updateListExpres(state.recetas)
                                            5 -> listaModoAhorroAdapter.updateListModoAhorro(state.recetas)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.rvClasicosArgentinos.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvIdealParaElMate.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvModoSaludable.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvExpres.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvModoAhorro.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun successState() {
        binding.MenuFeedo.visibility = View.VISIBLE
        binding.ProgresBarMenu.visibility = View.GONE
    }

    private fun errorState() {
        binding.MenuFeedo.visibility = View.GONE
        binding.ProgresBarMenu.visibility = View.GONE
    }

    private fun loadingState() {
        binding.MenuFeedo.visibility = View.GONE
        binding.ProgresBarMenu.visibility = View.VISIBLE
    }

    private fun initBuscador() {
        binding.etSearchFeed.setOnClickListener {
            findNavController().navigate(
                MenuFragmentDirections.actionMenuFragmentToBuscadorPrincipalFragment()
            )
        }
    }

    private fun initList() {
        listaCatalogosAdapter = ListaCatalogosAdapter(onItemSelected = {

            val type = when (it) {
                CatalogoInfo.Almuerzos -> CatalogosModel.Almuerzos
                CatalogoInfo.Aperitivos -> CatalogosModel.Aperitivos
                CatalogoInfo.Cena -> CatalogosModel.Cena
                CatalogoInfo.Desayunos -> CatalogosModel.Desayunos
                CatalogoInfo.Elaboradas -> CatalogosModel.Elaboradas
                CatalogoInfo.Ensaladas -> CatalogosModel.Ensaladas
                CatalogoInfo.Meriendas -> CatalogosModel.Meriendas
                CatalogoInfo.Postres -> CatalogosModel.Postres
                CatalogoInfo.Rapidas -> CatalogosModel.Rapidas
                CatalogoInfo.Diabeticos -> CatalogosModel.Diabeticos
                CatalogoInfo.SopasGuisos -> CatalogosModel.Sopas_y_Guisos
                CatalogoInfo.Veganas -> CatalogosModel.Veganas
                CatalogoInfo.Vegetarianas -> CatalogosModel.Vegetarianas
            }

            findNavController().navigate(
                MenuFragmentDirections.actionMenuFragmentToCatalogosListComidasFragment(type)
            )
        })

        binding.rvCatolgo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listaCatalogosAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                feedoMenuViewModel.catalogos.collect {
                    listaCatalogosAdapter.updateList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    // para navegar al detalle
    private fun navigateToDetalle(recetaId: Int) {
        findNavController().navigate(
            MenuFragmentDirections.actionMenuFragmentToDetalleRecetaFragment(recetaId)

        )
    }
}
