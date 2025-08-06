package com.example.apphogares.backEnd.core.models.navegacion

data class Navegacion(
    var integrantes: MutableList<Integrante>,
    val nps: Int = 0,
    var monedas: Int = 0,
    var vidas: Int = 0,
)