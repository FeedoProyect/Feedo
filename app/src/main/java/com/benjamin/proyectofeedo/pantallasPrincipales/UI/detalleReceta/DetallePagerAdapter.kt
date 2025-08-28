
package com.benjamin.proyectofeedo.pantallasPrincipales.UI.detalleReceta

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.detalleReceta.tabs.InstruccionesFragment
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.detalleReceta.tabs.IngredientesFragment

class DetallePagerAdapter(
    fragment: Fragment,
    private val ingredientes: ArrayList<String>,
    private val pasos: ArrayList<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            IngredientesFragment.newInstance(ingredientes)
        else
            InstruccionesFragment.newInstance(pasos)
    }
}
