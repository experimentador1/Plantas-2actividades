package com.example.plantas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
data class SymptomDetail(
    val name        : String,
    val description : String,
    val plantNames  : List<String>,   // deben coincidir con plantDetailList
    val tips        : List<String>
)

val symptomDetailList = listOf(
    SymptomDetail(
        name        = "Dolor de estómago",
        description = "El dolor de estómago puede ser causado por indigestión, gases o inflamación. Las plantas medicinales del sureste ofrecen un alivio natural, efectivo y suave para el sistema digestivo.",
        plantNames  = listOf("Menta", "Achiote"),
        tips        = listOf(
            "Evitar comidas pesadas o muy condimentadas.",
            "Mantenerse hidratado con pequeños sorbos de agua tibia.",
            "Aplicar compresas calientes en la zona abdominal.",
            "Caminar suavemente para facilitar el movimiento intestinal."
        )
    ),
    SymptomDetail(
        name        = "Gripe y Tos",
        description = "La gripe y la tos son afecciones respiratorias comunes. Las plantas medicinales del sureste mexicano tienen propiedades expectorantes y antivirales que ayudan a aliviar los síntomas.",
        plantNames  = listOf("Maguey Morado", "Menta"),
        tips        = listOf(
            "Descansar lo suficiente para que el cuerpo se recupere.",
            "Mantenerse bien hidratado con líquidos calientes.",
            "Ventilar los espacios donde permaneces.",
            "Evitar el contacto con personas vulnerables."
        )
    ),
    SymptomDetail(
        name        = "Insomnio",
        description = "El insomnio puede ser consecuencia del estrés, ansiedad o malos hábitos de sueño. Existen plantas del sureste con propiedades sedantes suaves que ayudan a conciliar el sueño.",
        plantNames  = listOf("Menta", "Achiote"),
        tips        = listOf(
            "Establecer horarios fijos para dormir y despertar.",
            "Evitar pantallas al menos 1 hora antes de dormir.",
            "Crear un ambiente oscuro, fresco y tranquilo.",
            "Reducir el consumo de cafeína por las tardes."
        )
    ),
    SymptomDetail(
        name        = "Inflamación",
        description = "La inflamación es la respuesta natural del cuerpo ante lesiones o irritaciones. Las plantas antiinflamatorias del sureste ayudan a reducirla de forma natural y segura.",
        plantNames  = listOf("Maguey Morado", "Aloe Vera (Sábila)"),
        tips        = listOf(
            "Aplicar frío en la zona inflamada las primeras 24 horas.",
            "Elevar la extremidad afectada para reducir hinchazón.",
            "Evitar actividad física intensa hasta reducir la inflamación.",
            "Consultar médico si la inflamación persiste más de 3 días."
        )
    ),
    SymptomDetail(
        name        = "Estrés y Ansiedad",
        description = "El estrés crónico puede afectar gravemente la salud. Las plantas adaptógenas y relajantes del sureste ayudan a calmar el sistema nervioso de manera natural.",
        plantNames  = listOf("Menta", "Aloe Vera (Sábila)"),
        tips        = listOf(
            "Practicar respiración profunda durante 5 minutos al día.",
            "Reducir el consumo de azúcar y estimulantes.",
            "Realizar actividad física moderada regularmente.",
            "Dedicar tiempo a actividades que generen bienestar."
        )
    ),
    SymptomDetail(
        name        = "Heridas Leves",
        description = "Las heridas superficiales pueden tratarse con plantas cicatrizantes y antisépticas del sureste, acelerando la recuperación de forma natural.",
        plantNames  = listOf("Aloe Vera (Sábila)", "Maguey Morado"),
        tips        = listOf(
            "Limpiar la herida con agua y jabón neutro primero.",
            "No cubrir heridas pequeñas que cicatrizan mejor al aire.",
            "Evitar tocar la zona con las manos sucias.",
            "Vigilar signos de infección como enrojecimiento o pus."
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomDetailScreen(
    navController : NavController,
    viewModel     : PlantasViewModel = viewModel()
) {
    val symptom by viewModel.selectedSymptom.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        // ── Top Bar ──
        item {
            TopAppBar(
                title = { Text(symptom.name, style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )
        }

        // ── Descripción ──
        item {
            Card(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text       = "Descripción",
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text       = symptom.description,
                        style      = MaterialTheme.typography.bodyMedium,
                        color      = MaterialTheme.colorScheme.onPrimaryContainer,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // ── Plantas recomendadas ──
        item {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text       = "Plantas Recomendadas",
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier   = Modifier.weight(1f)
                )
                TextButton(onClick = {}) {
                    Text("Ver todas", color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Plantas como cards clicables
        val matchedPlants = plantDetailList.filter { it.name in symptom.plantNames }
        items(matchedPlants) { plant ->
            ElevatedCard(
                onClick = {
                    viewModel.selectPlant(plant)
                    navController.navigate(Screen.Detail.route)
                },
                modifier  = Modifier.padding(horizontal = 16.dp, vertical = 4.dp).fillMaxWidth(),
                shape     = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AsyncImage(
                        model              = plant.imageUrl,
                        contentDescription = plant.name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .size(72.dp)
                            .padding(0.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text       = plant.name,
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text  = plant.properties.take(60) + "...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // ── Consejos Naturales ──
        item {
            Spacer(Modifier.height(16.dp))
            Text(
                text       = "Consejos Naturales",
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier   = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Card(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Column(
                    modifier            = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    symptom.tips.forEach { tip ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment     = Alignment.Top
                        ) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint     = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp).padding(top = 2.dp)
                            )
                            Text(
                                text      = tip,
                                style     = MaterialTheme.typography.bodyMedium,
                                color     = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}