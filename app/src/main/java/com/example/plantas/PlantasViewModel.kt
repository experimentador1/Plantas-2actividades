package com.example.plantas

import androidx.lifecycle.ViewModel
import com.example.plantas.ui.screens.FavoritePlant
import com.example.plantas.ui.screens.Plant
import com.example.plantas.ui.screens.PlantDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.plantas.ui.screens.RemedyDetail
import com.example.plantas.ui.screens.SymptomDetail
import com.example.plantas.ui.screens.remedyDetailList
import com.example.plantas.ui.screens.symptomDetailList

class PlantasViewModel : ViewModel() {

    // ── Planta seleccionada para ver detalle ──
    private val _selectedPlant = MutableStateFlow(plantDetailList[0])
    val selectedPlant: StateFlow<PlantDetail> = _selectedPlant.asStateFlow()

    fun selectPlant(plant: PlantDetail) {
        _selectedPlant.value = plant
    }

    // ── Favoritos ──
    private val _favorites = MutableStateFlow<List<FavoritePlant>>(emptyList())
    val favorites: StateFlow<List<FavoritePlant>> = _favorites.asStateFlow()

    fun toggleFavorite(plant: PlantDetail) {
        val current = _favorites.value
        val exists  = current.any { it.name == plant.name }
        _favorites.value = if (exists) {
            current.filter { it.name != plant.name }
        } else {
            current + FavoritePlant(
                name        = plant.name,
                benefit     = plant.properties.take(40) + "...",
                description = plant.properties,
                imageUrl    = plant.imageUrl,
                category    = "Hierbas"
            )
        }
    }

    fun isFavorite(plantName: String): Boolean {
        return _favorites.value.any { it.name == plantName }
    }

    fun removeFavorite(plantName: String) {
        _favorites.value = _favorites.value.filter { it.name != plantName }
    }
    // ── Remedio seleccionado ──
    private val _selectedRemedy = MutableStateFlow(remedyDetailList[0])
    val selectedRemedy: StateFlow<RemedyDetail> = _selectedRemedy.asStateFlow()

    fun selectRemedy(remedy: RemedyDetail) {
        _selectedRemedy.value = remedy
    }

    // ── Síntoma seleccionado ──
    private val _selectedSymptom = MutableStateFlow(symptomDetailList[0])
    val selectedSymptom: StateFlow<SymptomDetail> = _selectedSymptom.asStateFlow()

    fun selectSymptom(symptom: SymptomDetail) {
        _selectedSymptom.value = symptom
    }
}

// ── Lista completa de plantas con detalle ──
val plantDetailList = listOf(
    PlantDetail(
        name     = "Maguey Morado",
        sciName  = "Tradescantia spathacea",
        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBo7rOF3_krpUz8pbko9ZlT-CDeX0NnsqNDDo-_CtNX8oY1LU_osYS93e_lSfoTRdzoW4h_sUkXdAISN1tbZEiljcS3FOYqiCV9kttm0UcbzlhptwEzp0Tuq-L5tR0pdZelOn7U6awjDSCC9VFscXGYFDIti8IMlRhbE-3sT2d9UKiDXiF_ax-20ZlGHhIeDSvx2zLwX8lYtuUAjxeBk8q8jdVguy9eZed6L4rEajk4FsplkHgA4yC0KQCSLeleQ9swXlTV47QBAcI",
        properties     = "Es ampliamente reconocido por sus potentes capacidades antiinflamatorias. Se utiliza para acelerar la cicatrización de heridas superficiales y aliviar dolores reumáticos.",
        steps          = listOf(
            "Lavar cuidadosamente 2 o 3 hojas frescas.",
            "Hervir un litro de agua y añadir las hojas.",
            "Reposar tapado 10 minutos, colar y servir."
        ),
        contraindications = "El contacto con la savia puede causar irritación en piel sensible. Usar guantes durante su manipulación."
    ),
    PlantDetail(
        name     = "Aloe Vera (Sábila)",
        sciName  = "Aloe barbadensis",
        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDAAEfOU1DG5oEx8xZSxdbNQur5Jefzrz1IMvvOd8Slo4cXuZChWIWjTNgphRtcsIcbRvj4_yeRpGzkyCqobtOYvZ8BxWa0enP2-NZZpv5EV0uy89kZebVDj1hzgbiEGCeHBKceRvTCD6s_-AjIdapO3-Bu-wE1-1VttCW32OcKg2CFmlCLhPszTMYliRP1dW5Cf6sL8LlRuAV9rFQva9eVIxpyJuc-SRPLbUi8Cf2dwVyCad3hf3GwsvETgCRba2pYaQkRLLTKvHs",
        properties     = "Cicatrizante y antiinflamatorio natural. Ideal para quemaduras leves, problemas digestivos y afecciones de la piel.",
        steps          = listOf(
            "Cortar una hoja gruesa de la planta.",
            "Extraer el gel transparente del interior.",
            "Aplicar directamente sobre la zona afectada."
        ),
        contraindications = "No consumir en grandes cantidades. Puede causar diarrea si se ingiere el látex amarillo."
    ),
    PlantDetail(
        name     = "Menta",
        sciName  = "Mentha piperita",
        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBpxj_34t9do9UCOxuHh3M6qgrWXf5XWPS1hgO4moC559mgGFHE7StufCsbg_ElY7SRSkt_FvTmuFVRyivH8PEU7l5PdVF1b1H2FC2hGea0QrB_i8vA4pVWZh_klamMZ4V8mSqXrBhLt2T-lx45yCSWOXq2nOuaggM61BIE730HV1Ni8X20RQZCx2vdquHwhB3xDJRX92s2ICVqWgzE9GCIVJaHggfxyVWRe4Y2BrJwWI7MwxXIlAQYZb8H3fypqAuwXzB8QuoW5ic",
        properties     = "Relajante y digestivo. Excelente para infusiones que ayudan a la digestión y refrescan el aliento.",
        steps          = listOf(
            "Lavar un puñado de hojas frescas de menta.",
            "Hervir agua y añadir las hojas.",
            "Reposar 5 minutos, colar y servir caliente."
        ),
        contraindications = "Evitar en niños menores de 2 años. No aplicar aceite esencial directo en la piel sin diluir."
    ),
    PlantDetail(
        name     = "Achiote",
        sciName  = "Bixa orellana",
        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDxETJPSszw6vK6xnqmGREHaIn6-sHPyDqZ2NKlJHSPhwkznSkwNbfybiBvcRT9Y3L25lThn_n6EPffjwMr9xyZbByT8SvNOenm33-w9ilrNz1-WbLe7VzCkHWlvZlCyad5_J6XTC4BjtkijLYCxHqFpqfvoLBVrFTKNsJpkz6QFK2mg0WagoaHKm4XWouZlIfiNdCXFs87mrB7ZzPgM8yJ8eZajrqtjrIMSgrJTavwAd8Ro4MVdIoP_SgY1d4u15DIIRIGv76PPp8",
        properties     = "Digestivo y colorante natural. Usado en la medicina tradicional maya para afecciones de la piel.",
        steps          = listOf(
            "Extraer las semillas de la vaina madura.",
            "Hervir en agua por 10 minutos.",
            "Colar y aplicar o consumir según el uso."
        ),
        contraindications = "Consultar médico si se usa durante el embarazo. Puede causar reacciones alérgicas en personas sensibles."
    ),
)