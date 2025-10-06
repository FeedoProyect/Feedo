package com.benjamin.proyectofeedo.PantallasPrincipales.UI.utils

import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.benjamin.proyectofeedo.R

fun toggleHeartAnimation(
    heartImage: ImageView,
    isFav: Boolean
) {
    val drawableRes = if (isFav)
        R.drawable.avd_heart_fill
    else
        R.drawable.avd_heart_unfill

    val drawable = ContextCompat.getDrawable(heartImage.context, drawableRes)
    heartImage.setImageDrawable(drawable)
    (drawable as? AnimatedVectorDrawable)?.start()

    // Efecto "pop"
    ObjectAnimator.ofFloat(heartImage, "scaleX", 1f, 1.2f, 1f).apply {
        duration = 180
        start()
    }
    ObjectAnimator.ofFloat(heartImage, "scaleY", 1f, 1.2f, 1f).apply {
        duration = 180
        start()
    }
}
