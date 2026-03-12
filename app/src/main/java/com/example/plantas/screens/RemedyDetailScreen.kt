package com.example.plantas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plantas.PlantasViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

// ── Modelo de datos ──
data class RemedyDetail(
    val title       : String,
    val subtitle    : String,
    val imageUrl    : String,
    val benefit     : String,
    val ingredients : List<String>,
    val steps       : List<String>
)

val remedyDetailList = listOf(
    RemedyDetail(
        title       = "Infusión para la tos",
        subtitle    = "Gordolobo y Eucalipto",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuA6X-H-x2BokDh3S45rAkTdok9mk7B3WvP1tg4URkqP17SSInG8iQEU2SuV9AAi5Z2odiw4a1Vnp4wMf-fREluNNuekyl2yjrIGMeIZWhls41AWNDjEDjV11ctAoy38-DZrQ7ajMRizwZ9TY1Kcr0f55bRfv2mee1UUjzFYJnFxaNj3op0nqPIh1ko0IHgvw7JPGb0bpAyDoiYf5l34m-lTyFlmjJgupVnZgRqeL76KtbSs_v4wyOCxcLhYZhI_SB9FEIoXL1ny9KI",
        benefit     = "Alivia la irritación de garganta y ayuda a descongestionar las vías respiratorias.",
        ingredients = listOf("Hojas de gordolobo", "Eucalipto", "Miel", "Agua (1 taza)"),
        steps       = listOf(
            "Hervir el agua en un cazo pequeño.",
            "Agregar las hojas de gordolobo y el eucalipto.",
            "Dejar reposar durante 5-10 minutos tapado.",
            "Colar y endulzar con miel al gusto."
        )
    ),
    RemedyDetail(
        title       = "Pomada de caléndula",
        subtitle    = "Caléndula y Cera de abeja",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuADU8Ge3xdjaN-j09FgNdfKAMQVm4AaugF1QJ1z4vHl5u3DOXzkFsLJC4SDSSxXZdwTBv5yDFU6NpnDYeU28qM89xyTrfoZyRn0SsITYIEckKevxDTccvoLCt5gTAcrBHXJoEXQ3OQgW2U0-vp_POkoHQaOLRgCdC4GHPr2gaM5KiKVEzxv_ldJK68oDMqp_rGPym8xzJniwxO7tI641byYmgLYd-7VT-3sKOg9KXsdm3ZCVHBrj269ebwVGoLGfPO7FNu3tPOiW1Q",
        benefit     = "Ideal para cicatrización de heridas leves, quemaduras e irritaciones cutáneas.",
        ingredients = listOf("Flores de caléndula", "Cera de abeja", "Aceite de oliva", "Vitamina E"),
        steps       = listOf(
            "Macerar las flores en aceite de oliva por 4 semanas.",
            "Filtrar el aceite y calentarlo a baño maría.",
            "Añadir la cera de abeja derretida y mezclar.",
            "Verter en frascos y dejar solidificar."
        )
    ),
    RemedyDetail(
        title       = "Té digestivo",
        subtitle    = "Manzanilla y Menta",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuAT1N4upMsgUZsaDd8QkiczOEp3m7XURyTG4-LDyGF2ITMJxZO8cemFpLTlps1J8uFBEKgwSbaPGy3nMCrrd4QeinS_UxZIKnVT0D_dwsNyai-FiJpsCjr5IUeLNQRhQcQfJ-HfaufcjQ4vU42CnWVoWjMz1KzfmyeaSKsAwP9UR0FOPbUQBcydF2tRUYIIOVmW4Zz0jSIkhFURRljQ-4ekWeInrqiUQ1XN4hmd9W9MyIWqNMWNtzqtXgcMjWhGzyK6v0RVoW6dD6g",
        benefit     = "Mezcla clásica para reducir la inflamación abdominal y mejorar la digestión.",
        ingredients = listOf("Flores de manzanilla", "Hojas de menta", "Agua (1 taza)", "Miel opcional"),
        steps       = listOf(
            "Hervir una taza de agua.",
            "Añadir manzanilla y menta fresca.",
            "Tapar y reposar 7 minutos.",
            "Colar, endulzar al gusto y servir."
        )
    ),
    RemedyDetail(
        title       = "Jarabe de Buganvilla",
        subtitle    = "Buganvilla y Miel",
        imageUrl    = "https://lh3.googleusercontent.com/aida-public/AB6AXuCz7XTD5KTQ0d30sRBy65QbkRw_y1czOqY8XIGZq5eIHUvx9WE0u1NduzLY_8IofcbPtqK_LsOGrz9-WeuckfDEZBV3R1tdy5frVfiVFbkF4Xy5C6iVMRA-6jTj5LAoDN2zYrzoL9c1tl-jj0kXxZMkZcGnR0ay6ARz1yVitHYNvj5X5m8mR4gVEQzuS6z4TIdwMGMBB5ttwA_0k1YQW5_W41k-rNeU7uW92Pf0dFagcbvbtJRuX6o26ijqBFuoAy0OHRN1NJl6fkg",
        benefit     = "Remedio tradicional del sureste para calmar la tos seca y recurrente.",
        ingredients = listOf("Flores de buganvilla", "Miel de abeja", "Limón", "Agua (2 tazas)"),
        steps       = listOf(
            "Hervir las flores de buganvilla en el agua por 10 min.",
            "Colar y dejar enfriar un poco.",
            "Añadir miel y unas gotas de limón.",
            "Tomar una cucharada cada 4 horas."
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemedyDetailScreen(
    navController : NavController,
    viewModel     : PlantasViewModel = viewModel()
) {
    val remedy by viewModel.selectedRemedy.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            // ── Top Bar ──
            TopAppBar(
                title = { Text("Detalle del Remedio", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Share, contentDescription = "Compartir",
                            tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            // ── Hero Image ──
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                AsyncImage(
                    model              = remedy.imageUrl,
                    contentDescription = remedy.title,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                            startY = 300f
                        )
                    )
                )
            }

            // ── Título ──
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text       = remedy.title,
                    style      = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text       = remedy.subtitle,
                    style      = MaterialTheme.typography.titleMedium,
                    color      = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier   = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // ── Beneficios ──
            Card(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        Icons.Filled.HealthAndSafety,
                        contentDescription = null,
                        tint     = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp).padding(top = 2.dp)
                    )
                    Column {
                        Text(
                            text       = "Beneficios",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color      = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text      = remedy.benefit,
                            style     = MaterialTheme.typography.bodyMedium,
                            color     = MaterialTheme.colorScheme.onPrimaryContainer,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Ingredientes ──
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Filled.ShoppingBasket, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary)
                Text(
                    text       = "Ingredientes",
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(12.dp))
            Column(
                modifier            = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                remedy.ingredients.forEach { ingredient ->
                    ElevatedCard(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier          = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                            )
                            Text(ingredient, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Preparación ──
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Filled.RestaurantMenu, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary)
                Text(
                    text       = "Preparación",
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(12.dp))
            Column(
                modifier            = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                remedy.steps.forEachIndexed { index, step ->
                    PrepStep(number = index + 1, text = step)
                }
            }

            Spacer(Modifier.height(100.dp))
        }

        // ── Botón sticky ──
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface)
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .navigationBarsPadding()
        ) {
            Button(
                onClick  = {},
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape    = RoundedCornerShape(50)
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Guardar en Favoritos", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}