package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddInstruccionesFragment.ListaAddPasosAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.Paso
import com.benjamin.proyectofeedo.databinding.ItemPasoBinding

class AddPasosViewHolder(private val binding: ItemPasoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(pasos: Paso, OnItemSelected: (Paso) -> Unit){
        binding.tvPasoNumero.text = pasos.numero
        binding.tvPasoDescripcion.text = pasos.instruccion

        binding.parentAddPaso.setOnClickListener { OnItemSelected(pasos) }
    }
}