package com.example.apphogares.backEnd.core.models.hogarMain

import kotlinx.serialization.Serializable

@Serializable
data class Dispositivo(
    val idDispositivo: Int = 0,
    var idIntegranteHogar: String = "",
    val imei: String = "",
    val cellPhoneNumber: String = "",
    val peso: String = "",
    val fechaActualizacion: String = "",
    val estado: Boolean = false,
    var tokenFCM : String = ""
)