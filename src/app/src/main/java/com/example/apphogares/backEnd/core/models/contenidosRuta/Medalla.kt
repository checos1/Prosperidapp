package com.example.apphogares.backEnd.core.models.contenidosRuta

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap

data class Medalla (
    var ruta: String = "",
    val nombre: String = "",
    val mensaje1: String = "",
    val mensaje2: String = "",
    val url: String = "",
    val urlActiva: String = "",
    val urlInactiva: String = "",
    var imagen: ImageBitmap? = null,
    var imagenActiva: ImageBitmap? = null,
    var imagenInactiva: ImageBitmap? = null
)
