package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.FragmentPerfilBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FragmentPageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FragmentPageAdapter
    private val perfilViewModel: PerfilViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTab()
        initListener()


        perfilViewModel.loadCurrentUserInfo()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                perfilViewModel.userInfo.collect { user ->
                    user?.let {
                        binding.tvNombre.text =
                            it.username.ifEmpty { getString(R.string.perfil_default) }


                        setupBio(it.biografia ?: "")


                        if (!it.imagen_perfil.isNullOrBlank()) {
                            val fotoUrl = "${it.imagen_perfil}?t=${System.currentTimeMillis()}"

                            // Mostrar loader
                            binding.pbFotoPerfil.visibility = View.VISIBLE
                            binding.ivFotoPerfil.visibility = View.INVISIBLE

                            Picasso.get()
                                .load(fotoUrl)
                                .placeholder(R.drawable.agregarperfil)
                                .error(R.drawable.imagen_sin_perfil)
                                .into(binding.ivFotoPerfil, object : Callback {
                                    override fun onSuccess() {
                                        binding.pbFotoPerfil.visibility = View.GONE
                                        binding.ivFotoPerfil.visibility = View.VISIBLE
                                    }

                                    override fun onError(e: java.lang.Exception?) {
                                        binding.pbFotoPerfil.visibility = View.GONE
                                        binding.ivFotoPerfil.visibility = View.VISIBLE
                                        binding.ivFotoPerfil.setImageResource(R.drawable.imagen_sin_perfil)
                                    }
                                })
                        } else {
                            binding.ivFotoPerfil.setImageResource(R.drawable.imagen_sin_perfil)
                            binding.pbFotoPerfil.visibility = View.GONE
                            binding.ivFotoPerfil.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupBio(bio: String) {
        binding.tvDescripcion.text = bio
        binding.tvDescripcion.isSingleLine = true
        binding.tvDescripcion.maxLines = 1
        binding.tvMas.visibility = View.GONE
        binding.tvMas.text = getString(R.string.perfil_biografia_more)

        binding.tvDescripcion.post {
            val layout = binding.tvDescripcion.layout
            val isEllipsized = layout != null && layout.lineCount > 0 &&
                    layout.getEllipsisCount(layout.lineCount - 1) > 0

            if (isEllipsized) {
                binding.tvMas.visibility = View.VISIBLE
                binding.tvDescripcion.isSingleLine = true
                binding.tvDescripcion.maxLines = 1
                binding.tvMas.text = getString(R.string.perfil_biografia_more)
            } else {
                binding.tvMas.visibility = View.GONE
            }
        }

        binding.tvMas.setOnClickListener {
            val more = getString(R.string.perfil_biografia_more)
            val less = getString(R.string.perfil_biografia_less)

            if (binding.tvMas.text == more) {
                binding.tvDescripcion.isSingleLine = false
                binding.tvDescripcion.maxLines = Int.MAX_VALUE
                binding.tvMas.text = less
            } else {
                binding.tvDescripcion.isSingleLine = true
                binding.tvDescripcion.maxLines = 1
                binding.tvMas.text = more

                binding.tvDescripcion.post {
                    val layout = binding.tvDescripcion.layout
                    val stillEllipsized = layout != null &&
                            layout.lineCount > 0 &&
                            layout.getEllipsisCount(layout.lineCount - 1) > 0
                    binding.tvMas.visibility = if (stillEllipsized) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun initListener() {
        binding.imgSettings.setOnClickListener { startRotationAnimation(binding.imgSettings) }
        binding.btnIrPerfil.setOnClickListener {
            findNavController().navigate(
                PerfilFragmentDirections.actionPerfilFragmentToEditarPerfilFragment()
            )
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

    private fun startSetting() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






