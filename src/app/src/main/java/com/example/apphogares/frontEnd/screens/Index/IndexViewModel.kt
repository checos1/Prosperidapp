package com.example.apphogares.frontEnd.screens.Index

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.backEnd.Services.ServicesSystem.Downloads
import com.example.apphogares.backEnd.Services.ServicesSystem.GamificacionTematica
import com.example.apphogares.backEnd.Services.ServicesSystem.GestionImages
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.servicesAPI.NotificacionAPI
import com.example.apphogares.backEnd.Services.servicesAPI.PQRDocumentAPI
import com.example.apphogares.backEnd.Services.servicesAPI.PublicacionAPI
import com.example.apphogares.backEnd.core.models.gamificacion.Gamificacion
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.example.apphogares.frontEnd.RoutesNav
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class indexState {
    initScreen,
    loading,
    withoutConectivity,
    msgError,
    proximoPaso,
    nextScreen
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class IndexViewModel @Inject constructor(private val logError: LogError,
                                         private val network: network,
                                         private val navegacionApp: NavegacionApp,
                                         private val notificacionAPI: NotificacionAPI,
                                         private val apiPQR: PQRDocumentAPI,
                                         private val apiPublicaciones: PublicacionAPI
): ViewModel() {

    val nextScreen: MutableState<String> = mutableStateOf("")
    val msgError: MutableState<String> = mutableStateOf("")
    val endShowMayo: MutableState<Boolean> = mutableStateOf(false)

    val stateIndex: MutableState<indexState> = mutableStateOf(indexState.initScreen)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val existeContenidosRuta: MutableState<Boolean> = mutableStateOf(false)

    init {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                AppHogaresAplication.estadoDispositivo.isInternetConectivity = network.isInternetAvailable(AppHogaresAplication.context)
                network.getDataLocal()
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(AppHogaresAplication.context)
                network.getLocation(AppHogaresAplication.context, fusedLocationClient)
                if (AppHogaresAplication.estadoDispositivo.isInternetConectivity) {
                    stateIndex.value = indexState.loading
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        AppHogaresAplication.estadoDispositivo.typeInternetConectivity = network.getTypeInternetConnectivity(
                            AppHogaresAplication.context) ?: ""
                    }
                    network.validarEstadoDispositivo(AppHogaresAplication.context)
                    println("IndexViewModel Init: ${AppHogaresAplication.Infohogar!!.hogar!!.idHogar}")
                    if(AppHogaresAplication.Infohogar!!.hogar!!.idHogar != "" ){

                        GetNotifications()
                        try {
                            AppHogaresAplication.listaMedallasRuta = GestionImages().ObtenerMedallasRuta(AppHogaresAplication.contenidoCMS.Categorias)
                            println("IndexViewModel Init listaMedallasRuta: ${Gson().toJson(AppHogaresAplication.listaMedallasRuta)}")
                            Downloads(AppHogaresAplication.context).downloadImagesMedalla()
                        }catch (e: Exception) {
                            val msgError = "Error downloadImagesMedalla: ${e.message}"
                            logError.RegistrarError(msgError, "IndexViewModel", "init")
                        }
                        try {
                            AppHogaresAplication.listadoAudiosTematica = Audio().ObtenerAudiosTematica(AppHogaresAplication.contenidoCMS.Categorias)
                            println("IndexViewModel Init listadoAudiosTematica: ${Gson().toJson(AppHogaresAplication.listadoAudiosTematica)}")
                            Downloads(AppHogaresAplication.context).downloadAudios()
                        }catch (e: Exception) {
                            val msgError = "Error downloadAudios: ${e.message}"
                            logError.RegistrarError(msgError, "IndexViewModel", "init")
                        }
                        var existeEncuestaAbierta = false
                        println("encuestas: ${Gson().toJson(AppHogaresAplication.Infohogar.hogar!!.encuestas)}")
                        if (AppHogaresAplication.Infohogar.hogar!!.encuestas.isNotEmpty()){
                            AppHogaresAplication.Infohogar.hogar!!.encuestas.forEach {
                                if (it.estado == EstadoEncuesta.ABIERTA && it.categoria == ""){
                                    existeEncuestaAbierta = true
                                    return@forEach
                                }
                            }
                        }
                        AppHogaresAplication.listaPQRs = apiPQR.ObtenerPQRs(AppHogaresAplication.Infohogar.idHogar)
                        AppHogaresAplication.listaPublicaciones = apiPublicaciones.ObtenerPublicaciones()
                        AppHogaresAplication.Infohogar.hogar!!.pqrs = AppHogaresAplication.listaPQRs
                        AppHogaresAplication.Infohogar.hogar!!.publicaciones = AppHogaresAplication.listaPublicaciones
                        nextScreen.value = if (existeEncuestaAbierta) RoutesNav.encuestaScreen.route else RoutesNav.rutaScreen.route

                    }else{
                        nextScreen.value = RoutesNav.loginScreen.route
                    }
                } else {

                    if (AppHogaresAplication.estadoDispositivo.isLocalHogar) {
                        network.getInfoLocalHogar()
                        network.ObtenerContenidoCMS()
                        println("IndexViewModel: ${AppHogaresAplication.contenidoCMS}")
                        AppHogaresAplication.listadoAudiosTematica =
                            Audio().ObtenerAudiosTematica(AppHogaresAplication.contenidoCMS.Categorias)
                        AppHogaresAplication.listadoTarjetasTematica =
                            GestionImages().ObtenerTarjetasTematica(AppHogaresAplication.contenidoCMS.Categorias)
                        AppHogaresAplication.listaTematicaGamificacion = GamificacionTematica.ObtenerListaTematicasGamificacion(
                            AppHogaresAplication.contenidoCMS.Categorias)
                        AppHogaresAplication.navegacion = AppHogaresAplication.Infohogar!!.hogar!!.navegacion!!
                        if (AppHogaresAplication.Infohogar.hogar!!.encuestas.isNotEmpty()){
                            AppHogaresAplication.Infohogar.hogar!!.encuestas.forEach {
                                if (it.estado == EstadoEncuesta.ABIERTA){
                                    nextScreen.value = RoutesNav.encuestaScreen.route
                                }
                            }
                        }

                        nextScreen.value = RoutesNav.rutaScreen.route
                    } else {
                        stateIndex.value = indexState.withoutConectivity
                    }
                }
                validarestado()
            }
        }catch (e: Exception) {
            println("Error IndexViewModel: ${e.message}")
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "IndexViewModel", "init")
            }
            stateIndex.value = indexState.msgError
            msgError.value = e.message.orEmpty()
        }
    }

    private suspend fun GetNotifications() {
        val notificacionesAPI =
            notificacionAPI.GetNotificationsHogar(AppHogaresAplication.Infohogar!!.hogar!!.idHogar)

        var notificionesLocal = AppHogaresAplication.Infohogar.hogar!!.notificaciones
        if (notificionesLocal.isNotEmpty()) {
            notificacionesAPI.forEach { notificacionAPI ->
                val existeNotificacion =
                    AppHogaresAplication.Infohogar.hogar?.notificaciones!!.filterIndexed { _, notification ->
                        notificacionAPI.id == notification.id
                    }.isNotEmpty()
                if (!existeNotificacion) {
                    notificionesLocal.add(notificacionAPI)
                }
            }
            AppHogaresAplication.Infohogar.hogar!!.notificaciones = notificionesLocal
        } else {
            AppHogaresAplication.Infohogar.hogar!!.notificaciones = notificacionesAPI
        }
    }

    fun validarestado(){
        println("validarestado: ${endShowMayo.value} ${nextScreen.value}")
        if (nextScreen.value != "")
            stateIndex.value = indexState.proximoPaso
    }

    fun validarContenidosRuta(){
        try {
            existeContenidosRuta.value = AppHogaresAplication.contenidoCMS.Categorias?.size!! > 0
            iniGamiEstadoTematica()
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "indexnViewModel", "validarContenidosRuta")
            }
            msgError.value = e.message.orEmpty()
        }
    }

    fun iniGamiEstadoTematica() {

        try {
            if (AppHogaresAplication.navegacion.integrantes.size > 0 && !AppHogaresAplication.navegacion.integrantes[0]!!.EstadoRutas.isNullOrEmpty()) {
                return
            }

            viewModelScope.launch {
                if (AppHogaresAplication.listaEstadosTematicas.size > 0) {
                    AppHogaresAplication.navegacion.integrantes[0]!!.EstadoRutas =
                        AppHogaresAplication.listaEstadosTematicas
                    AppHogaresAplication.Infohogar.hogar!!.navegacion = AppHogaresAplication.navegacion

                    AppHogaresAplication.navegacion.integrantes[0]!!.EstadoNavegacionRuta = AppHogaresAplication.listaEstadosRutas

                    navegacionApp.ActualizarDataNavegacion()
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(
                    e.message.orEmpty(),
                    "LoginViewModel",
                    "iniGamiEstadoTematica"
                )
            }
        }

    }
}