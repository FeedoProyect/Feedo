package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.benjamin.proyectofeedo.databinding.FragmentFavoritosBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.FavoritosState
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter.ListaFavoritosAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    private val favoritoViewModel by viewModels<FavoritosViewModel>()

    private lateinit var adapterFav: ListaFavoritosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initList()
        iniState()

        // 👇 Acá pasás el UUID del usuario logueado
        val usuarioId = "7ede9214-d595-496e-9510-28db794d91b6"
        favoritoViewModel.getComidaFavoritos(usuarioId)
    }


    private fun initList() {
        adapterFav = ListaFavoritosAdapter()
        binding.rvSeccionFavoritosPerfil.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterFav
        }
    }

    private fun iniState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                favoritoViewModel.state.collect {
                    when(it) {
                        is FavoritosState.Error -> errorState()
                        FavoritosState.Loading -> loadingState()
                        is FavoritosState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun errorState(){

    }

    private fun loadingState(){

    }

    private fun successState(success: FavoritosState.Success) {
        adapterFav.updateListFavoritos(success.recetas?: emptyList())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}