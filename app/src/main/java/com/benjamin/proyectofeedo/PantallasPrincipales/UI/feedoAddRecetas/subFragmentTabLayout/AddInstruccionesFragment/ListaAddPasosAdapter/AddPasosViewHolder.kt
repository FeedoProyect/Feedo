package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddInstruccionesFragment.ListaAddPasosAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.Paso
import com.benjamin.proyectofeedo.databinding.ItemEditPasosBinding

class AddPasosViewHolder(private val binding: ItemEditPasosBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        pasos: Paso,
        OnItemSelected: (Paso) -> Unit,
        OnClickDelete: (Paso) -> Unit,
    ){
        binding.tvEditPasoNumero.text = pasos.numero
        binding.tvEditPasoDescripcion.text = pasos.instruccion

        binding.parentAddPaso.setOnClickListener { OnItemSelected(pasos) }
        binding.imgDeleteStep.setOnClickListener {
            OnClickDelete(pasos)
            true
        }
    }
}