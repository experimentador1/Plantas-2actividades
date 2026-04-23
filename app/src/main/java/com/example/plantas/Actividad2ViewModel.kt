package com.example.plantas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Actividad2ViewModel : ViewModel() {
    data class UiState(
        val titulo: String = "Video de la planta",
        val videoId: String = "M7lc1UVf-VE",
        val cargando: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun cambiarVideo(nuevoId: String) {
        _uiState.update { it.copy(videoId = nuevoId) }
    }
}
