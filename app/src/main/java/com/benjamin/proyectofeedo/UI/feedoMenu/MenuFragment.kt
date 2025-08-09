package com.benjamin.proyectofeedo.UI.feedoMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos.listaCatalogosAdapter
import com.benjamin.proyectofeedo.data.CatalogosProvived
import com.benjamin.proyectofeedo.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {


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
        val data = CatalogosProvived.ListaComidas
        val adapter = listaCatalogosAdapter(data)
        binding.rvCatolgo.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCatolgo.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
}