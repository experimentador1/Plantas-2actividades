package com.example.plantas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plantas.PlantasViewModel
import com.example.plantas.plantDetailList
import com.example.plantas.ui.navigation.Screen

// ── Modelo de datos ──
data class Plant(
    val id          : String,
    val name        : String,
    val sciName     : String,
    val tag         : String,
    val description : String,
    val imageUrl    : String
)

val samplePlants = listOf(
    Plant(
        id          = "maguey",
        name        = "Maguey Morado",
        sciName     = "Tradescantia spathacea",
        tag         = "Antiinflamatorio",
        description = "Para inflamaciones, heridas y dolores reumáticos del sureste.",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuBo7rOF3_krpUz8pbko9ZlT-CDeX0NnsqNDDo-_CtNX8oY1LU_osYS93e_lSfoTRdzoW4h_sUkXdAISN1tbZEiljcS3FOYqiCV9kttm0UcbzlhptwEzp0Tuq-L5tR0pdZelOn7U6awjDSCC9VFscXGYFDIti8IMlRhbE-3sT2d9UKiDXiF_ax-20ZlGHhIeDSvx2zLwX8lYtuUAjxeBk8q8jdVguy9eZed6L4rEajk4FsplkHgA4yC0KQCSLeleQ9swXlTV47QBAcI"
    ),
    Plant(
        id          = "sabila",
        name        = "Aloe Vera (Sábila)",
        sciName     = "Aloe barbadensis",
        tag         = "Cicatrizante",
        description = "Para quemaduras leves, problemas digestivos y afecciones de la piel.",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuDAAEfOU1DG5oEx8xZSxdbNQur5Jefzrz1IMvvOd8Slo4cXuZChWIWjTNgphRtcsIcbRvj4_yeRpGzkyCqobtOYvZ8BxWa0enP2-NZZpv5EV0uy89kZebVDj1hzgbiEGCeHBKceRvTCD6s_-AjIdapO3-Bu-wE1-1VttCW32OcKg2CFmlCLhPszTMYliRP1dW5Cf6sL8LlRuAV9rFQva9eVIxpyJuc-SRPLbUi8Cf2dwVyCad3hf3GwsvETgCRba2pYaQkRLLTKvHs"
    ),
    Plant(
        id          = "menta",
        name        = "Menta",
        sciName     = "Mentha piperita",
        tag         = "Digestivo",
        description = "Relajante y digestivo. Excelente para infusiones que ayudan al bienestar.",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuBpxj_34t9do9UCOxuHh3M6qgrWXf5XWPS1hgO4moC559mgGFHE7StufCsbg_ElY7SRSkt_FvTmuFVRyivH8PEU7l5PdVF1b1H2FC2hGea0QrB_i8vA4pVWZh_klamMZ4V8mSqXrBhLt2T-lx45yCSWOXq2nOuaggM61BIE730HV1Ni8X20RQZCx2vdquHwhB3xDJRX92s2ICVqWgzE9GCIVJaHggfxyVWRe4Y2BrJwWI7MwxXIlAQYZb8H3fypqAuwXzB8QuoW5ic"
    ),
    Plant(
        id          = "achiote",
        name        = "Achiote",
        sciName     = "Bixa orellana",
        tag         = "Colorante Natural",
        description = "Medicina tradicional maya para afecciones de la piel y digestión.",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuDxETJPSszw6vK6xnqmGREHaIn6-sHPyDqZ2NKlJHSPhwkznSkwNbfybiBvcRT9Y3L25lThn_n6EPffjwMr9xyZbByT8SvNOenm33-w9ilrNz1-WbLe7VzCkHWlvZlCyad5_J6XTC4BjtkijLYCxHqFpqfvoLBVrFTKNsJpkz6QFK2mg0WagoaHKm4XWouZlIfiNdCXFs87mrB7ZzPgM8yJ8eZajrqtjrIMSgrJTavwAd8Ro4MVdIoP_SgY1d4u15DIIRIGv76PPp8"
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController : NavController,
    viewModel     : PlantasViewModel = viewModel()
) {
    val filters  = listOf("Todas", "Hierbas", "Árboles", "Arbustos", "Endémicas")
    var selected by remember { mutableStateOf("Todas") }

    Column(modifier = Modifier.fillMaxSize()) {

        // ── Top App Bar ──
        TopAppBar(
            title = {
                Text("Catálogo", style = MaterialTheme.typography.titleLarge)
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Filtrar")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
        )

        // ── Search Bar ──
        SearchBarField(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // ── Filter Chips ──
        LazyRow(
            contentPadding        = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier              = Modifier.padding(bottom = 8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    selected = selected == filter,
                    onClick  = { selected = filter },
                    label    = { Text(filter) },
                    colors   = FilterChipDefaults.filterChipColors(
                        selectedContainerColor   = MaterialTheme.colorScheme.secondaryContainer,
                        selectedLabelColor       = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                )
            }
        }

        // ── Lista de plantas ──
        LazyColumn(
            contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(samplePlants) { plant ->
                PlantListCard(
                    plant   = plant,
                    onClick = {
                        // Busca el detalle completo de la planta y lo selecciona
                        val detail = plantDetailList.find { it.name == plant.name }
                        if (detail != null) {
                            viewModel.selectPlant(detail)
                            navController.navigate(Screen.Detail.route)
                        }
                    }
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

// ── Componente: Plant List Card ──
@Composable
fun PlantListCard(plant: Plant, onClick: () -> Unit) {
    ElevatedCard(
        onClick   = onClick,
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {

            // Imagen
            AsyncImage(
                model              = plant.imageUrl,
                contentDescription = plant.name,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .width(112.dp)
                    .fillMaxHeight()
            )

            // Contenido
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text          = plant.tag.uppercase(),
                    color         = MaterialTheme.colorScheme.primary,
                    style         = MaterialTheme.typography.labelSmall,
                    fontWeight    = FontWeight.Bold,
                    letterSpacing = 0.6.sp
                )
                Text(
                    text       = plant.name,
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text     = plant.description,
                    style    = MaterialTheme.typography.bodySmall,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                FilledTonalButton(
                    onClick        = onClick,
                    modifier       = Modifier.height(32.dp),
                    shape          = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                    colors         = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor   = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text("Ver más", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}