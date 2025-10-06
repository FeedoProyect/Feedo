package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaSeccionesMenu.listaDeComidasSecciones

import android.graphics.drawable.Animatable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaSeccionesViewHolder(
    private val binding: ItemSeccionesMenuBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        auth: Auth,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit
    ) {
        val TAG = "ListaSeccionesVH"

        binding.tvComidaSeccionesMenu.text = comidasModel.recetas.titulo
        Picasso.get()
            .load(comidasModel.recetas.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)

        // Estado inicial del corazón
        setFavoriteIcon(comidasModel.recetas.esFavorito)
        Log.d(TAG, "Render inicial → ${comidasModel.recetas.titulo}, esFavorito=${comidasModel.recetas.esFavorito}")

        // Click del corazón
        binding.cardFavBackground.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener

            val nuevoEstado = !comidasModel.recetas.esFavorito
            Log.d(TAG, "Click → anterior=${comidasModel.recetas.esFavorito}, nuevo=$nuevoEstado")

            comidasModel.recetas.esFavorito = nuevoEstado

            // Actualizamos icono
            setFavoriteIcon(nuevoEstado)
            Log.d(TAG, "setFavoriteIcon llamado con nuevoEstado=$nuevoEstado")

            // Animación
            animateHeart(nuevoEstado)
            Log.d(TAG, "animateHeart llamado con fav=$nuevoEstado")

            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }

    private fun setFavoriteIcon(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.avd_heart_fill else R.drawable.avd_heart_unfill
        Log.d("ListaSeccionesVH", "setFavoriteIcon → drawable=$drawableRes")
        binding.iconFav.setImageResource(drawableRes)
    }

    private fun animateHeart(fav: Boolean) {
        val drawable = binding.iconFav.drawable
        if (drawable is Animatable) {
            Log.d("ListaSeccionesVH", "animateHeart → start() ejecutado")
            drawable.start()
        } else {
            Log.d("ListaSeccionesVH", "animateHeart → drawable NO es Animatable")
        }
    }
}

