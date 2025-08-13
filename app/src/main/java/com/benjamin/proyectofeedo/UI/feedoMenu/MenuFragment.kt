package com.benjamin.proyectofeedo.UI.feedoMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogosAdapter.ListaCatalogosAdapter
import com.benjamin.proyectofeedo.databinding.FragmentMenuBinding
import com.benjamin.proyectofeedo.domain.model.CatalogoInfo
import com.benjamin.proyectofeedo.domain.model.CatalogosModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val feedoMenuViewModel by viewModels<FeedoMenuViewModel>()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaCatalogosAdapter: ListaCatalogosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initUIState()
        initList()
    }

    private fun initList() {
        listaCatalogosAdapter = ListaCatalogosAdapter(onItemSelected = {

            val type = when(it){
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
                MenuFragmentDirections.actionMenuFragmentToCatalogosListFragment(type)
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
                    //Hubo cambios
                    listaCatalogosAdapter.updateList(it)
                }
            }
        }
    }

//    private fun initList() {
//        lifecycleScope.launch {
//            val client = getClient()
//            val supabaseResponse = client.postgrest["recetas"].select()
//            val data = supabaseResponse.decodeList<CatalogoResponse>()
//            Log.e("supabase", data.toString())
//
//            val adapter = ListaCatalogosAdapter(data)
//            binding.rvCatolgo.layoutManager = LinearLayoutManager(
//                requireContext(),
//                LinearLayoutManager.HORIZONTAL, false
//            )
//            binding.rvCatolgo.adapter = adapter
//        }
//    }

//    private fun getClient(): SupabaseClient {
//        return createSupabaseClient(
//            supabaseUrl = "https://vdtfvlfmdurwsdcvircw.supabase.co",
//            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZkdGZ2bGZtZHVyd3NkY3ZpcmN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMyODk3MzEsImV4cCI6MjA2ODg2NTczMX0.tHJ0zc7ZVsC_sxXGISisq7y_wxT-PWYH4UHSjS6iuos",
//        ) {
//            install(Postgrest)
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
}