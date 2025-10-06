package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas

import android.app.Activity
import android.app.Dialog
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.AddIngredienteViewModel
import com.benjamin.proyectofeedo.databinding.DialogRecetasBinding
import com.benjamin.proyectofeedo.databinding.FragmentAddRecetasBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.FragmentPageAddRecetaAdapter
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.DialogAddFeatureBinding
import com.benjamin.proyectofeedo.settingsFeedo.UI.darkMode.DarkModeViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class AddRecetaFragment : Fragment() {

    private var _binding: FragmentAddRecetasBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private lateinit var adapter: FragmentPageAddRecetaAdapter

    private val addRecetaViewModel by activityViewModels<AddRecetaViewModel>()
    private val darkModeViewModel by activityViewModels<DarkModeViewModel>()

    private val addIngredienteViewModel by activityViewModels<AddIngredienteViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }


    private fun initUI() {
        observeDarkMode()
        initTab()
        initListeners()
        observeIngredientes()
        observeInsertState()
    }

    private fun observeIngredientes() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addIngredienteViewModel.ingredientesList.collect { ingredientes ->
                    // Cada vez que cambian los ingredientes en AddIngredienteViewModel,
                    // se actualizan en AddRecetaViewModel también
                    addRecetaViewModel.setIngredientes(ingredientes)
                }
            }
        }
    }

    private fun observeInsertState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addRecetaViewModel.insertState.collect { state ->
                    when (state) {
                        is InsertState.Loading -> {
                            // Mostrar ProgressBar
                        }
                        is InsertState.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "¡Receta guardada exitosamente!",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.botonGoComidaInsert.isEnabled = true

                            // Resetear el estado para poder agregar otra
                            addRecetaViewModel.resetInsertState()
                        }
                        is InsertState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                state.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        InsertState.Idle -> { /* No hacer nada */ }
                    }
                }
            }
        }
    }

    private fun observeDarkMode() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                darkModeViewModel.isDarkMode.collect { darkMode ->
                    if (darkMode.darkMode) {
                        // Mostrar imagen dark
                        binding.imgAgregarFotoComidaDark.visibility = View.VISIBLE
                        binding.imgAgregarFotoComidaLight.visibility = View.GONE
                    } else {
                        // Mostrar imagen light
                        binding.imgAgregarFotoComidaLight.visibility = View.VISIBLE
                        binding.imgAgregarFotoComidaDark.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                resultadoImage.launch(intent)
            }
    }

    // Actualizar el metodo de imagen para guardarla en el ViewModel
    private val resultadoImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                val data = resultado.data
                imageUri = data!!.data
                binding.imgAgregarFotoComidaLight.setImageURI(imageUri)
                binding.imgAgregarFotoComidaDark.setImageURI(imageUri)

                // GUARDAR EN EL VIEWMODEL
                addRecetaViewModel.setImageUri(imageUri)
            } else {
                Toast.makeText(requireContext(), "Accion cancelada", Toast.LENGTH_SHORT).show()
            }
        }

    private fun initListeners() {
        binding.imgAgregarFotoComidaLight.setOnClickListener { initImage() }
        binding.imgAgregarFotoComidaDark.setOnClickListener { initImage() }

        binding.tvAgregarTituloReceta.setOnClickListener { initDialog() }

        binding.botonGoComidaInsert.setOnClickListener {
            addRecetaViewModel.guardarReceta()
        }
    }

    private fun initDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogRecetasBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.botonAddNameComida.setOnClickListener {
            val comidaNombre = dialogBinding.etAddComida.text.toString()

            if (comidaNombre.isNotBlank()) {
                // Poner el texto en el TextView del fragment
                binding.tvAgregarTituloReceta.text = comidaNombre

                // GUARDAR EN EL VIEWMODEL
                addRecetaViewModel.setTitulo(comidaNombre)

                // Cerrar el dialog
                dialog.dismiss()
            } else {
                // Opcional: mostrar un error si está vacío
                dialogBinding.etAddComida.error = "Escribe un nombre"
            }
        }

        dialog.show()
    }

    private fun initTab() {
        adapter = FragmentPageAddRecetaAdapter(childFragmentManager, lifecycle)
        binding.viewPage2AgregarComida.adapter = adapter

        val tabTitle = listOf("Ingredientes", "Instrucciones")

        TabLayoutMediator(
            binding.tabLayoutAgregarReceta,
            binding.viewPage2AgregarComida
        ) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        //aplica la fuente al tab seleccionado
        applyCustomFontToTabs()

        // Escuchar cuando cambia de página
        binding.viewPage2AgregarComida.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    when (position) {
                        0 -> { // Ingredientes
                            binding.imgAddTimeFood.visibility = View.VISIBLE
                            binding.tvaddTimeFood.visibility = View.VISIBLE
                            binding.imgAddPasosFood.visibility = View.GONE
                            binding.tvAddPasosFood.visibility = View.GONE

                            binding.tvaddTimeFood.setOnClickListener {
                                initDialogsAddFeature(position)
                            }
                            binding.imgAddTimeFood.setOnClickListener {
                                initDialogsAddFeature(position)
                            }
                        }

                        1 -> { // Instrucciones
                            binding.imgAddTimeFood.visibility = View.GONE
                            binding.tvaddTimeFood.visibility = View.GONE
                            binding.imgAddPasosFood.visibility = View.VISIBLE
                            binding.tvAddPasosFood.visibility = View.VISIBLE

                            binding.tvAddPasosFood.setOnClickListener {
                                initDialogsAddFeature(position)
                            }
                            binding.imgAddPasosFood.setOnClickListener {
                                initDialogsAddFeature(position)
                            }
                            ListenSteps()
                        }
                    }
                }
            }
        )
    }

    private fun initDialogsAddFeature(position: Int) {
        val dialogFeature = Dialog(requireContext())
        val dialogBindingFeature = DialogAddFeatureBinding.inflate(layoutInflater)
        dialogFeature.setContentView(dialogBindingFeature.root)


        when (position) {
            0 -> {
                dialogBindingFeature.parentDialogTime.visibility = View.VISIBLE
                dialogBindingFeature.botonDialogAddTime.setOnClickListener {
                    val tiempoComida = dialogBindingFeature.etDialogAddTime.text.toString()

                    if (tiempoComida.isNotBlank()) {
                        binding.tvaddTimeFood.text = tiempoComida

                        addRecetaViewModel.setTiempoPreparacion(tiempoComida)

                        dialogFeature.dismiss()
                    } else {
                        dialogBindingFeature.etDialogAddTime.error =
                            "Debes añadir el tiempo de preparacion"
                    }
                }
            }
            1 -> {
                dialogBindingFeature.parentDialogPasos.visibility = View.VISIBLE
                dialogBindingFeature.botonDialogAddPasos.setOnClickListener {
                    val pasosComida = dialogBindingFeature.etDialogAddCantidadPasos.text.toString()

                    if (pasosComida.isNotBlank()) {
                        val cantidad = pasosComida.toIntOrNull()

                        if (cantidad != null && cantidad >= 0) {
                            binding.tvAddPasosFood.text = pasosComida

                            addRecetaViewModel.setCantidadPasos(cantidad)

                            dialogFeature.dismiss()
                        } else {
                            dialogBindingFeature.etDialogAddCantidadPasos.error =
                                "Debes añadir la cantidad de pasos"
                        }
                    }
                }
            }
        }
        dialogFeature.show()
    }

    fun ListenSteps() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addRecetaViewModel.pasos.collect {
                    if(it.size == 0){
                        val texto = "¿Cantidad\npasos?"
                        binding.tvAddPasosFood.text = texto
                    } else {
                        binding.tvAddPasosFood.text = it.size.toString()
                    }
                }
            }
        }
    }

    private fun applyCustomFontToTabs(){
        binding.tabLayoutAgregarReceta.post {
            binding.tabLayoutAgregarReceta.selectTab(binding.tabLayoutAgregarReceta.getTabAt(0))
        }
    }

    private fun confirmarReduccionPasos(nuevaCantidad: Int, cantidadActual: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Reducir pasos")
            .setMessage("Esto eliminará ${cantidadActual - nuevaCantidad} paso(s). ¿Continuar?")
            .setPositiveButton("Sí") { _, _ ->
                addRecetaViewModel.setCantidadPasos(nuevaCantidad)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecetasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}