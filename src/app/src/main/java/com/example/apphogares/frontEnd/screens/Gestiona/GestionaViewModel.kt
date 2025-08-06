package com.example.apphogares.frontEnd.screens.Gestiona

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.PreferencesManager
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.servicesAPI.ContenidoCMSAPI
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.example.apphogares.frontEnd.RoutesNav
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GestionaViewModel @Inject constructor(
                    private val network: network,
                    private val contenidoCMSAPI: ContenidoCMSAPI,
                    private val preferencesManager: PreferencesManager
                ): ViewModel()  {

    val encuestasCategoriasAbiertas: MutableState<List<Encuesta>> = mutableStateOf(listOf())

    init {
        println("versionViewModel Init")
        try {
            viewModelScope.launch {
                encuestasCategoriasAbiertas.value = AppHogaresAplication.encuestas.filter { it.estado == EstadoEncuesta.ABIERTA && it.categoria != "" }

                AppHogaresAplication.Infohogar.hogar!!.encuestas = AppHogaresAplication.encuestas

                withContext(Dispatchers.IO) {
                    network.GrabarInfoHogar()
                }
            }


        } catch (e: Exception) {
            println("versionViewModel ${Gson().toJson(AppHogaresAplication.encuestas)}")
        }
    }

    fun GetEncuestaCategoria(){
        try {
            if(!AppHogaresAplication.estadoDispositivo.isInternetConectivity) {

                Toast.makeText(AppHogaresAplication.context, "No hay conexión a internet", Toast.LENGTH_SHORT).show()
                return
            }
            viewModelScope.launch {
                val numeroDocumento =
                    AppHogaresAplication.Infohogar.hogar!!.integrantes?.filterIndexed() { index, integrante ->
                        integrante.idIntegranteHogar == AppHogaresAplication.integranteSeleccionado
                    }?.first()?.numeroDocumento
                var response = contenidoCMSAPI.GetEncuestaCategoria(numeroDocumento ?: "")
                if (response.esError) {
                    Toast.makeText(AppHogaresAplication.context, "Error al solicitar Encuesta", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(AppHogaresAplication.context, "Solicitud Encuesta enviada", Toast.LENGTH_SHORT).show()
                    var listaEncuesta = Gson().fromJson(response.mensaje, Array<Encuesta>::class.java).toList()
                    listaEncuesta.forEach() { encuesta ->
                        val existeEncuesta =
                            AppHogaresAplication.Infohogar.hogar?.encuestas!!.filterIndexed { _, survey ->
                                encuesta.id == survey.id
                            }.isNotEmpty()
                        if (!existeEncuesta) {
                            encuesta.estado = EstadoEncuesta.ABIERTA
                            AppHogaresAplication.Infohogar.hogar?.encuestas!!.add(encuesta)
                        }
                    }
                    if (AppHogaresAplication.Infohogar.hogar?.encuestas!!.filterIndexed() { index, survey ->
                            survey.estado == EstadoEncuesta.ABIERTA
                        }.isNotEmpty()) {
                        val encuestasJson = Gson().toJson(AppHogaresAplication.Infohogar.hogar?.encuestas)

                        preferencesManager.saveData("encuestas", encuestasJson)

                        AppHogaresAplication.encuestas =
                            AppHogaresAplication.Infohogar.hogar?.encuestas?.toMutableList()
                                ?: mutableListOf()

                        encuestasCategoriasAbiertas.value = AppHogaresAplication.encuestas.filter { it.estado == EstadoEncuesta.ABIERTA && it.categoria != "" }

                        withContext(Dispatchers.IO) {
                            network.GrabarInfoHogar()
                        }


                        AppHogaresAplication.funNav(RoutesNav.encuestaCategoriaScreen.route)
                    }else{
                        Toast.makeText(AppHogaresAplication.context, "No hay Encuestas disponibles", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            //logError.RegistrarError(e.message.orEmpty(), "encuestaViewModel", "solicitarEncuesta")
        }
    }
}