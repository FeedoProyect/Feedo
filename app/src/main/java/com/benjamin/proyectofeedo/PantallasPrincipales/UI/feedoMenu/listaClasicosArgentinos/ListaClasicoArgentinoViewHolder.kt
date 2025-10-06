package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaClasicoArgentino.listaDeComidasClasicoArgentino

import android.graphics.drawable.Animatable
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
        auth: Auth,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit
    ) {
        // Nombre e imagen
        binding.tvComidaSeccionesMenu.text = comidasModel.recetas.titulo
        Picasso.get()
            .load(comidasModel.recetas.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)

        // Estado inicial del corazón
        setFavoriteIcon(comidasModel.recetas.esFavorito)

        // Click en el corazón
        binding.iconFav.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )

            // Cambia el estado local
            val nuevoEstado = !comidasModel.recetas.esFavorito
            comidasModel.recetas.esFavorito = nuevoEstado
            animateHeart(nuevoEstado)

            // Notifica el cambio al callback (para Supabase)
            onItemSelectedFav(favorito)
        }
    }

    /** Cambia el ícono del corazón según el estado actual */
    private fun setFavoriteIcon(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.avd_heart_fill else R.drawable.avd_heart_unfill
        binding.iconFav.setImageResource(drawableRes)
    }

    /** Reproduce la animación del corazón */
    private fun animateHeart(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.avd_heart_fill else R.drawable.avd_heart_unfill
        binding.iconFav.setImageResource(drawableRes)
        val drawable = binding.iconFav.drawable
        if (drawable is Animatable) drawable.start()
    }
}

