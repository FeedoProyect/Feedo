package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.PantallaFotoIngredientes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.benjamin.proyectofeedo.databinding.FragmentFotoIngredientesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FotoIngredientesFragment : Fragment() {

    private var _binding: FragmentFotoIngredientesBinding? = null
    private val binding get() = _binding!!

    private val fotoIngredienteViewModel by viewModels<FotoIngredienteViewModel>()

    private lateinit var adapterFotoIngrediente: FtIngredAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initAdapter()
        initState()
        buttonBack()
        initBuscador()
    }

    private fun buttonBack() {
        binding.cdBackWindow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        adapterFotoIngrediente = FtIngredAdapter(
            onItemClick = { ingrediente ->
                // Guarda el OBJETO COMPLETO
                findNavController().previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("selected_ingredient", ingrediente)

                findNavController().popBackStack()
            }
        )

        binding.rvFotoIngredientes.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterFotoIngrediente
        }
    }

    private fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fotoIngredienteViewModel.state.collect {
                    when (it) {
                        is FotosIngredientesState.Error -> errorState()
                        FotosIngredientesState.Loading -> loadingState()
                        is FotosIngredientesState.Success -> succesState(it)
                        FotosIngredientesState.Empty -> emptyState()
                    }
                }
            }
        }
    }

    private fun errorState() {

    }

    private fun loadingState() {

    }

    private fun succesState(success: FotosIngredientesState.Success) {
        adapterFotoIngrediente.updateList(success.ingredientes)
    }

    private fun emptyState() {
        adapterFotoIngrediente.updateList(emptyList())
    }

    private fun initBuscador() {
        val editTextFlow = callbackFlow {
            binding.etPantallaBuscadorIngredientes.doOnTextChanged { text, _, _, _ ->
                trySend(text.toString())
            }
            awaitClose {}
        }

        lifecycleScope.launch {
            editTextFlow
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    fotoIngredienteViewModel.getFotosIngredientes(query)
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFotoIngredientesBinding.inflate(layoutInflater)
        return binding.root
    }
}