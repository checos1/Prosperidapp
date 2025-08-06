package com.example.apphogares.backEnd.core.models.PQRs

object ProgramasPQR {
    var programas = listOf(
        Programa("1", "Acompañamiento (Unidos)"),
        Programa("2", "Auditorias Visibles")
    )
}

data class Programa(
    val idProgarma: String = "",
    val Progarma: String = ""
)