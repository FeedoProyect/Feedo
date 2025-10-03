package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.ListaAddIngredienteAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FotoIngredientesModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.IngredienteItem
import com.benjamin.proyectofeedo.databinding.ItemAddIngredienteBinding

class AddIngredientAdapter(
    private val onItemClickDelete: (IngredienteItem) -> Unit,
    private val onItemClick: (IngredienteItem) -> Unit
): RecyclerView.Adapter<AddIngredienteViewHolder>() {

    private val listNewIngredient = mutableListOf<IngredienteItem>()

    fun updateList(newList: List<IngredienteItem>) {
        val diffCallback = IngredienteDiffCallback(listNewIngredient, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listNewIngredient.clear()
        listNewIngredient.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddIngredienteViewHolder {
        val binding =
            ItemAddIngredienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddIngredienteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AddIngredienteViewHolder,
        position: Int
    ) {
        holder.render(listNewIngredient[position], onItemClickDelete, onItemClick)
    }

    override fun getItemCount() = listNewIngredient.size
}


// DiffUtil para optimizar las actualizaciones
class IngredienteDiffCallback(
    private val oldList: List<IngredienteItem>,
    private val newList: List<IngredienteItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Comparación por ID estable
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}