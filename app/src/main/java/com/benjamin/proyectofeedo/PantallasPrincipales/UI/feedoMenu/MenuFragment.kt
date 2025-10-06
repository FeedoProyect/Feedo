package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.GridSpacingItemDecoration
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos.ListaClasicoArgentinoAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaComidaExpres.ListaExpresAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaDeCatalogosAdapter.ListaCatalogosAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaEspecialMate.ListaEspecialMateAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaModoAhorro.ListaModoAhorroAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaModoSaludable.ListaModoSaludableAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.CatalogoInfo
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.CatalogosModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.DialogCatalogosMenuBinding
import com.benjamin.proyectofeedo.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val feedoMenuViewModel by viewModels<FeedoMenuViewModel>()
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private lateinit var listaCatalogosAdapter: ListaCatalogosAdapter
    private lateinit var listaClasicoArgentinoAdapter: ListaClasicoArgentinoAdapter
    private lateinit var listaEspecialMateAdapter: ListaEspecialMateAdapter
    private lateinit var listaModoSaludableAdapter: ListaModoSaludableAdapter
    private lateinit var listaExpresAdapter: ListaExpresAdapter
    private lateinit var listaModoAhorroAdapter: ListaModoAhorroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initCollectors()
        initBuscador()
        initUIState()
        initList()
        initClicksDialogs()
        initAdapters()
    }

    // ----------------------------------------------------------
    // 🔹 Inicialización de diálogos y navegación
    // ----------------------------------------------------------

    private fun initClicksDialogs() {
        binding.piquitoDialogCatalogos.setOnClickListener { dialogCatalogos() }
        binding.piquitoClasicoArgentino.setOnClickListener { pantallaSecciones(1) }
        binding.piquitoIdealMate.setOnClickListener { pantallaSecciones(2) }
        binding.piquitoModoSaludable.setOnClickListener { pantallaSecciones(3) }
        binding.piquitoExpress.setOnClickListener { pantallaSecciones(4) }
        binding.piquitoModoAhorro.setOnClickListener { pantallaSecciones(5) }
    }

    private fun pantallaSecciones(seccionId: Int) {
        findNavController().navigate(
            MenuFragmentDirections.actionMenuFragmentToSeccionesFragment(seccionId)
        )
    }

    private fun dialogCatalogos() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogCatalogosMenuBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val adapterDialog = ListaCatalogosAdapter { catalogo ->
            val type = when (catalogo) {
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

            dialog.dismiss()
            findNavController().navigate(
                MenuFragmentDirections.actionMenuFragmentToCatalogosListComidasFragment(type)
            )
        }

        adapterDialog.updateList(feedoMenuViewModel.catalogos.value)

        dialogBinding.rvDialogCatalogos.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterDialog
            addItemDecoration(GridSpacingItemDecoration(2, 24, true))
        }

        dialog.show()
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            (resources.displayMetrics.heightPixels * 0.7).toInt()
        )
    }

    // ----------------------------------------------------------
    // 🔹 Configuración de adapters
    // ----------------------------------------------------------

    private fun handleFavChange(favorito: FavoritosRequestModel, isFavorite: Boolean) {
        val viewModel = feedoMenuViewModel

        if (isFavorite) {
            Log.d("Favoritos", "🟢 Agregando favorito desde MenuFragment")
            viewModel.addComidasFavoritos(favorito)
        } else {
            Log.d("Favoritos", "🔴 Eliminando favorito desde MenuFragment")
            viewModel.deleteComidasFavoritos(favorito)
        }
    }

    private fun initAdapters() {
        listaClasicoArgentinoAdapter = ListaClasicoArgentinoAdapter(
            auth = supabaseClient.auth,
            onItemClick = { receta -> navigateToDetalle(receta.recetas.id) },
            onItemSelectedFavs = { fav, isFav -> handleFavChange(fav, isFav) }
        )

        listaEspecialMateAdapter = ListaEspecialMateAdapter(
            auth = supabaseClient.auth,
            onItemClick = { receta -> navigateToDetalle(receta.recetas.id) },
            onItemSelectedFavs = { fav, isFav -> handleFavChange(fav, isFav) }
        )

        listaModoSaludableAdapter = ListaModoSaludableAdapter(
            auth = supabaseClient.auth,
            onItemClick = { receta -> navigateToDetalle(receta.recetas.id) },
            onItemSelectedFavs = { fav, isFav -> handleFavChange(fav, isFav) }
        )

        listaExpresAdapter = ListaExpresAdapter(
            auth = supabaseClient.auth,
            listaExpres = emptyList(),
            onItemClick = { receta -> navigateToDetalle(receta.recetas.id) },
            onItemSelectedFav = { fav, isFav -> handleFavChange(fav, isFav) }
        )

        listaModoAhorroAdapter = ListaModoAhorroAdapter(
            auth = supabaseClient.auth,
            onItemClick = { receta -> navigateToDetalle(receta.recetas.id) },
            onItemSelectedFavs = { fav, isFav -> handleFavChange(fav, isFav) }
        )

        binding.rvClasicosArgentinos.adapter = listaClasicoArgentinoAdapter
        binding.rvIdealParaElMate.adapter = listaEspecialMateAdapter
        binding.rvModoSaludable.adapter = listaModoSaludableAdapter
        binding.rvExpres.adapter = listaExpresAdapter
        binding.rvModoAhorro.adapter = listaModoAhorroAdapter
    }

    // ----------------------------------------------------------
    // 🔹 Collectors
    // ----------------------------------------------------------

    private fun initCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                feedoMenuViewModel.state.collect { stateMap ->
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

        // ✅ Configurar TODOS los LayoutManagers
        binding.rvClasicosArgentinos.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvIdealParaElMate.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvModoSaludable.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)  // ⭐ ESTO FALTABA
        binding.rvModoAhorro.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvExpres.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    // ----------------------------------------------------------
    // 🔹 Estados visuales
    // ----------------------------------------------------------

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

    // ----------------------------------------------------------
    // 🔹 Buscador y catálogos
    // ----------------------------------------------------------

    private fun initBuscador() {
        binding.etSearchFeed.setOnClickListener {
            findNavController().navigate(
                MenuFragmentDirections.actionMenuFragmentToBuscadorPrincipalFragment()
            )
        }
    }

    private fun initList() {
        listaCatalogosAdapter = ListaCatalogosAdapter { catalogo ->
            val type = when (catalogo) {
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
        }

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

    // ----------------------------------------------------------
    // 🔹 Navegación al detalle
    // ----------------------------------------------------------

    private fun navigateToDetalle(recetaId: Int) {
        findNavController().navigate(
            MenuFragmentDirections.actionMenuFragmentToDetalleRecetaFragment(recetaId)
        )
    }

    // ----------------------------------------------------------
    // ✅ NUEVO: refrescar favoritos al volver al menú
    // ----------------------------------------------------------

    override fun onResume() {
        super.onResume()
        recarganFavoritos()
    }

    private fun recarganFavoritos() {
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: return
        Log.d("Favoritos", "♻️ Recargando favoritos al volver al menú")
        feedoMenuViewModel.getComidaFavoritos(userId)
    }
}