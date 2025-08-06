package com.example.apphogares.backEnd.core.models.hogarMain

import kotlinx.serialization.Serializable


data class Oferta(
    var descripcionOferta: String = "",
    var descripcionRequisitos: String = "",
    var diasAtencion: String = "",
    var direccion: String = "",
    var fechaCierreCicloOperativo: String = "",
    var fechaFin: String = "",
    var fechaInicio: String = "",
    var grupoTerritorial: String = "",
    var horarioAtencion: String = "",
    var idEncuesta: Int = 0,
    var idHogar: String = "",
    var idLogro: Int = 0,
    var idMunicipio: String = "",
    var idOferta: Int? = null,
    var nombreLogro: String = "",
    var nombreMunicipio: String = "",
    var nombreOferente: String = "",
    var nombreOferta: String = "",
    var perfil: String = "",
    var tipoEncuesta: Int = 0,
    var nombreIntegrante: String = "",
    var temas: String = "",
    var interesado: Boolean = false,
    var fechaActualizacion: String = ""

)