package com.example.apphogares.frontEnd.screens.InfoHogar.Hogar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.core.models.hogarMain.HogarInfo
import com.example.apphogares.backEnd.core.models.hogarMain.Integrante
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.NotificationHandler
import com.example.apphogares.backEnd.core.models.Programa
import com.example.apphogares.frontEnd.RoutesNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class hogarState {
    Loading,
    ShowContactInformation,
    Error
}

@HiltViewModel
class infoHogarViewModel @Inject constructor(private val navegacionApp: NavegacionApp,
                                             private val logError: LogError,): ViewModel(){

    private var dataHogar: MutableState<HogarInfo> = mutableStateOf(HogarInfo())
    val msgError: MutableState<String> = mutableStateOf("")
    var listaIntegrantesHogar: MutableList<Integrante> = mutableListOf()
    val jefeHogar: MutableState<Integrante>  = mutableStateOf(Integrante())


    var idIntegrante: MutableState<String> = mutableStateOf("")
    var programaSeleccionado: MutableState<String> = mutableStateOf("")

    var showMemberInformation: MutableState<Boolean> = mutableStateOf(false)
    var showInformationPrograma: MutableState<Boolean> = mutableStateOf(false)
    var ShowAyudaHogar: MutableState<Boolean> = mutableStateOf(false)

    var stateHogar: MutableState<hogarState> = mutableStateOf(hogarState.Loading)


    init {
        viewModelScope.launch {
            try {
                setDataHogar()
                listaIntegrantesHogar = (AppHogaresAplication.Infohogar.hogar!!.integrantes as MutableList<Integrante>?)!!
                jefeHogar.value = listaIntegrantesHogar.first { it.idIntegranteHogar == AppHogaresAplication.Infohogar.idIntegranteHogar }
                AppHogaresAplication.screenActual = RoutesNav.infoHogarScreen.route
                NotificationHandler().notificationFlow.collect { notification ->
                    AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
                }
            }catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "infoHogarViewModel", "init")
                msgError.value = "Ha ocurrido un error init!!" + e.message
                stateHogar.value = hogarState.Error
            }
        }

    }

    fun setDataHogar(){
        try {
            dataHogar.value.idHogar = AppHogaresAplication.Infohogar.idHogar
            dataHogar.value.listaIntegrantes = AppHogaresAplication.Infohogar.hogar!!.integrantes
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "infoHogarViewModel", "setDataHogar")
            }
            msgError.value = "Ha ocurrido un error setDataHogar!!" + e.message
            stateHogar.value = hogarState.Error
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarVisitaHija(vistaPadre:String, vistaHija:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                navegacionApp.AgregarVisitaHija(vistaPadre, vistaHija)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "infoHogarViewModel", "setDataHogar")
            }
            stateHogar.value = hogarState.Error
            msgError.value = "Ha ocurrido un error agregarVisitaHija!!" + e.message
        }
    }

    fun ShowMemberInformation(idIntegranteHogar: String){
        showMemberInformation.value = true
        idIntegrante.value = idIntegranteHogar
    }

    fun ShowInformationPrograma(programa: String){
        showInformationPrograma.value = true
        programaSeleccionado.value = programa
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ShowContactInformation(){
        stateHogar.value = hogarState.ShowContactInformation
        agregarVisitaHija("InfoHogar", "Contacto")
    }

}