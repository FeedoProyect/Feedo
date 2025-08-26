package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoRegisterAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.databinding.FragmentRegisterAccountBinding
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterAccountBinding? = null
    private val binding get() = _binding!!

    private val registerUserViewModel by viewModels<RegisterUserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initListener()
        initState()
    }

    private fun initState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                registerUserViewModel.state.collect {
                    when(it){
                        is AuthState.Error -> errorState(it)
                        AuthState.Idle -> idleState()
                        AuthState.Loading -> loadingState()
                        is AuthState.Success -> succesState(it)
                    }
                }
            }
        }
    }

    private fun idleState() {
        binding.botonRegistrado.isEnabled = true
    }

    private fun loadingState() {
        binding.botonRegistrado.isEnabled = false
        // Podés mostrar un ProgressBar
    }

    private fun succesState(success: AuthState.Success) {
        binding.botonRegistrado.isEnabled = true
        Toast.makeText(requireContext(), "Registro exitoso: ${success.user.email}", Toast.LENGTH_SHORT).show()

        // Redirigir a la pantalla principal (Home)
        findNavController().navigate(
            RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginUserFragment()
        )
    }

    private fun errorState(error: AuthState.Error) {
        binding.botonRegistrado.isEnabled = true
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }


    private fun initListener() {
        binding.botonRegistrado.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString().trim()
            val password = binding.etRegisterContraseA.text.toString()
            val confirmPassword = binding.etConfirmarContraseA.text.toString()

            // Validaciones básicas
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Llamamos al ViewModel
            registerUserViewModel.register(email, password)
        }

        binding.tvInicieSesion.setOnClickListener {
            findNavController().navigate(
                RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginUserFragment()
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}