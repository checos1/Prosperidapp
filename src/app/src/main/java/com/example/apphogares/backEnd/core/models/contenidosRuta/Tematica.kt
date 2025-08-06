package com.example.apphogares.backEnd.core.models.contenidosRuta

data class Tematica(
    val actividades: List<Actividade>,
    val ordenar: Int,
    val audio: String,
    val portada: String,
    val codigo: String,
    val id: String,
    val iteracciones: List<Iteraccione>,
    val nombre: String
)