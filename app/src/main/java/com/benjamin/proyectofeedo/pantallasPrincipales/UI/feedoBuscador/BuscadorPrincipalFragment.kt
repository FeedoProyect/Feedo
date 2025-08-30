package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalAdapter.BuscadorPrincipalAdapter
import com.benjamin.proyectofeedo.databinding.FragmentBuscadorPrincipalBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuscadorPrincipalFragment : Fragment() {

    private var _binding: FragmentBuscadorPrincipalBinding? = null
    private val binding get() = _binding!!

    private val buscadorPrincipalViewModel by viewModels<BuscadorPrincipalViewModel>()

    lateinit var adapterBuscador: BuscadorPrincipalAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }


    private fun initUI() {
        initList()
        initStatee()
        buttonBack()
        initEditText()
    }

    private fun actualizarResultados(lista: List<ComidasModel>) {
        val response = "Encontramos ${lista.size} resultados en relacion a tu busqueda"
        binding.tvResultadosBusqueda.text = response
    }

    private fun initList() {
        adapterBuscador = BuscadorPrincipalAdapter()
        binding.rvListaComidasBuscador.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = adapterBuscador
        }
    }

    private fun initStatee() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscadorPrincipalViewModel.state.collect {
                    when (it) {
                        is BuscadorPrincipalState.Error -> errorState()
                        BuscadorPrincipalState.Loading -> loadingState()
                        is BuscadorPrincipalState.Success -> succesState(it)
                        BuscadorPrincipalState.Empty -> emptySate()
                    }
                }
            }
        }
    }

    private fun emptySate(){
        binding.rvListaComidasBuscador.isVisible = false
        binding.imgSinResultadosBuscador.isVisible = true
        binding.tvNoSeencontroComida.isVisible = true
        adapterBuscador.updateList(emptyList())
        actualizarResultados(emptyList())
    }

    private fun errorState() {

    }

    private fun loadingState() {

    }

    private fun succesState(success: BuscadorPrincipalState.Success) {
        binding.rvListaComidasBuscador.isVisible = true
        binding.imgSinResultadosBuscador.isVisible = false
        binding.tvNoSeencontroComida.isVisible = false



        adapterBuscador.updateList(success.comidasBuscador)
        actualizarResultados(success.comidasBuscador)
    }

    private fun initEditText() {

        binding.etPantallaBuscador.requestFocus()

        // Abrir teclado
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etPantallaBuscador, InputMethodManager.SHOW_IMPLICIT)

        val editTextFlow = callbackFlow {
            binding.etPantallaBuscador.doOnTextChanged { text, _, _, _ ->
                trySend(text.toString())
            }
            awaitClose {}
        }

        lifecycleScope.launch {
            editTextFlow
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if(query.isNotEmpty()){
                        buscadorPrincipalViewModel.getComidasBuscador(query)
                    } else{
                        // Actualizamos el estado a Empty para que se muestre la UI correspondiente
                        buscadorPrincipalViewModel.clearSearch()
                    }
                }
        }

    }

    private fun buttonBack() {
        binding.cdBackWindow.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuscadorPrincipalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}