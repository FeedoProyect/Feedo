package com.benjamin.proyectofeedo.usuarioLogin.LoginUI.feedoRegisterAccount

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.home.MainActivity
import com.benjamin.proyectofeedo.databinding.FragmentRegisterAccountBinding
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthState
import com.benjamin.proyectofeedo.usuarioLogin.LoginUI.AuthUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterAccountBinding? = null
    private val binding get() = _binding!!

    private val authUserViewModel by viewModels<AuthUserViewModel>()

    private var hasNavigatedToValidation = false
    private var isGoogleRegistration = false

    // Job para el polling de verificación de email
    private var emailVerificationJob: Job? = null

    // Guardar datos del usuario pendiente
    private var pendingEmail: String? = null
    private var pendingPassword: String? = null

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
                authUserViewModel.state.collect {
                    when(it){
                        is AuthState.Error -> errorState(it)
                        AuthState.Idle -> idleState()
                        AuthState.Loading -> loadingState()
                        is AuthState.Success -> succesState(it)
                        AuthState.LoggedOut -> { }
                    }
                }
            }
        }
    }

    private fun idleState() {
        showNormalUI()
        emailVerificationJob?.cancel()
    }

    private fun loadingState() {
        if (isGoogleRegistration) {
            showLoadingUI("Registrando con Google...")
        } else {
            showLoadingUI("Registrando cuenta...")
        }
    }

    private fun succesState(success: AuthState.Success) {
        if (!hasNavigatedToValidation) {
            hasNavigatedToValidation = true

            emailVerificationJob?.cancel()

            if(isGoogleRegistration) {
                // Google: ir directo al MainActivity (ya verificado)
                Toast.makeText(requireContext(), "Registro exitoso: ${success.user.email}", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                // Email/Password: mostrar pantalla de verificación
                showEmailVerificationUI()
                startEmailVerificationPolling()
            }
        }
    }

    private fun errorState(error: AuthState.Error) {
        showNormalUI()
        emailVerificationJob?.cancel()
        Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG).show()
    }

    private fun startEmailVerificationPolling() {
        emailVerificationJob?.cancel()

        if (pendingEmail == null || pendingPassword == null) {
            Toast.makeText(requireContext(), "Error: No se pudo iniciar verificación", Toast.LENGTH_SHORT).show()
            return
        }

        emailVerificationJob = lifecycleScope.launch {
            while (isActive) {
                delay(3000) // Verificar cada 3 segundos

                try {
                    // Intentar hacer login para verificar si el email fue confirmado
                    val isVerified = authUserViewModel.checkEmailVerification(pendingEmail!!, pendingPassword!!)

                    if (isVerified) {
                        // Email verificado exitosamente
                        withContext(Dispatchers.Main) {
                            emailVerificationJob?.cancel()
                            showNormalUI()
                            Toast.makeText(
                                requireContext(),
                                "¡Email verificado! Ahora puedes iniciar sesión",
                                Toast.LENGTH_LONG
                            ).show()

                            // Limpiar credenciales
                            pendingEmail = null
                            pendingPassword = null

                            // Navegar al login
                            findNavController().popBackStack()
                        }
                        break
                    }
                } catch (e: Exception) {
                    Log.e("RegisterFragment", "Error en polling", e)
                }
            }
        }
    }

    private fun showNormalUI() {
        binding.apply {
            tvCreaUnaCuenta.visibility = View.VISIBLE
            imgfeedoregister.visibility = View.VISIBLE
            parentDatos.visibility = View.VISIBLE
            loadingContainer.visibility = View.GONE
            botonRegistrado.isEnabled = true
        }
    }

    private fun showLoadingUI(message: String) {
        binding.apply {
            tvCreaUnaCuenta.visibility = View.GONE
            imgfeedoregister.visibility = View.GONE
            parentDatos.visibility = View.GONE
            loadingContainer.visibility = View.VISIBLE
            tvLoadingMessage.text = message
            emailVerificationContainer.visibility = View.GONE
        }
    }

    private fun showEmailVerificationUI() {
        binding.apply {
            tvCreaUnaCuenta.visibility = View.GONE
            imgfeedoregister.visibility = View.GONE
            parentDatos.visibility = View.GONE
            loadingContainer.visibility = View.VISIBLE
            emailVerificationContainer.visibility = View.VISIBLE
            tvLoadingMessage.text = "Esperando verificación de email..."
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun initListener() {
        binding.botonRegistrado.setOnClickListener {
            initSessionEmail()
        }

        binding.cdRegisterWithGoogle.setOnClickListener {
            initRegisterGoogle()
        }

        binding.tvInicieSesion.setOnClickListener {
            emailVerificationJob?.cancel()
            pendingEmail = null
            pendingPassword = null
            findNavController().popBackStack()
        }

        binding.btnCancelVerification.setOnClickListener {
            emailVerificationJob?.cancel()
            pendingEmail = null
            pendingPassword = null
            Toast.makeText(
                requireContext(),
                "Puedes volver más tarde para iniciar sesión una vez verificado tu email",
                Toast.LENGTH_LONG
            ).show()
            findNavController().popBackStack()
        }
    }

    private fun initSessionEmail() {
        val userName = binding.etUsuario.text.toString().trim()
        val email = binding.etRegisterEmail.text.toString().trim()
        val password = binding.etRegisterContraseA.text.toString()
        val confirmPassword = binding.etConfirmarContraseA.text.toString()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userName.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar credenciales para el polling
        pendingEmail = email
        pendingPassword = password

        isGoogleRegistration = false
        authUserViewModel.register(email, password, userName)
    }

    private fun initRegisterGoogle() {
        isGoogleRegistration = true
        authUserViewModel.loginWithGoogle(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}