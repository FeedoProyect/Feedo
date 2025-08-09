package com.benjamin.proyectofeedo.data

import com.benjamin.proyectofeedo.data.response.CatalogosName

class CatalogosProvived{
    companion object{
        val ListaComidas = mutableListOf<CatalogosName>(
            CatalogosName(name = "Desayunos", imageURL = "https://i.pinimg.com/736x/8c/37/56/8c375683ba1df40fb38db3d55eefd2ce.jpg"),
            CatalogosName(name = "Almuerzos", imageURL = "https://i.pinimg.com/736x/52/80/c6/5280c63d836427687d36e12ab0e18ad7.jpg"),
            CatalogosName(name = "Meriendas", imageURL = "https://i.pinimg.com/736x/1f/37/d8/1f37d88c76b5fac0b863fa836d8df398.jpg"),
            CatalogosName(name = "Cena", imageURL = "https://i.pinimg.com/736x/c6/e4/ec/c6e4ec9a0e4ea8551fac129a6bbe982e.jpg"),
            CatalogosName(name = "Snacks", imageURL = "https://i.pinimg.com/736x/59/49/5d/59495d8c6c5a0c1de23fa6bd02683851.jpg"),
            CatalogosName(name = "Vegetarianas", imageURL = "https://i.pinimg.com/736x/08/c7/0d/08c70d473888e35bd1c4cd9d1d2983d7.jpg"),
            CatalogosName(name = "Pastas", imageURL = "https://i.pinimg.com/736x/05/7f/14/057f14a4fcdc816b8bb288fa9ba97249.jpg"),
        )
    }
}