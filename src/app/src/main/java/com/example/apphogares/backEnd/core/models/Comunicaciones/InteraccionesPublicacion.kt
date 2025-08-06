package com.example.apphogares.backEnd.core.models.Comunicaciones

data class InteraccionesPublicacion(
    val id: String,
    val likes: Int = 0,
    val compartidos: Int = 0,
    val ultimaActualizacion: String = ""
)

