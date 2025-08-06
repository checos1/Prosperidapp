package com.example.apphogares.backEnd.core.models.hogarMain

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class LogErrores(
    val idLogErrores: Long = 0,
    val idHogar: String,
    val fuente: String = "APP",
    val error: String = "",
    val fecha: Date = Date(),
    val clase: String = "",
    val metodo: String = ""
)
