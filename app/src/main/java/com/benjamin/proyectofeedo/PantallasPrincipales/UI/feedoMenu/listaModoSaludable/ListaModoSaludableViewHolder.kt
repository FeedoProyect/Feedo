package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaModoSaludable

import android.graphics.drawable.Animatable
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaModoSaludableViewHolder(
    private val binding: ItemSeccionesMenuBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        // Mostrar título e imagen
        binding.tvComidaSeccionesMenu.text = comidasModel.recetas.titulo
        Picasso.get()
            .load(comidasModel.recetas.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)

        // Estado inicial del corazón
        setFavoriteIcon(comidasModel.recetas.esFavorito)

        // Click en el ícono de favorito
        binding.iconFav.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val nuevoEstado = !comidasModel.recetas.esFavorito
            comidasModel.recetas.esFavorito = nuevoEstado

            // Ejecutar animación
            animateHeart(nuevoEstado)

            // Notificar el cambio (para Supabase)
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )
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
        val drawable = binding.iconFav.context.getDrawable(drawableRes)
        binding.iconFav.setImageDrawable(drawable)
        (drawable as? Animatable)?.start()

        // Efecto "pop" al pulsar
        binding.iconFav.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(150)
            .withEndAction {
                binding.iconFav.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }
            .start()
    }
}




