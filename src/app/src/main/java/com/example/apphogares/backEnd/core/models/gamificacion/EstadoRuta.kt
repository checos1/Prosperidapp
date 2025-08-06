package com.example.apphogares.backEnd.core.models.gamificacion

import com.example.apphogares.backEnd.core.enums.RutaImagenEstado

data class  EstadoRuta (
    var ruta: String = "",
    var categoria: String = "",
    var estadoRuta: EstadoRutaEnum = EstadoRutaEnum.Incompleto,
    var imagenEstado: RutaImagenEstado = RutaImagenEstado.CANDADO
)
