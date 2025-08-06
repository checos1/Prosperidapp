package com.example.apphogares.backEnd.core.models.hogarMain

import java.util.Date

enum class EstadoEncuesta(val estado: String) {
    ABIERTA("ABIERTA"),
    FINALIZADA("FINALIZADA"),
    RESPONDIDA("RESPONDIDA")
}
data class Encuesta(
    val id: String = "",
    val nombre: String = "",
    val pregunta: String = "",
    var respuestas: String = "",
    val fechaEnvio: String = "",
    val fechaCierre: String = "",
    val tipoPregunta: String = "",
    val topico: String = "",
    val integrantes: String = "",
    var estado: EstadoEncuesta = EstadoEncuesta.ABIERTA,
    val categoria: String = "",
    var orden: Int = 0,
    var respuestaSeleccionada: String = "",
    var fechaRespuesta: String = ""
)

