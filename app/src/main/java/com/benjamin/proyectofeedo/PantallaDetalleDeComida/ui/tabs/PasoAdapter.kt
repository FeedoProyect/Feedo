package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemPasoBinding

data class Paso(val numero: String, val descripcion: String)

class PasoAdapter(private val pasos: List<Paso>) :
    RecyclerView.Adapter<PasoAdapter.PasoViewHolder>() {

    inner class PasoViewHolder(val binding: ItemPasoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasoViewHolder {
        val binding = ItemPasoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasoViewHolder, position: Int) {
        val paso = pasos[position]
        holder.binding.tvPasoNumero.text = paso.numero
        holder.binding.tvPasoDescripcion.text = paso.descripcion
    }

    override fun getItemCount(): Int = pasos.size
}
