package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment

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
import androidx.recyclerview.widget.GridLayoutManager
import com.benjamin.proyectofeedo.databinding.FragmentFavoritosBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.FavoritosState
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter.ListaFavoritosAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    private val favoritoViewModel by viewModels<FavoritosViewModel>()

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private lateinit var adapterFav: ListaFavoritosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initList()
        iniState()

        val userId = supabaseClient.auth.currentUserOrNull()?.id
        if (userId != null) {
            favoritoViewModel.getComidaFavoritos(userId)
        } else {
            Log.e("Favoritos", "⚠️ No hay usuario logueado")
        }
    }



    private fun initList() {
        adapterFav = ListaFavoritosAdapter(mutableListOf()) { comida ->
            val userId = supabaseClient.auth.currentUserOrNull()?.id
            if (userId != null) {
                favoritoViewModel.deleteComidaFavorito(userId, comida.id)
                adapterFav.removeItem(comida)
            }
            messageDeleteFav()
        }

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

    private fun messageDeleteFav(){
        Toast.makeText(requireContext(), "Se ha eliminado de favoritos", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}