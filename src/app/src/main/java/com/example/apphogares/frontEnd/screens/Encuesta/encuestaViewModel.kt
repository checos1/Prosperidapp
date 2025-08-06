package com.example.apphogares.frontEnd.screens.Encuesta

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.PreferencesManager
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.example.apphogares.frontEnd.RoutesNav
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class encuestaViewModel @Inject constructor(
                                            private val logError: LogError,
                                            private val preferencesManager: PreferencesManager,
                                            private val network: network): ViewModel()  {

    val encuesta: MutableState<Encuesta> = mutableStateOf(Encuesta())
    val encuestaFinalizada: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val encuestasCategoriaAbiertas: MutableState<List<Encuesta>> = mutableStateOf(listOf())
    val encuestaTitulo: MutableState<String> = mutableStateOf("")


    init {
        viewModelScope.launch {
            println("encuestaViewModel Init")
            try {

                encuestasCategoriaAbiertas.value = AppHogaresAplication.encuestas.filter { it.estado == EstadoEncuesta.ABIERTA && it.categoria != "" }
                    .sortedBy{ it.orden }

                encuesta.value = encuestasCategoriaAbiertas.value.firstOrNull { survey ->
                   survey.estado == EstadoEncuesta.ABIERTA
                }!!

                if(encuestasCategoriaAbiertas.value.size > 0) {
                    encuestaTitulo.value = encuestasCategoriaAbiertas.value[0].nombre
                }
                println("encuestaViewModel Init fin")
            } catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "encuestaViewModel", "init")
            }
        }
    }

    fun responderEncuesta(respuesta: String) {
        try {
            loading.value = true

            viewModelScope.launch {
                encuesta.value.respuestaSeleccionada = respuesta
                encuesta.value.estado = EstadoEncuesta.RESPONDIDA
                AppHogaresAplication.Infohogar.hogar!!.encuestas.filterIndexed { index, survey ->
                    survey.id == encuesta.value.id
                }.forEach() {
                    it.respuestaSeleccionada = respuesta
                    it.estado = EstadoEncuesta.RESPONDIDA
                }
                preferencesManager.saveData("encuestas", Gson().toJson(AppHogaresAplication.Infohogar.hogar!!.encuestas))
                println("encuestaViewModel responderEncuesta ${Gson().toJson(AppHogaresAplication.Infohogar.hogar!!.encuestas)}")

                AppHogaresAplication.GlobalState.updateGlobalState(Encuesta())
                if (AppHogaresAplication.screenActual == "") {
                    AppHogaresAplication.screenActual = RoutesNav.rutaScreen.route
                }else{
                    AppHogaresAplication.funNav(AppHogaresAplication.screenActual)
                }

                loading.value = false
            }
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "encuestaViewModel", "responderEncuesta")
            }
        }
    }

    fun responderEncuestaCategoria(respuesta: String, encuesta: Encuesta) {
        try {

            viewModelScope.launch {
                println("encuestaViewModel responderEncuestaCategoria Init ${Gson().toJson(encuesta)}")
                encuesta.respuestaSeleccionada = respuesta
                encuesta.fechaRespuesta = LocalDate.now().toString()
                encuesta.estado = EstadoEncuesta.RESPONDIDA
                AppHogaresAplication.encuestas.filterIndexed { index, survey ->
                    survey.id == encuesta.id
                }.forEach() {
                    it.respuestaSeleccionada = respuesta
                    it.estado = EstadoEncuesta.RESPONDIDA
                }

                println("encuestaViewModel responderEncuestaCategoria Init ${Gson().toJson(encuesta)}")
                AppHogaresAplication.GlobalState.updateGlobalState(Encuesta())

                encuestaFinalizada.value = AppHogaresAplication.encuestas.filterIndexed { index, survey ->
                    survey.estado == EstadoEncuesta.ABIERTA
                }.isEmpty()

                encuestasCategoriaAbiertas.value = AppHogaresAplication.encuestas.filter { it.estado == EstadoEncuesta.ABIERTA && it.categoria != "" }
                println("encuestaViewModel responderEncuestaCategoria Fin ${Gson().toJson(encuesta)}")

                if(!encuestaFinalizada.value) {
                    AppHogaresAplication.funNav(RoutesNav.encuestaCategoriaScreen.route)
                }
            }
        }catch (e: Exception) {
            viewModelScope.launch {
                println("encuestaViewModel Error responderEncuestaCategoria ${e.message}")
                logError.RegistrarError(e.message.orEmpty(), "encuestaViewModel", "responderEncuesta")
            }
        }
    }

    fun finalizarEncuesta(){

        AppHogaresAplication.Infohogar.hogar!!.encuestas = AppHogaresAplication.encuestas
        preferencesManager.saveData("encuestas", Gson().toJson(AppHogaresAplication.Infohogar.hogar!!.encuestas))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                network.GrabarInfoHogar(true)
            }
        }

        AppHogaresAplication.funNav(RoutesNav.gestionaScreen.route)
    }

}