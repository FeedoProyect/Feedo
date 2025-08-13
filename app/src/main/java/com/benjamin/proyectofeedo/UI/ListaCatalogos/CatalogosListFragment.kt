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
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.FragmentCatalogosListBinding
import com.benjamin.proyectofeedo.domain.model.CatalogosModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogosListFragment : Fragment() {

    private var _binding: FragmentCatalogosListBinding? = null
    private val binding get() = _binding!!

    private val catalogosListViewModel by viewModels<CatalogosListViewModel>()

    private val args: CatalogosListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
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
                        is CatalogosListState.Error -> erroState()
                        CatalogosListState.Loading -> loadingState()
                        is CatalogosListState.Succes -> succesState()
                    }
                }
            }
        }
    }

    private fun loadingState(){

    }

    private fun erroState(){

    }

    private fun succesState(){

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