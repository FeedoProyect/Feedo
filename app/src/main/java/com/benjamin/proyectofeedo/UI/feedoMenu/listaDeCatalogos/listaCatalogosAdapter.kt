package com.benjamin.proyectofeedo.UI.feedoMenu.listaDeCatalogos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.data.response.CatalogosName

class listaCatalogosAdapter(private val catalogosList: MutableList<CatalogosName>) :
    RecyclerView.Adapter<listaCatalogosViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): listaCatalogosViewHolder {
        return listaCatalogosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lista_catalogo, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: listaCatalogosViewHolder,
        position: Int
    ) {
        holder.render(catalogosList[position])
    }

    override fun getItemCount() = catalogosList.size
}