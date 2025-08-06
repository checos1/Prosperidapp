package com.example.apphogares.backEnd.core.models.hogarMain

import kotlinx.serialization.Serializable

@Serializable
data class Integrante(
    var apellidos: String = "",
    var fechaExpedicion: String = "",
    var fechaNacimiento: String = "",
    var idIntegranteHogar: String = "",
    var nombre: String = "",
    var numeroDocumento: String = "",
    var sexo: String = "",
    var tipoDocumento: String = "",
    var programas: String = "",
    var nombreProgramas: String = "",
    var esJefeHogar:  String = "",

)
