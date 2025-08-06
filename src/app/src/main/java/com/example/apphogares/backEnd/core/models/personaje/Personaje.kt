package com.example.apphogares.backEnd.core.models.personaje

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class Personaje (
    val personaje: String = "",
    val colorsPerson: Color = Color(red = 0, green = 0, blue = 0, alpha = 0xFF),
    val icons: String = ""
)