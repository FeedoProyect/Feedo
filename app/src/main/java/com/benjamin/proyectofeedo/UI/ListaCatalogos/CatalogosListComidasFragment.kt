package com.benjamin.proyectofeedo.UI.ListaCatalogos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.benjamin.proyectofeedo.UI.ListaCatalogos.listaDeComidasCatalogosAdapter.CatalogosListComidasAdapter
import com.benjamin.proyectofeedo.databinding.FragmentCatalogosListBinding
import com.benjamin.proyectofeedo.domain.model.CatalogosModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogosListComidasFragment : Fragment() {

    private var _binding: FragmentCatalogosListBinding? = null
    private val binding get() = _binding!!

    private val catalogosListViewModel by viewModels<CatalogosListComidasViewModel>()

    lateinit var elAdapter: CatalogosListComidasAdapter

    private val args: CatalogosListComidasFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializamos el adapter vacío
        elAdapter = CatalogosListComidasAdapter()
        binding.rvComidasCatalogo.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = elAdapter
        }

        initUI()
        catalogosListViewModel.getComidass(args.type.name)
    }

    private fun initUI() {
        initUIState()
        tituloDeCatalogo(args.type)
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle (Lifecycle.State.STARTED){
                catalogosListViewModel.state.collect {
                    when(it){
                        is CatalogosListComidasState.Error -> erroState()
                        CatalogosListComidasState.Loading -> loadingState()
                        is CatalogosListComidasState.Success -> succesState(it)
                    }
                }
            }
        }
    }

    private fun loadingState(){
        binding.progresBar.isVisible = true
    }

    private fun erroState(){
        binding.progresBar.isVisible = false
    }

    private fun succesState(success: CatalogosListComidasState.Success) {
        binding.progresBar.isVisible = false

        // Actualizamos la lista del adapter, sin recrearlo
        elAdapter.updateList(success.comidas)

    }


    private fun tituloDeCatalogo(catalogosModel: CatalogosModel){
        binding.TituloCatalogo.text = catalogosModel.name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogosListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}