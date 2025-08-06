package com.example.apphogares.backEnd.core.models.actividadesRuta

import kotlinx.serialization.Serializable


@Serializable
data class TematicaGamificacion(
    var idTematica: String = "",
    var codigo: String = "",
    var monedas: Int = 0,
    var vidas: Int = 0,
    var posicionBarra: Int = 0
)
