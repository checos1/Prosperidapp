package com.example.apphogares.backEnd.core.models.contenidosRuta

data class Actividade(
    val complete: String,
    val nombre: String,
    val palabras: String,
    val relacion: String,
    val tarjetas: List<Tarjeta>,
    val tipo: String
)
