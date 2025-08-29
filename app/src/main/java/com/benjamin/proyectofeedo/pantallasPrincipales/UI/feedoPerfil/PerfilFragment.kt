package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.benjamin.proyectofeedo.databinding.FragmentPerfilBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FragmentPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FragmentPageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initTab()
        initListener()
    }

    private fun initListener() {
        navSetting()
    }

    private fun navSetting() {
        binding.imgSettings.setOnClickListener {
            startRotationAnimation(binding.imgSettings)
        }
    }

    private fun startRotationAnimation(view: View) {
        view.animate()
            .setDuration(500)
            .setInterpolator(LinearInterpolator())
            .rotationBy(360f)
            .withEndAction { startSetting() }
            .start()
    }

    private fun startSetting(){
        findNavController().navigate(
            PerfilFragmentDirections.actionPerfilFragmentToSettingViewFragment()
        )
    }

    private fun initTab() {
        adapter = FragmentPageAdapter(childFragmentManager, lifecycle)
        binding.viewPage2Perfil.adapter = adapter

        val tabTitle = listOf("Recetas", "Favoritos")

        TabLayoutMediator(binding.tabLayoutPerfil, binding.viewPage2Perfil) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}