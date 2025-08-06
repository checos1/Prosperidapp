package com.example.apphogares.backEnd.core.models.navegacion

data class Visita(
    var vistaHija: String,
    val conexion: Conexion,
    val fecha: String,
    val geo: String
)