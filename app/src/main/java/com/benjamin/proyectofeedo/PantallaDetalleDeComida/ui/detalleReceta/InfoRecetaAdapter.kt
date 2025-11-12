package com.benjamin.proyectofeedo.PantallasPrincipales.UI.detalleReceta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.R

class InfoRecetaAdapter(
    private val infoList: List<String>
) : RecyclerView.Adapter<InfoRecetaAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textInfo: TextView = view.findViewById(R.id.textInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_info_receta, parent, false)
        return InfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.textInfo.text = infoList[position]
    }

    override fun getItemCount(): Int = infoList.size
}
