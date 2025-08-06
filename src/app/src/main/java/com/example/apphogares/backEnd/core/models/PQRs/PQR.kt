package com.example.apphogares.backEnd.core.models.PQRs

import com.example.apphogares.AppHogaresAplication
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class PQR(
    var idhogar: String = AppHogaresAplication.Infohogar.idHogar,
    var numeroDocumento:String = "",
    var programa: String = "",
    var idPeticionAPP: String = UUID.randomUUID().toString(),
    var radicado: String = "",
    var tipoPeticion: String = "PETICIÓN",
    var estado: String = "Por Asignar",
    var fechaRadicacion: String = LocalDate.now().toString(),
    var fechaRespuesta: String = "",
    var cuerpoPeticion: String = "",
    var respuesta: String = "",
    var fotoPeticion: String = "",
    var archivoPeticion: String = "",
    var archivoRespuesta: String = "",
    var asunto: String = "idHogar: ${idhogar} - idPeticionAPP: ${idPeticionAPP}",
    var informacionHogar: String = ""
)

