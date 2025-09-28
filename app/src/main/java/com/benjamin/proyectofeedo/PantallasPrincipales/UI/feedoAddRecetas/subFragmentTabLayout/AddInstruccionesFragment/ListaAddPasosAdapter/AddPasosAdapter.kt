package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddInstruccionesFragment.ListaAddPasosAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.Paso
import com.benjamin.proyectofeedo.databinding.ItemEditPasosBinding

class AddPasosAdapter(
    private val OnItemSelected: (Paso) -> Unit,
    private val OnClickDelete: (Int) -> Unit
) : RecyclerView.Adapter<AddPasosViewHolder>() {

    private val listNewSteps = mutableListOf<Paso>()

    fun submitList(pasos: List<Paso>) {
        listNewSteps.clear()
        listNewSteps.addAll(pasos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddPasosViewHolder {
        val binding = ItemEditPasosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddPasosViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AddPasosViewHolder,
        position: Int
    ) {
        holder.render(listNewSteps[position], OnItemSelected, OnClickDelete, position)
    }

    override fun getItemCount() = listNewSteps.size
}