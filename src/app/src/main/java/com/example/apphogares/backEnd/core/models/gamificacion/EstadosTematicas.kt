package com.example.apphogares.backEnd.core.models.gamificacion

import java.time.LocalDate

data class EstadosTematicas(
    var codigo: String = "",
    var categoria: String = "",
    var ruta: String = "",
    var pasoUno: Boolean = false,
    var pasoDos: Boolean = false,
    var pasoTres: Boolean = false,
    var estadoTematica: EstadoTematicaEnum = EstadoTematicaEnum.Bloqueado,
    var fechaActualizacion: String = LocalDate.now().toString()
)

