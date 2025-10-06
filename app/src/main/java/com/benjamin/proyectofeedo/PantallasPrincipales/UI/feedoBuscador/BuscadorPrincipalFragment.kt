package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoBuscador

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalAdapter.BuscadorPrincipalAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.databinding.FragmentBuscadorPrincipalBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BuscadorPrincipalFragment : Fragment() {

    private var _binding: FragmentBuscadorPrincipalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private val buscadorPrincipalViewModel by viewModels<BuscadorPrincipalViewModel>()

    private lateinit var adapterBuscador: BuscadorPrincipalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuscadorPrincipalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initList()
        initState()
        buttonBack()
        initEditText()
    }

    private fun initList() {
        adapterBuscador = BuscadorPrincipalAdapter(
            auth = supabaseClient.auth,
            onItemClick = { comida ->
                val action = BuscadorPrincipalFragmentDirections
                    .actionBuscadorPrincipalFragmentToDetalleRecetaFragment(comida.id)
                findNavController().navigate(action)
            },
            onItemSelectedFav = { favoritos, isFav ->
                if (isFav) {
                    buscadorPrincipalViewModel.addComidasFavoritos(favoritos)
                    messageFav()
                } else {
                    buscadorPrincipalViewModel.removeComidasFavoritos(favoritos)
                    messageUnfav()
                }
            }
        )

        binding.rvListaComidasBuscador.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterBuscador
        }
    }

    private fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscadorPrincipalViewModel.state.collect { state ->
                    when (state) {
                        is BuscadorPrincipalState.Error -> errorState()
                        BuscadorPrincipalState.Loading -> loadingState()
                        is BuscadorPrincipalState.Success -> successState(state)
                        BuscadorPrincipalState.Empty -> emptyState()
                    }
                }
            }
        }
    }

    private fun emptyState() {
        binding.rvListaComidasBuscador.isVisible = false
        binding.imgSinResultadosBuscador.isVisible = true
        binding.tvNoSeencontroComida.isVisible = true
        adapterBuscador.updateList(emptyList())
        actualizarResultados(emptyList())
    }

    private fun successState(success: BuscadorPrincipalState.Success) {
        binding.rvListaComidasBuscador.isVisible = true
        binding.imgSinResultadosBuscador.isVisible = false
        binding.tvNoSeencontroComida.isVisible = false
        adapterBuscador.updateList(success.comidasBuscador)
        actualizarResultados(success.comidasBuscador)
    }

    private fun actualizarResultados(lista: List<ComidasModel>) {
        val response = "Encontramos ${lista.size} resultados en relación a tu búsqueda"
        binding.tvResultadosBusqueda.text = response
    }

    private fun errorState() {}
    private fun loadingState() {}

    private fun initEditText() {
        binding.etPantallaBuscador.requestFocus()

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
                    if (query.isNotEmpty()) {
                        buscadorPrincipalViewModel.getComidasBuscador(query)
                    } else {
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

    private fun messageFav() {
        Toast.makeText(requireContext(), "Se ha añadido a favoritos", Toast.LENGTH_SHORT).show()
    }

    private fun messageUnfav() {
        Toast.makeText(requireContext(), "Se ha quitado de favoritos", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
