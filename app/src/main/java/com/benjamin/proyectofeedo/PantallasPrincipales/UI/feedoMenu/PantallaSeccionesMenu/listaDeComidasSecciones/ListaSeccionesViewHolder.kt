package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaSeccionesMenu.listaDeComidasSecciones

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemComidaSeccionesBinding
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaSeccionesViewHolder(private val binding: ItemComidaSeccionesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        auth: Auth,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit
    ){
        binding.tvComidaNameSecciones.text = comidasModel.recetas.titulo
        Picasso.get().load(comidasModel.recetas.imagen).error(R.drawable.img_error).into(binding.imgComidaSecciones)

        binding.imgAgregarFavComidaSecciones.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}