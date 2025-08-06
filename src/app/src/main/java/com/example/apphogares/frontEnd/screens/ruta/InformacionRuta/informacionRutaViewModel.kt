package com.example.apphogares.frontEnd.screens.ruta.InformacionRuta

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.frontEnd.RoutesNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class informacionRutaViewModel @Inject constructor(private val navegacionApp: NavegacionApp,
                                             private val logError: LogError): ViewModel() {

    val msgError: MutableState<String> = mutableStateOf("")

    init {
        AppHogaresAplication.screenActual = RoutesNav.alertaScreen.route
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "informacionRutaViewModel", "init")
                msgError.value = "Ha ocurrido un error informacionRutaViewModel init!!" + e.message
            }
        }
        agregarVisitaHija("InformacionRuta", "InformacionRuta")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarVisitaHija(vistaPadre:String, vistaHija:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                navegacionApp.AgregarVisitaHija(vistaPadre, vistaHija)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "informacionRutaViewModel", "agregarVisitaHija")
            }
            msgError.value = "Ha ocurrido un error agregarVisitaHija!!" + e.message
        }
    }
}
