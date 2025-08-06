package com.example.apphogares.backEnd.core.models.navegacion

data class Vista(
    var nps: Int,
    var posicion: String,
    val puntosneg: Int,
    var puntospos: Int,
    val visitas: MutableList<Visita>,
    val vista: String
)