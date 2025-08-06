package com.example.apphogares.backEnd.core.models.hogarMain

import kotlinx.serialization.Serializable

@Serializable
data class Contacto(
    val correoElectronico: String = "",
    val telefono: String = "",
    val ubicacionHogar: String = "",
    var numerosContacto: List<String> = emptyList(),
    var codigo_municipio: String = "",
    var departamento: String = "",
    var municipio: String = ""
)
