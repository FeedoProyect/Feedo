package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoLoginAccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.UI.home.MainActivity
import com.benjamin.proyectofeedo.databinding.FragmentLoginUserBinding
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoWelcome.WelcomeFeedoFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginUserFragment : Fragment() {

    private var _binding: FragmentLoginUserBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initListenerBottom()
    }

    private fun initListenerBottom() {
        binding.botonGoApp.setOnClickListener {
            ingresoMenuFeedo()
        }
        binding.tvRegistrarCuenta.setOnClickListener {
            ingresoRegistroCuenta()
        }
    }

    private fun ingresoRegistroCuenta(){
        findNavController().navigate(
            LoginUserFragmentDirections.actionLoginUserFragmentToRegisterUserFragment()
        )
    }

    private fun ingresoMenuFeedo(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}