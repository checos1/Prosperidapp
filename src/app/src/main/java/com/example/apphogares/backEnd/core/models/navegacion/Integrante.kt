package com.example.apphogares.backEnd.core.models.navegacion

import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRuta
import com.example.apphogares.backEnd.core.models.gamificacion.EstadosTematicas

data class Integrante(
    val idIntegrante: String = "",
    var vistas: MutableList<Vista>,
    var EstadoRutas: MutableList<EstadosTematicas> = mutableListOf(),
    var notificaciones: MutableList<Notificacion> = mutableListOf(),
    var EstadoNavegacionRuta: MutableList<EstadoRuta> = mutableListOf(),
    var EstadoCategorias: MutableList<Categoria> = mutableListOf()
)