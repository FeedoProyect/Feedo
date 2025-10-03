package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddInstruccionesFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.Paso
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.AddRecetaViewModel
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddInstruccionesFragment.ListaAddPasosAdapter.AddPasosAdapter
import com.benjamin.proyectofeedo.databinding.DialogAddInstruccionBinding
import com.benjamin.proyectofeedo.databinding.DialogEditInstruccionBinding
import com.benjamin.proyectofeedo.databinding.FragmentAgregarInstruccionesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddInstruccionesFragment : Fragment() {

    private var _binding: FragmentAgregarInstruccionesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterAddPasos: AddPasosAdapter

    private val addRecetaViewModel by activityViewModels<AddRecetaViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initAdapter()
    }

    private fun initAdapter() {
        adapterAddPasos = AddPasosAdapter(
            OnItemSelected = { paso ->
                initDialogEditStep(paso)
            }, OnClickDelete = { paso ->
                addRecetaViewModel.eliminarPaso(paso)
            }
        )


        binding.rvAddInstrucciones.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterAddPasos
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addRecetaViewModel.pasos.collect { pasos ->
                    adapterAddPasos.submitList(pasos)

                    updateDialogAddPasos(pasos.isEmpty())
                }
            }
        }
    }

    private fun updateDialogAddPasos(isEmpty: Boolean) {
        if (isEmpty) {
            binding.parentInstrucciones.setOnClickListener { initDialogAddSteps() }
        } else {
            binding.tvAddPasos.setOnClickListener { initDialogAddSteps() }
            binding.imgAddPasos.setOnClickListener { initDialogAddSteps() }
        }
    }

    private fun initDialogAddSteps() {
        val dialogSteps = Dialog(requireContext())
        val dialogBindingSteps = DialogAddInstruccionBinding.inflate(layoutInflater)
        dialogSteps.setContentView(dialogBindingSteps.root)

        val posicion = adapterAddPasos.itemCount + 1
        val nuevaPosicion = "Paso ${posicion}"

        dialogBindingSteps.tvPosicionAddStep.text = nuevaPosicion

        dialogBindingSteps.botonAddStep.setOnClickListener {
            val instruccion = dialogBindingSteps.etDialogAddStep.text.toString()

            if (instruccion.isNotBlank()) {
                addRecetaViewModel.addInstruccion(instruccion)
                dialogSteps.dismiss()
            }
        }

        dialogSteps.show()
    }

    private fun initDialogEditStep(paso: Paso) {
        val dialogEditStep = Dialog(requireContext())
        val dialogBinding = DialogEditInstruccionBinding.inflate(layoutInflater)
        dialogEditStep.setContentView(dialogBinding.root)


        // Opcional: Ajustar el tamaño del dialog
        val window = dialogEditStep.window
        window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(), // 90% del ancho de pantalla
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val texto = paso.numero

        dialogBinding.tvPosicionStep.text = texto
        dialogBinding.etDialogEditStep.setText(paso.instruccion)

        dialogBinding.botonDialogEditStep.setOnClickListener {
            val editInstruccion = dialogBinding.etDialogEditStep.text.toString()

            if (editInstruccion.isNotBlank()) {
                addRecetaViewModel.editarInstruccion(paso, editInstruccion)
                dialogEditStep.dismiss()
                Toast.makeText(requireContext(), "Instrucción actualizada", Toast.LENGTH_SHORT)
                    .show()
            } else {
                dialogBinding.etDialogEditStep.error = "La instrucción no puede estar vacía"
            }
        }

        dialogBinding.botonDialogEditDeleteStep.setOnClickListener {
            addRecetaViewModel.eliminarPaso(paso)
            dialogEditStep.dismiss()
        }
        dialogEditStep.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarInstruccionesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}