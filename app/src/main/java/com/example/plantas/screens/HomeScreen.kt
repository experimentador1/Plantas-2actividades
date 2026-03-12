package com.example.plantas.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plantas.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {

        // ── Top App Bar ──
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Eco,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Text(
                        text  = "Plantas del Sureste",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
        )

        // ── Search Bar ──
        SearchBarField(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        // ── Categorías ──
        SectionHeader(title = "Categorías Populares", actionLabel = "Ver todas")
        CategoryRow()

        // ── Planta del Día ──
        SectionHeader(title = "Planta del Día")
        PlantOfTheDayCard(onClick = { navController.navigate(Screen.Detail.route) })

        // ── Remedios Rápidos ──
        SectionHeader(title = "Remedios Rápidos")
        QuickRemediesGrid()

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// ── Componente: Search Bar ──
@Composable
fun SearchBarField(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(50),
        color    = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Filled.Search, contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text  = "¿Qué planta o síntoma buscas?",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Filled.Tune, contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ── Componente: Section Header ──
@Composable
fun SectionHeader(title: String, actionLabel: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        if (actionLabel != null) {
            TextButton(onClick = {}) {
                Text(text = actionLabel, color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ── Componente: Category Row ──
data class Category(val icon: ImageVector, val label: String)

@Composable
fun CategoryRow() {
    val categories = listOf(
        Category(Icons.Filled.Restaurant, "Digestión"),
        Category(Icons.Filled.Air,        "Respiración"),
        Category(Icons.Filled.Bedtime,    "Sueño"),
        Category(Icons.Filled.Healing,    "Piel"),
        Category(Icons.Filled.Psychology, "Estrés"),
    )
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categories.forEach { cat ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(72.dp)
            ) {
                Surface(
                    shape  = CircleShape,
                    color  = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(64.dp),
                    shadowElevation = 2.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(cat.icon, contentDescription = cat.label,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(28.dp))
                    }
                }
                Spacer(Modifier.height(6.dp))
                Text(cat.label, style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// ── Componente: Planta del Día Card ──
@Composable
fun PlantOfTheDayCard(onClick: () -> Unit) {
    ElevatedCard(
        onClick   = onClick,
        modifier  = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        shape     = RoundedCornerShape(28.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            AsyncImage(
                model          = "https://lh3.googleusercontent.com/aida-public/AB6AXuCd01iSpKhPjg-Guak7KbS8COQk-JRE4MmrlcPe0fDm5hpAiUdEDvVGN751qSwIZoq197WGacXJX_I43rdxm--dpmYHrgPaFycbaVkabbU6F_i7gk-IRhOt0jAiZG_IsuCNMpqbbRu0qE-1d-Oj-wR4MqLd_-y450udvRgvGK21CKq2Wp-WnyxRUxEz08flZKXLNZowajjgcSA2OboZj6gCgc866dm-Frn6wOA376qX5QM6CxVDJ504YQuSMncw8cln68clQwkvh2s",
                contentDescription = "Albahaca",
                contentScale   = ContentScale.Crop,
                modifier       = Modifier.fillMaxWidth().height(200.dp)
            )
            Surface(
                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                shape    = RoundedCornerShape(50),
                color    = MaterialTheme.colorScheme.primary
            ) {
                Text("DESTACADA",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color    = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Albahaca",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold)
                    Text("Ocimum basilicum",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp, fontStyle = FontStyle.Italic)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.FavoriteBorder,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null)
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Conocida por sus propiedades digestivas y antiespasmódicas. " +
                        "Ideal para aliviar el malestar estomacal y reducir el estrés.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(14.dp))
            Button(
                onClick  = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(50)
            ) {
                Text("Ver detalles", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null,
                    modifier = Modifier.size(18.dp))
            }
        }
    }
}

// ── Componente: Quick Remedies Grid ──
data class QuickRemedy(val icon: ImageVector, val title: String, val count: String)

@Composable
fun QuickRemediesGrid() {
    val items = listOf(
        QuickRemedy(Icons.Filled.Coffee,       "Infusiones", "12 recetas"),
        QuickRemedy(Icons.Filled.Science,      "Tinturas",   "8 métodos"),
        QuickRemedy(Icons.Filled.Spa,          "Compresas",  "5 técnicas"),
        QuickRemedy(Icons.Filled.LocalFlorist, "Pomadas",    "6 recetas"),
    )
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items.take(2).forEach { item ->
                QuickTile(item = item, modifier = Modifier.weight(1f))
            }
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items.drop(2).forEach { item ->
                QuickTile(item = item, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun QuickTile(item: QuickRemedy, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape    = RoundedCornerShape(16.dp),
        color    = MaterialTheme.colorScheme.surfaceContainer,
        border   = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(item.icon, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            Text(item.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(item.count, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}