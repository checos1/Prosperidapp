package com.example.apphogares.backEnd.core.models.contenidosRuta

data class Ruta(
    val codigo: String,
    val ordenar: Int,
    val cogestor: Boolean,
    val descripcion: String,
    val id: String,
    val nombre: String,
    val tematicas: List<Tematica>,
    val medalla: Medalla,
    var estadoRuta: String = ""
)