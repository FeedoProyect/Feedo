package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaSeccionesMenu.listaDeComidasSecciones

import android.animation.ObjectAnimator
import android.graphics.drawable.Animatable
import android.util.Log
import android.view.animation.OvershootInterpolator
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
        onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit
    ) {
        binding.tvComidaSeccionesMenu.text = comidasModel.recetas.titulo

        Picasso.get()
            .load(comidasModel.recetas.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)

        // Estado inicial del corazón
        setFavoriteIcon(comidasModel.recetas.esFavorito)

        // Click del corazón
        binding.cardFavBackground.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val nuevoEstado = !comidasModel.recetas.esFavorito

            comidasModel.recetas.esFavorito = nuevoEstado
            setFavoriteIcon(nuevoEstado)
            animateHeartSmooth()

            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )

            onItemSelectedFav(favorito, nuevoEstado)
        }
    }

    private fun setFavoriteIcon(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.ic_heart_full else R.drawable.ic_heart_empty
        binding.iconFav.setImageResource(drawableRes)
        Log.d("FeedoFav", "Pintando ícono favorito=$fav en posición $adapterPosition")
    }

    private fun animateHeartSmooth() {
        val scaleX = ObjectAnimator.ofFloat(binding.iconFav, "scaleX", 1f, 1.3f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.iconFav, "scaleY", 1f, 1.3f, 1f)
        scaleX.interpolator = OvershootInterpolator()
        scaleY.interpolator = OvershootInterpolator()
        scaleX.duration = 250
        scaleY.duration = 250
        scaleX.start()
        scaleY.start()

        val drawable = binding.iconFav.drawable
        if (drawable is Animatable) drawable.start()
    }
}




