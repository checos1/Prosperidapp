package com.example.apphogares.backEnd.core.models.hogarMain


data class Notificacion(
    val asunto: String = "",
    val cantidad_envio: Int = 0,
    val categoria: String = "",
    val cicloVital: String = "",
    val codigoCicloVital: String = "",
    val codigoMunicipio: String = "",
    val codigoPerfilHogar: String = "",
    val enviado: Boolean = false,
    val fechaFin: String = "",
    val fecha_envio: Any  = "",
    val id: String = "",
    val integrantes: String = "",
    val mensaje: String = "",
    val municipio: String = "",
    val perfilHogar: String = "",
    val topico: String = "",
    val paraConfirmar: Boolean = false,
    val abierta: Boolean = false,
    val fechaAbierta: String = "",
    val confirmada: Boolean = false,
    val linkExterno: String = "",
    val botonAlerta: String = "",
    var fechaConfirmacionBoton: String = "",
    var estadoNotificacion: String = "Abierta",
)