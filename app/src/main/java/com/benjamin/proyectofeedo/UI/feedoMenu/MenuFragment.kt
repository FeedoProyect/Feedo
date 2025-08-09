package com.benjamin.proyectofeedo.UI.feedoMenu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos.listaCatalogosAdapter
import com.benjamin.proyectofeedo.data.BDModul
import com.benjamin.proyectofeedo.data.response.CatalogoResponse
import com.benjamin.proyectofeedo.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: listaCatalogosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initList()
    }

    private fun initList() {
        lifecycleScope.launch {
            val supabaseResponse = supabaseClient.postgrest["recetas"].select()
            val data = supabaseResponse.decodeList<CatalogoResponse>()

            val adapter = listaCatalogosAdapter(data)
            binding.rvCatolgo.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCatolgo.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
}