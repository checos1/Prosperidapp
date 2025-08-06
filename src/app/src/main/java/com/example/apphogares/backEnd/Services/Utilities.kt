package com.example.apphogares.backEnd.Services


import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.enums.TypesDni
import javax.inject.Inject
import com.example.apphogares.frontEnd.RoutesNav
import com.google.firebase.messaging.FirebaseMessaging
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class utilities @Inject constructor() {

    fun GetTypesDni(): MutableList<TypesDni> {
        val listTypesDni: MutableList<TypesDni> = mutableListOf()
        listTypesDni.add(TypesDni("CC", "Cédula de ciudadanía"))
        listTypesDni.add(TypesDni("TI", "Tarjeta de Identidad"))
        listTypesDni.add(TypesDni("CE", "Cédula de extranjeria"))
        listTypesDni.add(TypesDni("DNI", "Documento nacional de identidad"))
        listTypesDni.add(TypesDni("PAS", "Pasaporte"))
        listTypesDni.add(TypesDni("SAL", "Salvoconducto para refugiado"))
        listTypesDni.add(TypesDni("PEP", "Permiso especial de permanencia"))
        listTypesDni.add(TypesDni("RC", "Registro Civil"))
        return listTypesDni
    }

    fun setTipoDocumento(value: String): String {
        val dni = GetTypesDni().firstOrNull { it.description == value }
        if (dni != null) {
            return dni.id
        } else {
            return "Tipo de documento no válido"
        }
        //return GetTypesDni().first() { it.description == value }.id
    }

    fun cambiarActividad(
        actividad: String,
        funNav: (String) -> Unit,
        catCode: String,
        rutaCode: String,
        tematicaCode: String
    ) {

        when(actividad){
            "ComponentHerramientasRelacion" -> {
                funNav("${RoutesNav.emparejarScreen.route}/$catCode/$rutaCode/$tematicaCode")
            }
            "ComponentHerramientasComplete" -> {
                funNav("${RoutesNav.completarScreen.route}/$catCode/$rutaCode/$tematicaCode")
            }
            "ComponentHerramientasSopaDeLetras" -> {
                funNav("${RoutesNav.memoriaScreen.route}/$catCode/$rutaCode/$tematicaCode")
            }
            "ComponentHerramientasMemoria" -> {
                funNav("${RoutesNav.memoriaScreen.route}/$catCode/$rutaCode/$tematicaCode")
            }
        }
    }

    suspend fun subscribeToTopic(topicos: String) {
        try {
            var listaTopicos = topicos.split(",")
            for (topic in listaTopicos) {
                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            return@addOnCompleteListener
                        }
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        AppHogaresAplication.tokenFCM.let {
            FirebaseMessaging.getInstance().subscribeToTopic(it)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@addOnCompleteListener
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ConvertToDate(fecha: String): LocalDate {
      //  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var dateFormat = LocalDate.parse(fecha, formatter)
        println("utilities ConvertToDate: $dateFormat")
        return dateFormat
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun FormatearFecha(fechaISO: String): String {
        return try {
            val dateTime = java.time.OffsetDateTime.parse(fechaISO)
            dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        } catch (e: Exception) {
            "Fecha inválida"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun FormatearHora(fechaISO: String): String {
        return try {
            val dateTime = java.time.OffsetDateTime.parse(fechaISO)
            dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            "Hora inválida"
        }
    }

    fun formatFecha(fechaIso: String): String {
        val fecha = LocalDate.parse(fechaIso)
        val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM yyyy", Locale("es", "ES"))
        return fecha.format(formatter)
    }

    fun obtenerVideoIdDeUrl(url: String): String? {
        return Uri.parse(url).getQueryParameter("v")
    }
}

