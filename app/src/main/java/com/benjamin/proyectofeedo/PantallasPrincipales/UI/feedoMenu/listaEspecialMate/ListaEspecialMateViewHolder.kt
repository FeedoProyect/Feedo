package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaEspecialMate

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemEspecialMateBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaEspecialMateViewHolder(
    private val binding: ItemEspecialMateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        binding.tvEspecialMate.text = comidasModel.recetas.titulo
        Picasso.get().load(comidasModel.recetas.imagen).into(binding.imgEspecialMate)

        binding.imgAddFavMate.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}
