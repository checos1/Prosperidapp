package com.example.apphogares.backEnd.core.models.PQR

import com.example.apphogares.frontEnd.theme.*
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class PQRDocument(
    var idPeticionAPP: String = UUID.randomUUID().toString(),
    var radicado: String = "",
    var idHogar: String = "",
    val idUsuario: String = "anonimo",
    val idTipoDocumento: Int = 270,
    val asunto: String = "Radicación PQR APP",
    val canal: String = "CW",
    val blqTipoUsuario: BlqTipoUsuario = BlqTipoUsuario(),
    val blqTipoPersona: BlqTipoPersona = BlqTipoPersona(),
    val blqInfoAdicional: BlqInfoAdicional = BlqInfoAdicional(),
    var blqPersonaNatural: BlqPersonaNatural = BlqPersonaNatural(),
    var blqDatosContactoSolicitante: BlqDatosContactoSolicitante = BlqDatosContactoSolicitante(),
    var blqInformacionPeticion: BlqInformacionPeticion = BlqInformacionPeticion(),
    val blqAceptacionCondiciones: BlqAceptacionCondiciones = BlqAceptacionCondiciones(),
    val blqTerminosCondiciones: BlqTerminosCondiciones = BlqTerminosCondiciones(),
    var anexos: MutableList<Anexo>? = mutableListOf(),
    var estado: String = "Por Asignar",
    var response: Response = Response(),
    val consultarPQRResponse: ConsultarPQRResponse = ConsultarPQRResponse()
)

data class BlqTipoUsuario(
    val tipoUsuario: String = "NO"
)

data class BlqTipoPersona(
    val idTipoPersona: Int = 1,
    val tipoPersona: String = "Persona Natural",
    val medioRespuesta: String = "Correo electrónico"
)

data class BlqInfoAdicional(
    val idAtencionPreferencial: Int = 1,
    val atencionPreferencial: String = "No requiere",
    val idInformacionPoblacional: Int = 10,
    val informacionPoblacional: String = "Ninguno de los anteriores",
    val idOcupacion: Int = 9,
    val ocupacion: String = "No Definido",
    val idNivelEducativo: Int = 9,
    val nivelEducativo: String = "No determinado",
    var idGeneroInf: String = "",
    var generoInf: String = "",
    var fechaNacimientoInf: String = ""
)

data class BlqPersonaNatural(
    var identificacion: String = "",
    var idTipoIdentificacion: String = "",
    var tipoIdentificacion: String = "",
    var primerNombre: String = "",
    var segundoNombre: String = "",
    var primerApellido: String = "",
    var segundoApellido: String = "",
    var idGenero: Int = 1,
    var genero: String = "",
    var fechaNacimiento: String = ""
)

data class BlqDatosContactoSolicitante(
    val idPais: Int = 6138,
    val pais: String = "Colombia",
    var idDepartamento: Int = 7501,
    var departamento: String = "Bogotá D.C.",
    var idCiudad: Int = 6526,
    var ciudad: String = "Bogotá D.C.",
    var direccion: String = "",
    var barrio: String = "",
    var telefono: String = "",
    var celular: String = "",
    var correoElectronico: String = ""
)

data class BlqInformacionPeticion(
    var idAsunto: Int = 0,
    var asunto: String = "",
    var descripcion: String = "",
    val idCanal: Int = 33,
    var fotoPeticion: String = "",
    var archivoPeticion: String = "",
    var urlArhivoPeticion: String = "",
    var urlFotoPeticion: String = ""
)

data class BlqAceptacionCondiciones(
    val avisoAceptacionCondiciones: String = "En cumplimiento del Decreto 103 de 2015, que reglamenta parcialmente la Ley 1712 de 2014, y de conformidad con la Resolución interna No. 2041 de 2019 en Prosperidad Social la reproducción de la información pública para solicitudes de entidades o particulares NO TIENE NIGÚN COSTO."
)

data class BlqTerminosCondiciones(
    val terminosCondiciones: String = "SI"
)

data class Anexo(
    var nombreAnexo: String = "",
    var contenido: String = ""
)

data class StatusColor(
    val status: String,
    val statusColor: androidx.compose.ui.graphics.Color,
)

data class TipoPQR(
    val idTipo: Int = 0,
    val tipo: String,
    val tipoColor: androidx.compose.ui.graphics.Color,
    val orden: Int = 0
)

object PQRs {
    val tipos = listOf(
        TipoPQR(1, "Denuncia", BlueSky, 3),
        TipoPQR(2, "Reclamo", VerdeCorrecto, 4),
        TipoPQR(3, "Sugerencia", RedLight, 6),
        TipoPQR(4, "Petición", Blue, 1),
//        TipoPQR(5, "Solicitud de información", Red, 7),
        TipoPQR(7, "Queja", BackgroundBottomInTo, 2),
 //       TipoPQR(6, "Solicitud de información pública", BackgroundBottomInTo, 8),
        TipoPQR(8, "Felicitación", BackGroundTopLoginPlus, 5)
    )

    val listStatusColor = listOf(
        StatusColor("POR ASIGNAR", BlueSky),
        StatusColor("EN GESTIÓN", blueOval),
        StatusColor("RADICADO", BackgroundBottomInTo),
        StatusColor("CERRADA", VerdeCorrectoTwo),
        StatusColor("POR PROCESAR", circlePerson1),
        StatusColor("ASIGNADO", circlePerson1),
    )
}

@Serializable
data class Response(
    val idDocumento: Long = 0,
    val radicado: String = "",
    val fechaRadicacion: String = LocalDate.now().toString(),
    val idOperacion: String = "",
    val fechaOperacion: String = LocalDate.now().toString(),
    val descripcion: String? = null,
    val estadoOperacion: String = "",
    val errores: String? = null
)

@Serializable
data class ConsultarPQRResponse(
    val idOperacion: String = "",
    val fechaOperacion: String = LocalDate.now().toString(),
    val estadoOperacion: String = "",
    val errores: String? = "",
    val resultado: Resultado = Resultado()
)

@Serializable
data class Resultado(
    val radicado: String = "",
    val fecha: String = LocalDate.now().toString(),
    val estado: String = "",
    val respuesta: String = "",
    val archivos: List<String> = emptyList()
)
