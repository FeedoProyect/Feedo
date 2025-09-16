package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaModoSaludable

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemModoSaludableBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaModoSaludableViewHolder(
    private val binding: ItemModoSaludableBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        binding.tvModoSaludable.text = comidasModel.titulo
        Picasso.get().load(comidasModel.imagen).into(binding.imgModoSaludable)

        binding.imgAddFavsModoSaludable.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}
