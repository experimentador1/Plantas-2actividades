package com.example.plantas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Actividad2ViewModel : ViewModel() {
    private val _fragmentText = MutableStateFlow("soy un fragmento")
    val fragmentText: StateFlow<String> = _fragmentText.asStateFlow()
}
