package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoLoginAccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.home.MainActivity
import com.benjamin.proyectofeedo.databinding.FragmentLoginUserBinding
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthState
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginUserFragment : Fragment() {

    private var _binding: FragmentLoginUserBinding? = null
    private val binding get() = _binding!!

    private val authUserViewModel by viewModels<AuthUserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initState()
        initListenerBottom()
    }

    private fun initState(){
        lifecycleScope.launch {
            authUserViewModel.state.collect { state ->
                when (state) {
                    is AuthState.Idle -> {}
                    is AuthState.Loading -> {
                        // Podés mostrar un ProgressBar
                        //
                    }
                    is AuthState.Success -> {

                        // Ir al MainActivity
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish() // para que no vuelva al login con back
                    }
                    is AuthState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    AuthState.LoggedOut -> {

                    }
                }
            }
        }
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
        val email = binding.etGmail.text.toString().trim()
        val password = binding.etContraseA.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            authUserViewModel.login(email, password)
        } else {
            Toast.makeText(requireContext(), "Completa los campos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}