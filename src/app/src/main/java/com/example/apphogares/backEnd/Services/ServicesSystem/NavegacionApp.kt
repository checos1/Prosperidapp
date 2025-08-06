package com.example.apphogares.backEnd.Services.ServicesSystem

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoTematicaEnum
import com.example.apphogares.backEnd.core.models.gamificacion.EstadosTematicas
import com.example.apphogares.backEnd.core.models.navegacion.Conexion
import com.example.apphogares.backEnd.core.models.navegacion.Integrante
import com.example.apphogares.backEnd.core.models.navegacion.Visita
import com.example.apphogares.backEnd.core.models.navegacion.Vista
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRuta
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRutaEnum
import com.example.apphogares.backEnd.core.models.hogarMain.HogarPreguntas
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject




class NavegacionApp @Inject constructor(private val api: AuthenticacionApi, private val repository: AppRepository,
                                        private val logError: LogError
) {

    private val audio = Audio()
    fun ObtenerNavegacionInicialApp() {
        var navegacion = if(AppHogaresAplication.navegacion.integrantes == null) AppHogaresAplication.Infohogar.hogar!!.navegacion else AppHogaresAplication.navegacion
        var idIntegrante = AppHogaresAplication.Infohogar.idIntegranteHogar
        var integrante = Integrante(idIntegrante, mutableListOf<Vista>(), mutableListOf<EstadosTematicas>())
        navegacion!!.integrantes.add(integrante)
        AppHogaresAplication.navegacion = navegacion!!
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun AdicionarMonedas(nombreVista: String, renderGamification: MutableState<Int>, cantidad:Int = 10)  {

        try {
            AgregarVisita(nombreVista, cantidad)
            var vista = AppHogaresAplication.navegacion.integrantes.first().vistas.first() {
                it.vista == nombreVista
            }
            vista.puntospos = vista.puntospos + cantidad
            renderGamification.value = AppHogaresAplication!!.navegacion!!.monedas + cantidad
            AppHogaresAplication.navegacion.monedas = AppHogaresAplication.navegacion.monedas + cantidad
            ActualizarDataNavegacion()
        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "AdicionarMonedas")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun QuitarMonedas(nombreVista: String, renderGamification: MutableState<Int>, cantidad:Int = 10)  {
        try {
            AgregarVisita(nombreVista, cantidad)
            var vista = AppHogaresAplication.navegacion.integrantes.first().vistas.first() {
                it.vista == nombreVista
            }

            vista.puntospos = if(vista.puntospos <= 10) 10 else vista.puntospos - cantidad
            renderGamification.value = if(AppHogaresAplication.navegacion.monedas <= 10) 10 else AppHogaresAplication!!.navegacion!!.monedas - cantidad
            AppHogaresAplication.navegacion.monedas = if(AppHogaresAplication.navegacion.monedas <= 10) 10 else AppHogaresAplication.navegacion.monedas - cantidad
            QuitarVida()
            ActualizarDataNavegacion()
        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "QuitarMonedas")
        }

    }

    suspend fun QuitarVida() {
        try {
            AppHogaresAplication.navegacion.vidas = AppHogaresAplication.navegacion.vidas - 1
            if (AppHogaresAplication.navegacion.vidas == 0) {
                ReiniciaVidas()
            }
        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "QuitarVida")
        }

    }

    suspend fun ReiniciaVidas() {
        try {
            AppHogaresAplication.navegacion.vidas = 5
            AppHogaresAplication.navegacion.monedas = 10

            ActualizarDataNavegacion()
        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "ReiniciaVidas")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun CrearVisita(): Visita {
        println("Navegacion CrearVisita")
        var conexion = Conexion(
            AppHogaresAplication.estadoDispositivo.isInternetConectivity,
            AppHogaresAplication.estadoDispositivo.typeInternetConectivity
        )
        val geoloclization = "${AppHogaresAplication.latitud}, ${AppHogaresAplication.longitud}"
        val visita = Visita("", conexion,LocalDateTime.now().toString(), geoloclization)
        return visita
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun AgregarVisita(nombreVista: String, cantidad: Int) {
        var visita = CrearVisita()
        var vistas = AppHogaresAplication.navegacion.integrantes!!.first().vistas
        if (vistas.size == 0){
            var vista = CrearVista(nombreVista, cantidad)
            vista.visitas.add(visita)
            vistas.add(CrearVista(nombreVista, cantidad))
        }else {
            var vistaTematica: Vista? = vistas.filterIndexed() { index, vista ->
                vista.vista == nombreVista
            }.firstOrNull()
            if(vistaTematica == null){
                vistaTematica = CrearVista(nombreVista, cantidad)
                vistaTematica.visitas.add(visita)
                vistas.add(vistaTematica)
            }
        }
        AppHogaresAplication.navegacion.integrantes.first().vistas = vistas
        println("Navegacion AgregarVisita")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend  fun AgregarVisitaHija (nombreVista: String, vistaHija: String) {
        try {
            println("AgregarVisitaHija : $nombreVista, $vistaHija")
            var visita = CrearVisita()
            println("AgregarVisitaHija CrearVisita: $visita")
            visita.vistaHija = vistaHija
            var vistas = AppHogaresAplication.navegacion.integrantes!!.first().vistas.filterIndexed() { index, it ->
                it.vista == nombreVista
            }
            println("AgregarVisitaHija vistas: $vistas")
            println("AgregarVisitaHija AppHogaresAplication.navegacion.integrantes: ${AppHogaresAplication.navegacion.integrantes}")
            if (vistas.isEmpty()) {
                var vista = CrearVista(nombreVista, 0)
                vista.visitas.add(visita)
                AppHogaresAplication.navegacion.integrantes.first().vistas.add(vista)
            } else {
                AppHogaresAplication.navegacion.integrantes.first().vistas.filterIndexed() { index, it ->
                    it.vista == nombreVista
                }.firstOrNull()?.let { vista ->
                    vista.visitas.add(visita)
                }
            }

            ActualizarDataNavegacion()
        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "AgregarVisitaHija")
        }

    }

    private fun CrearVista(vista: String, cantidad: Int): Vista {
        var vista = Vista(0, "0", 0, cantidad, mutableListOf<Visita>(), vista)
        vista.posicion = "0"
        vista.nps = 0
        return vista
    }

    suspend fun ActualizarDataNavegacion() {
        AppHogaresAplication.Infohogar.hogar!!.navegacion = AppHogaresAplication.navegacion

        var hogarInfoApp = HogarListaPreguntas(
                            AppHogaresAplication.Infohogar.hogar!!.idHogar,
                            AppHogaresAplication.Infohogar.idIntegranteHogar,
                            Gson().toJson(AppHogaresAplication.Infohogar)
                           )

        repository.insert(hogarInfoApp)
/*        try {
            val apiResponse = api.RegistrarInfohogarApp(hogarInfoApp)
            if (apiResponse == "OK") {
                println("ActualizarDataNavegacion: Ok")
            } else {
                println("Error al actualizar data navegacion api: $apiResponse")
            }
        } catch (e: Exception) {
            println("Error al actualizar data navegacion error: ${e.message}")
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "ActualizarDataNavegacion")
        }*/
    }

    suspend fun ActualizarEstadosTematica(estadosTematicas: EstadosTematicas){
        try {

            var estadoTematica = AppHogaresAplication.navegacion.integrantes.first().EstadoRutas.first() {
                it.codigo == estadosTematicas.codigo
            }
            if (estadoTematica != null) {
                estadoTematica.pasoUno = estadosTematicas.pasoUno
                estadoTematica.pasoDos = estadosTematicas.pasoDos
                estadoTematica.pasoTres = estadosTematicas.pasoTres
                if (estadoTematica.pasoTres) {
                    estadoTematica.estadoTematica = EstadoTematicaEnum.Realizado
                    HabilitarSiguienteTematica(estadoTematica.codigo)
                }else if (estadoTematica.pasoDos || estadoTematica.pasoUno  ) {
                    estadoTematica.estadoTematica = EstadoTematicaEnum.Activo
                }
                estadoTematica.estadoTematica = estadosTematicas.estadoTematica
            }else {
                AppHogaresAplication.navegacion.integrantes.first().EstadoRutas.add(estadosTematicas)
            }

            ActualizarDataNavegacion()

        } catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "ActualizarEstadosTematica")
        }
    }

    suspend fun ActualizarEstadosRutas(estadoRuta: EstadoRuta){
        try {

            var integranteRuta = AppHogaresAplication.navegacion.integrantes.first()
            val listaTematicasRutaNoRealizado = integranteRuta.EstadoRutas.filterIndexed() { index, it ->
                it.estadoTematica != EstadoTematicaEnum.Realizado && it.ruta == estadoRuta.ruta
            }
            println("ActualizarEstadosRutas listaTematicasRutaNoRealizado: ${Gson().toJson(listaTematicasRutaNoRealizado)}")
            if (listaTematicasRutaNoRealizado.isNotEmpty() && listaTematicasRutaNoRealizado.size > 0) {
                estadoRuta.estadoRuta = EstadoRutaEnum.Incompleto
            }else {
                estadoRuta.estadoRuta = EstadoRutaEnum.Realizado
            }

            val listaTematicasRuta = integranteRuta.EstadoRutas.filterIndexed() { index, it ->
                it.estadoTematica != EstadoTematicaEnum.Realizado && it.ruta == estadoRuta.ruta
            }

            val existeEstadoRuta = integranteRuta.EstadoNavegacionRuta.filterIndexed() { index, it ->
                it.ruta == estadoRuta.ruta
            }.firstOrNull()
            if (existeEstadoRuta == null) {
                AppHogaresAplication.navegacion.integrantes.first().EstadoNavegacionRuta.add(estadoRuta)
            }

            ActualizarDataNavegacion()

        } catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "ActualizarEstadosRutas")
        }
    }


    suspend fun HabilitarSiguienteTematica(codigoTematica:String){
        try {
            AppHogaresAplication.navegacion.integrantes.forEach(){
                it.EstadoRutas.indexOfFirst { it.codigo == codigoTematica }.let { index ->
                    if (index != -1 && index < it.EstadoRutas.size - 1) {
                        it.EstadoRutas[index + 1].estadoTematica = EstadoTematicaEnum.Activo
                        ActualizarDataNavegacion()
                    }
                }
            }
        }catch (e: Exception) {
            logError.RegistrarError(
                e.message.toString(),
                "NavegacionApp",
                "HabilitarSiguienteTematica"
            )
        }

    }

    suspend fun ActualizaActividaTematica(tematicaCode: String, indexActividadEnCurso: Int){
        try {
            println("ActualizaActividaTematica: $tematicaCode, $indexActividadEnCurso")
            AppHogaresAplication.navegacion.integrantes.forEach(){
                it.EstadoRutas.filterIndexed() { index, estadoTematica ->
                    estadoTematica.codigo == tematicaCode
                }.firstOrNull()?.let { estadoTematica ->
                    if (estadoTematica.estadoTematica == EstadoTematicaEnum.Bloqueado) {
                        return
                    }
                    estadoTematica.fechaActualizacion = LocalDateTime.now().toString()
                    estadoTematica.pasoUno = indexActividadEnCurso >= 0
                    estadoTematica.pasoDos = indexActividadEnCurso >= 1
                    estadoTematica.pasoTres = indexActividadEnCurso >= 2

                    if (estadoTematica.pasoTres) {
                        estadoTematica.estadoTematica = EstadoTematicaEnum.Realizado
                        HabilitarSiguienteTematica(estadoTematica.codigo)
                    } else if (estadoTematica.pasoDos || estadoTematica.pasoUno) {
                        estadoTematica.estadoTematica = EstadoTematicaEnum.Activo
                    }
                }
            }

            AppHogaresAplication.listaEstadosRutas.forEach() {
                 ActualizarEstadosRutas(it)
            }
            ActualizarDataNavegacion()
        }catch (e: Exception) {
            logError.RegistrarError(
                e.message.toString(),
                "NavegacionApp",
                "ActualizaActividaTematica"
            )
        }

    }

    fun ObtenerEstadoTematica(codigoTematica: String): EstadosTematicas? {
        AppHogaresAplication.navegacion.integrantes.forEach() {
            it.EstadoRutas.filterIndexed() { index, estadoTematica ->
                estadoTematica.codigo == codigoTematica
            }.firstOrNull()?.let { estadoTematica ->
                return estadoTematica
            }
        }
        return null
    }

    suspend fun EstadosInicialesTematicas(): MutableList<EstadosTematicas> {
        val objList = mutableListOf<EstadosTematicas>()

        try {
            AppHogaresAplication.contenidoCMS.Categorias!!.forEach() { categoria ->
                var indexRuta: Int = 0
                categoria.rutas.forEach() { ruta ->
                    var indexTematica: Int = 0
                    ruta.tematicas.forEach() {

                        val estadoTematica =
                            if (indexTematica == 0 && indexRuta == 0) {
                                EstadoTematicaEnum.Activo
                            }  else {
                                EstadoTematicaEnum.Bloqueado
                            }
                        objList.add(
                            EstadosTematicas(
                                ruta = ruta.codigo,
                                categoria = categoria.codigo,
                                codigo = it.codigo,
                                estadoTematica = estadoTematica,
                                pasoUno = false,
                                pasoDos = false,
                                pasoTres = false
                            )
                        )
                        indexTematica++
                    }
                    indexRuta++
                }
            }

            objList.forEach() {
                ActualizarEstadosTematica(it)
            }
        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "EstadosInicialesTematicas")
        }


        return objList
    }

    suspend fun EstadosInicialesRutas(): MutableList<EstadoRuta> {
        val objList = mutableListOf<EstadoRuta>()

        try {
            AppHogaresAplication.contenidoCMS.Categorias!!.forEach() { categoria ->
                categoria.rutas.forEach() { ruta ->
                    objList.add(
                        EstadoRuta(
                            ruta = ruta.codigo,
                            categoria = categoria.codigo,
                            estadoRuta = EstadoRutaEnum.Incompleto
                        )
                    )
                }
            }

            objList.forEach() {
                ActualizarEstadosRutas(it)
            }
        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "EstadosInicialesRutas")
        }

        println("EstadosInicialesRutas objList ->> ${Gson().toJson(objList)}")
        return objList
    }

    suspend fun EstadoRutaMedallas(){
        println("Estados RutaMedallas ->> ${Gson().toJson(AppHogaresAplication.navegacion.integrantes.first().EstadoNavegacionRuta)}")
        if(AppHogaresAplication.navegacion.integrantes.first().EstadoNavegacionRuta.size == 0){
            AppHogaresAplication.listaEstadosRutas = EstadosInicialesRutas()
        }else{
            AppHogaresAplication.listaEstadosRutas = AppHogaresAplication.navegacion.integrantes.first().EstadoNavegacionRuta
        }
        //Gamification.value = Gamificacion(AppHogaresAplication.listaEstadosRutas)
        println("rutaViewModel Init ->> ${Gson().toJson(AppHogaresAplication.listaEstadosRutas)}")
        AppHogaresAplication.listaEstadosRutas.forEach() {
            ActualizarEstadosRutas(it)
        }
        if (AppHogaresAplication.listaMedallasRuta.isEmpty()) {
            AppHogaresAplication.listaMedallasRuta = GestionImages().ObtenerMedallasRuta(AppHogaresAplication.contenidoCMS.Categorias)
        }
        AppHogaresAplication.listaMedallasRuta.forEach { medalla ->
            if (medalla.imagenActiva == null){
                medalla.imagenActiva = GestionImages().obtenerRutaFisicaImagenRuta(AppHogaresAplication.context, medalla.ruta, EstadoRutaEnum.Realizado)
            }
            if (medalla.imagenInactiva == null){
                medalla.imagenInactiva = GestionImages().obtenerRutaFisicaImagenRuta(AppHogaresAplication.context, medalla.ruta, EstadoRutaEnum.Incompleto)
            }
        }
    }

    suspend fun CargarContenidosRuta(){
        try {
            AppHogaresAplication.listaTematicaGamificacion =
                GamificacionTematica.ObtenerListaTematicasGamificacion(AppHogaresAplication.contenidoCMS.Categorias)
            val filteredNotifications = AppHogaresAplication.Infohogar.hogar?.notificaciones
                ?.filter { it.estadoNotificacion != "Eliminada" }
                ?.toMutableList()
                ?: mutableListOf()
            AppHogaresAplication.alertas = filteredNotifications

            if(AppHogaresAplication.navegacion.integrantes.first().EstadoRutas.size == 0){
                AppHogaresAplication.listaEstadosTematicas = EstadosInicialesTematicas()
            }
        }catch (e: Exception) {
            println("CargarContenidosRuta Error: ${e.message}")
            logError.RegistrarError(e.message.toString(), "NavegacionApp", "CargarContenidosRuta")
        }


        //_rutaMedallaSeleccionada.value = AppHogaresAplication.listaMedallasRuta.first().ruta
    }
}