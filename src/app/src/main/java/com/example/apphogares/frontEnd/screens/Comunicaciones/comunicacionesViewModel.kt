package com.example.apphogares.frontEnd.screens.Comunicaciones

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.core.models.Comunicaciones.Comunicacion
import com.example.apphogares.frontEnd.RoutesNav
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class comunicacionesScreenState {
    Loading,
    ShowAlertInformation,
    ShowInformateInformation,
    Error
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class comunicacionesViewModel @Inject constructor(
    private val navegacionApp: NavegacionApp,
    private val logError: LogError
): ViewModel()  {
    val selectedTab: MutableState<Int> = mutableStateOf(0)
    val tabs = listOf("Infórmate", "Alertas")
    val msgError: MutableState<String> = mutableStateOf("")
    val screenState: MutableState<comunicacionesScreenState> = mutableStateOf(comunicacionesScreenState.ShowInformateInformation)
    val comunicacion: MutableState<Comunicacion?> = mutableStateOf(Comunicacion())

    init {
        viewModelScope.launch {
            try {
                AppHogaresAplication.screenActual = RoutesNav.ComunicacionesScreen.route
                comunicacion.value = AppHogaresAplication.contenidoCMS.Comunicaciones!![0]
                println("comunicacionesViewModel init: ${Gson().toJson(AppHogaresAplication.contenidoCMS.Comunicaciones)}")
            } catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "comunicacionesViewModel", "init")
                msgError.value = "Ha ocurrido un error comunicacionesViewModel init!!" + e.message
                screenState.value = comunicacionesScreenState.Error

            }
        }
    }
}