package com.example.apphogares.frontEnd.screens.autenticacion

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.backEnd.Services.ServicesSystem.GamificacionTematica
import com.example.apphogares.backEnd.Services.ServicesSystem.GestionImages
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import com.example.apphogares.backEnd.core.models.gamificacion.Gamificacion
import com.example.apphogares.backEnd.core.models.hogarMain.HogarPreguntas
import com.example.apphogares.backEnd.core.models.hogarMain.Preguntas
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.servicesAPI.PQRDocumentAPI
import com.example.apphogares.backEnd.Services.servicesAPI.PublicacionAPI
import com.example.apphogares.backEnd.core.models.actividadesRuta.TematicaGamificacion
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val api: AuthenticacionApi,
                                         private val repository: AppRepository,
                                         private val network: network,
                                         public val navegacionApp: NavegacionApp,
                                         private val apiPQR: PQRDocumentAPI,
                                         private val apiPublicaciones: PublicacionAPI,
                                         private val logError: LogError,
                                         private val utilities: utilities
    ): ViewModel(){


    //Objeto Gamificación
    val Gamification: MutableState<Gamificacion> = mutableStateOf(Gamificacion())

    //Mutable para administrar en que momento se muestra cada dialogo de la conversación en una tematica
    //var objIteration: MutableState<List<Float>> = mutableStateOf(listOf())

    private val error: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val isLoggedIn: MutableState<Boolean> = mutableStateOf(false)
    val isNotHogar: MutableState<Boolean> = mutableStateOf(false)

    //Objeto de tipo State para almacenar los datos Hogar -> Preguntas, por ahora se carga desde una variable Json que simula la respuesta
    private val dataHogar:MutableState<HogarPreguntas> = mutableStateOf(HogarPreguntas())
    val posQuestion: MutableState<Int> = mutableStateOf(0)

    //Valores del formulario de login
    val typeDocument: MutableState<String> = mutableStateOf("")
    val document: MutableState<String> = mutableStateOf("")
    // Estado del checkbox, guardado entre recomposiciones
    val aceptado : MutableState<Boolean> = mutableStateOf(true)

    //val activityCompleted : MutableState<Boolean> = mutableStateOf(false)

    val msgError: MutableState<String> = mutableStateOf("")

    val existeContenidosRuta: MutableState<Boolean> = mutableStateOf(false)

    init {
        viewModelScope.launch( Dispatchers.IO) {
            try {
                val hogarPreguntaDTO = repository.getHogarPreguntas().first()
               AppHogaresAplication.Infohogar = Gson().fromJson(hogarPreguntaDTO.jsonHogar, HogarPreguntas::class.java)
                if (AppHogaresAplication.Infohogar.idHogar != "") {
                    AppHogaresAplication.listaPQRs = apiPQR.ObtenerPQRs(AppHogaresAplication.Infohogar.idHogar)
                    AppHogaresAplication.listaPublicaciones = apiPublicaciones.ObtenerPublicaciones()
                    AppHogaresAplication.Infohogar.hogar!!.pqrs = AppHogaresAplication.listaPQRs
                    AppHogaresAplication.Infohogar.hogar!!.publicaciones = AppHogaresAplication.listaPublicaciones
                }

            } catch (e: Exception) {
                //logError.RegistrarError(e.message.toString(), "LoginViewModel", "init")
            }
        }
    }

    //Metodo para que los ViewScreen puedan acceder al objeto Hogar->Preguntas
    fun getQuestions(): List<Preguntas> {

        if(dataHogar.value.listaPreguntas.isNullOrEmpty() )
        {
            setDataHogar()
        }
        return dataHogar.value.listaPreguntas.orEmpty()
    }

    //Metodo para que los ViewScreen puedan acceder al objeto Hogar
    fun setDataHogar() {
        try {
            if (dataHogar.value.idHogar == ""){
                dataHogar.value = AppHogaresAplication.Infohogar
            }
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "LoginViewModel", "setDataHogar")
            }
            msgError.value = e.message.orEmpty()
        }
    }

    //Metodo que setea datos en el objeto Hogar -> Pregunta, recibe la posición y el valor a setear en la respuesta seleccionada
    fun setQuestionsSeleccion(posQuestion:Int, seleccion:String){ dataHogar.component1().listaPreguntas.orEmpty().get(posQuestion).seleccion = seleccion }

    //Funcion que conecta con el servicio de datos
    fun connectDataLogin() {
        try {
            loading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                val datosHogarPregunta = api.getPreguntasValidacion(typeDocument.value, document.value)
                if (datosHogarPregunta.idHogar !=  ""){
                    repository.insert(HogarListaPreguntas(datosHogarPregunta.idHogar, "", Gson().toJson(datosHogarPregunta)))
                    isLoggedIn.value = true
                }else{
                    isNotHogar.value = true
                }
                loading.value = false
            }
        } catch (e: Exception) {
                viewModelScope.launch {
                    logError.RegistrarError(e.message.orEmpty(), "LoginViewModel", "connectDataLogin")
            }
            error.value = true
            loading.value = false
            msgError.value = e.message.orEmpty()
        }
    }

    fun sendValidation(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                loading.value = true
                val datosHogarPreguntaRespuesta = api.verificarPreguntas(dataHogar.value)
                if (datosHogarPreguntaRespuesta.idHogar !=  "" && datosHogarPreguntaRespuesta.flag == true){
                    AppHogaresAplication.Infohogar = datosHogarPreguntaRespuesta
                    AppHogaresAplication.integranteSeleccionado = datosHogarPreguntaRespuesta.idIntegranteHogar
                    AppHogaresAplication.Infohogar.hogar?.idIntegranteSeleccionado  = AppHogaresAplication.integranteSeleccionado
                    AppHogaresAplication.Infohogar.hogar?.dispositivo?.tokenFCM = AppHogaresAplication.tokenFCM

                    network.getInfoLocalHogar()

                    repository.insert(HogarListaPreguntas(datosHogarPreguntaRespuesta.idHogar, AppHogaresAplication.integranteSeleccionado, Gson().toJson(
                    AppHogaresAplication.Infohogar)))

                    AppHogaresAplication.listaEstadosTematicas = navegacionApp.EstadosInicialesTematicas()
                    println("sendValidation listaEstadosTematicas: ${AppHogaresAplication.listaEstadosTematicas}")
                    AppHogaresAplication.listaEstadosRutas = navegacionApp.EstadosInicialesRutas()
                    println("sendValidation listaEstadosRutas: ${AppHogaresAplication.listaEstadosRutas}")
                    iniGamiEstadoTematica()

                    AppHogaresAplication.Infohogar.hogar?.topicos.let {
                        utilities.subscribeToTopic(AppHogaresAplication.Infohogar.hogar?.topicos!!)
                    }
                    AppHogaresAplication.listaMedallasRuta =
                        GestionImages().ObtenerMedallasRuta(AppHogaresAplication.contenidoCMS.Categorias)

                    isLoggedIn.value = true
                }else {
                    dataHogar.value = datosHogarPreguntaRespuesta
                    if (dataHogar.value.listaPreguntas.orEmpty().size < 3) posQuestion.value = -1 else posQuestion.value = 0
                }

                loading.value = false
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "LoginViewModel", "sendValidation")
            }
            error.value = true
            loading.value = AppHogaresAplication.contenidoCMS.Categorias?.size!! == 0
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