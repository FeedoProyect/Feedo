package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class BuscadorPrincipalViewHolder(
    private val binding: ItemSeccionesMenuBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidaInfo: ComidasModel,
        auth: Auth,
        onItemClick: (ComidasModel) -> Unit,
        onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit
    ) {
        // Imagen y título
        Picasso.get()
            .load(comidaInfo.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)

        binding.tvComidaSeccionesMenu.text = comidaInfo.titulo

        // Estado inicial del ícono
        updateFavIcon(comidaInfo.esFavorito)

        // Click en el corazón
        binding.cardFavBackground.setOnClickListener {
            val nuevoEstado = !comidaInfo.esFavorito
            comidaInfo.esFavorito = nuevoEstado
            updateFavIcon(nuevoEstado)

            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidaInfo.id,
                usuarioId = userId
            )

            onItemSelectedFav(favorito, nuevoEstado)
        }

        // Click en el item → abrir detalle
        binding.root.setOnClickListener {
            onItemClick(comidaInfo)
        }
    }

    private fun updateFavIcon(isFav: Boolean) {
        val icon = if (isFav) R.drawable.ic_heart_full else R.drawable.ic_heart_empty
        binding.iconFav.setImageResource(icon)
    }
}


