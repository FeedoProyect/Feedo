package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.AddRecetaFragmentDirections
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.ListaAddIngredienteAdapter.AddIngredientAdapter
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FotoIngredientesModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IngredienteItem
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.DialogAddIngredienteBinding
import com.benjamin.proyectofeedo.databinding.DialogEditIngredienteBinding
import com.benjamin.proyectofeedo.databinding.FragmentAgregarIngredienteBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddIngredienteFragment : Fragment() {

    private var _binding: FragmentAgregarIngredienteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterAddIngrediente: AddIngredientAdapter
    private val addIngredienteViewModel by viewModels<AddIngredienteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initAdapters()
        observeSelectedIngredient()
        observeClicksItems()
        observeIngredientesList()
    }

    private fun observeIngredientesList() {
        viewLifecycleOwner.lifecycleScope.launch {
            addIngredienteViewModel.ingredientesList.collect { lista ->
                adapterAddIngrediente.updateList(lista)
            }
        }
    }

    private fun observeClicksItems() {
        binding.parentIngrediente.setOnClickListener {
            addIngredienteViewModel.clearSelectedIngrediente()
            addIngredienteViewModel.setEditingMode(false)
            initDialogIngrediente()
        }
        binding.tvAddIngrediente.setOnClickListener {
            addIngredienteViewModel.clearSelectedIngrediente()
            addIngredienteViewModel.setEditingMode(false)
            initDialogIngrediente()
        }
        binding.imgAddIngrediente.setOnClickListener {
            addIngredienteViewModel.clearSelectedIngrediente()
            addIngredienteViewModel.setEditingMode(false)
            initDialogIngrediente()
        }
    }

    private fun observeSelectedIngredient() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<FotoIngredientesModel>("selected_ingredient")
            ?.observe(viewLifecycleOwner) { ingrediente ->
                ingrediente?.let {
                    addIngredienteViewModel.setSelectedIngrediente(it)

                    findNavController().currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<FotoIngredientesModel>("selected_ingredient")

                    // CLAVE: Usa un pequeño delay para que el fragment se renderice primero
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(100) // Espera a que el fragment esté completamente visible

                        if (addIngredienteViewModel.isEditingMode.value) {
                            addIngredienteViewModel.ingredienteBeingEditedId.value?.let { id ->
                                val ingredienteEdit = addIngredienteViewModel.getIngredientesList()
                                    .firstOrNull { ing -> ing.id == id }
                                ingredienteEdit?.let { initDialogEditIngrediente(it) }
                            }
                        } else {
                            initDialogIngrediente()
                        }
                    }
                }
            }
    }

    private fun initAdapters() {
        adapterAddIngrediente = AddIngredientAdapter(
            onItemClick = { ingrediente ->
                initDialogEditIngrediente(ingrediente)
            },
            onItemClickDelete = { ingrediente ->
                addIngredienteViewModel.deleteIngrediente(ingrediente)
            }
        )

        binding.rvAddIngredientes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterAddIngrediente
        }
    }

    private fun initDialogIngrediente() {
        val dialogAddIngrediente = Dialog(requireContext())
        val dialogBindingAddIngrediente = DialogAddIngredienteBinding.inflate(layoutInflater)
        dialogAddIngrediente.setContentView(dialogBindingAddIngrediente.root)

        // Carga la imagen si ya hay una seleccionada
        addIngredienteViewModel.selectedIngredienteForDialog.value?.let { ingredienteSeleccionado ->
            Picasso.get()
                .load(ingredienteSeleccionado.imagen)
                .error(R.drawable.img_error)
                .into(dialogBindingAddIngrediente.imgAddFotoIngrediente)

            dialogBindingAddIngrediente.etDialogAddIngrediente.setText(ingredienteSeleccionado.nombre)
        }

        dialogBindingAddIngrediente.imgAddFotoIngrediente.setOnClickListener {
            dialogAddIngrediente.dismiss()

            // ASEGÚRATE de que NO esté en modo edición
            addIngredienteViewModel.setEditingMode(false)

            findNavController().navigate(
                AddRecetaFragmentDirections.actionAddRecetasFragmentToFotoIngredientesFragment()
            )
        }

        dialogBindingAddIngrediente.botonAddIngrediente.setOnClickListener {
            val nombreIngrediente = dialogBindingAddIngrediente.etDialogAddIngrediente.text.toString()

            if (nombreIngrediente.isNotEmpty()) {
                val imagen = addIngredienteViewModel.selectedIngredienteForDialog.value?.imagen
                addIngredienteViewModel.addIngrediente(nombreIngrediente, imagen)
                addIngredienteViewModel.clearSelectedIngrediente()
                dialogAddIngrediente.dismiss()
            }
        }

        dialogAddIngrediente.setOnDismissListener {
            // Solo limpia si NO vas a navegar
            if (!addIngredienteViewModel.isEditingMode.value) {
                addIngredienteViewModel.clearSelectedIngrediente()
            }
        }

        dialogAddIngrediente.show()
    }

    private fun initDialogEditIngrediente(ingrediente: IngredienteItem) {
        val dialogEditIngrediente = Dialog(requireContext())
        val dialogBindingEditIngrediente = DialogEditIngredienteBinding.inflate(layoutInflater)
        dialogEditIngrediente.setContentView(dialogBindingEditIngrediente.root)

        // IMPORTANTE: Marca que estamos en modo edición
        addIngredienteViewModel.setEditingMode(true, ingrediente)

        // Usa la imagen nueva si existe, sino la original
        val imagenToShow = addIngredienteViewModel.selectedIngredienteForDialog.value?.imagen
            ?: ingrediente.imagen

        dialogBindingEditIngrediente.etDialogEditIngrediente.setText(ingrediente.nombre)
        Picasso.get()
            .load(imagenToShow)
            .error(R.drawable.img_error)
            .into(dialogBindingEditIngrediente.imgEditFotoIngrediente)

        dialogBindingEditIngrediente.imgEditFotoIngrediente.setOnClickListener {
            dialogEditIngrediente.dismiss()

            // Mantén el modo edición ACTIVO
            // addIngredienteViewModel.setEditingMode(true, ingrediente) <- Ya está seteado arriba

            findNavController().navigate(
                AddRecetaFragmentDirections.actionAddRecetasFragmentToFotoIngredientesFragment()
            )
        }

        dialogBindingEditIngrediente.botonEditIngrediente.setOnClickListener {
            val nombreIngrediente = dialogBindingEditIngrediente.etDialogEditIngrediente.text.toString()

            if (nombreIngrediente.isNotEmpty()) {
                val imagen = addIngredienteViewModel.selectedIngredienteForDialog.value?.imagen
                    ?: ingrediente.imagen

                addIngredienteViewModel.updateIngrediente(ingrediente.id, nombreIngrediente, imagen)
                addIngredienteViewModel.clearSelectedIngrediente()
                addIngredienteViewModel.setEditingMode(false)
                dialogEditIngrediente.dismiss()
            }
        }

        dialogBindingEditIngrediente.botonDialogEditDeleteStep.setOnClickListener {
            addIngredienteViewModel.deleteIngrediente(ingrediente)
            addIngredienteViewModel.clearSelectedIngrediente()
            addIngredienteViewModel.setEditingMode(false)
            dialogEditIngrediente.dismiss()
        }

        dialogEditIngrediente.setOnDismissListener {
            // Solo limpia si NO vas a volver de FotoIngredientes
            // No hagas nada aquí, deja que los botones manejen el estado
        }

        dialogEditIngrediente.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarIngredienteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}