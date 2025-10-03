package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaClasicoArgentinoViewHolder(
    private val binding: ItemSeccionesMenuBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        binding.tvComidaSeccionesMenu.text = comidasModel.recetas.titulo

        Picasso.get().load(comidasModel.recetas.imagen).error(R.drawable.img_error).into(binding.imgComidaSeccionesMenu)

        binding.imgAddComidaSeccionFav.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}

