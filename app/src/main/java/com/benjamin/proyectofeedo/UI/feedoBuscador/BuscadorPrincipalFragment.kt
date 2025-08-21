package com.benjamin.proyectofeedo.UI.feedoBuscador

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.UI.feedoBuscador.BuscadorPrincipalAdapter.BuscadorPrincipalAdapter
import com.benjamin.proyectofeedo.databinding.FragmentBuscadorPrincipalBinding
import dagger.hilt.android.AndroidEntryPoint
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

    private fun initList() {
        adapterBuscador = BuscadorPrincipalAdapter()
        binding.rvListaComidasBuscador.apply {
            layoutManager = LinearLayoutManager(requireContext())
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
                    }
                }
            }
        }
    }

    private fun errorState() {

    }

    private fun loadingState() {

    }

    private fun succesState(success: BuscadorPrincipalState.Success) {
        adapterBuscador.updateList(success.comidasBuscador)
    }

    private fun initEditText() {
        // Focus automático en el EditText
        binding.etPantallaBuscador.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etPantallaBuscador, InputMethodManager.SHOW_IMPLICIT)

        binding.etPantallaBuscador.addTextChangedListener{
            val response = it.toString()
            if(response.isNotEmpty()){
                buscadorPrincipalViewModel.getComidasBuscador(response)
            } else{
                adapterBuscador.updateList(emptyList())
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