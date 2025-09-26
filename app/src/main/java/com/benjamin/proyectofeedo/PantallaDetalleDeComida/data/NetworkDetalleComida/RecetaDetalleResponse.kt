import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.IngredienteModel
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.model.RecetaDetalleModel
import kotlinx.serialization.Serializable

@Serializable
data class RecetaDetalleResponse(
    val id: Int,
    val titulo: String,
    val imagen: String? = null,
    val descripcion: String? = null,
    val tiempo_preparacion: String? = null,
    val pasos: String? = null,
    val receta_ingredientes: List<IngredienteResponse> = emptyList()
)

@Serializable
data class IngredienteResponse(
    val ingredientes: IngredienteNetwork
)

@Serializable
data class IngredienteNetwork(
    val id: Int,
    val nombre: String,
    val img_ingrediente: String? = null
)


fun RecetaDetalleResponse.toDomain() = RecetaDetalleModel(
    id = id,
    titulo = titulo,
    imagen = imagen,
    descripcion = descripcion,
    tiempoPreparacion = tiempo_preparacion,
    pasos = pasos?.split("\n") ?: emptyList(), // divide string en lista
    ingredientes = receta_ingredientes.map {
        IngredienteModel(
            id = it.ingredientes.id,
            nombre = it.ingredientes.nombre,
            imagen = it.ingredientes.img_ingrediente
        )
    }
)
