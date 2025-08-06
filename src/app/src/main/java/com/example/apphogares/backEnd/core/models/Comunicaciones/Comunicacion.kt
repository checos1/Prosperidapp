package com.example.apphogares.backEnd.core.models.Comunicaciones

import kotlinx.serialization.Serializable

@Serializable
data class Comunicacion(
    val nombre: String = "",  // Campo nullable
    val telefono1: String = "",  // Campo nullable
    val telefono: String = "",  // Campo nullable
    val cuenta_facebook: String = "",
    val cuenta_instagram: String = "",
    val cuenta_whatsApp: String = "",
    val cuenta_tiktok: String = "",
    val cuenta_x: String = "",
    val logo: String = "",
    val imagenAuxiliar: String = "",  // Campo nullable
    val videoUrl: String = "",  // Campo nullable

)
