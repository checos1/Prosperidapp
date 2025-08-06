package com.example.apphogares.frontEnd.screens.InfoHogar.ContactoScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import com.example.apphogares.backEnd.core.models.hogarMain.Hogar
import com.example.apphogares.backEnd.core.models.hogarMain.HogarPreguntas
import com.example.apphogares.backEnd.core.models.navegacion.Integrante
import com.example.apphogares.backEnd.core.models.navegacion.Navegacion
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.PreferencesManager
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.example.apphogares.frontEnd.RoutesNav
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HogarContactoViewModel @Inject constructor(private val repository: AppRepository, private val logError: LogError,
                                                 private val preferencesManager: PreferencesManager): ViewModel()  {
    var isPromptVisible : MutableState<Boolean> = mutableStateOf(false)
    var numeroContacto : MutableState<String> = mutableStateOf("")
    var numerosDeContacto: MutableState<List<String>> = mutableStateOf(emptyList())
    var cerraSesion : MutableState<Boolean> = mutableStateOf(false)

    val msgError: MutableState<String> = mutableStateOf("")

    init {
        try {
            numerosDeContacto.value = AppHogaresAplication.Infohogar.hogar?.contacto?.numerosContacto.orEmpty()
        }catch (e: Exception) {
            viewModelScope.launch {
                println("Error: ${e.message}")
                logError.RegistrarError(e.message.orEmpty(), "HogarContactoViewModel", "init")
            }
            msgError.value = e.message.orEmpty()
        }

    }

    fun addItemToContactList(item: String) {
        try {
            val currentList = numerosDeContacto.value.toMutableList()
            currentList.add(item)
            numerosDeContacto.value = currentList

            AppHogaresAplication.Infohogar.hogar?.contacto?.numerosContacto = numerosDeContacto.value
            viewModelScope.launch {
                repository.insert(HogarListaPreguntas(
                    AppHogaresAplication.Infohogar.idHogar,
                    AppHogaresAplication.integranteSeleccionado,
                    Gson().toJson(AppHogaresAplication.Infohogar)))
            }
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "HogarContactoViewModel", "addItemToContactList")
            }
            msgError.value = e.message.orEmpty()
        }

    }

    fun cerrarSesionHogar(){
        viewModelScope.launch {
            try {
                preferencesManager.saveData("notificaciones", "")
                preferencesManager.saveData("encuestas", "")

                AppHogaresAplication.Infohogar = HogarPreguntas("", "", false, emptyList(), Hogar("",  "", "", "", "", "","", "", "", "",null, emptyList(), emptyList()))
                AppHogaresAplication.integranteSeleccionado = ""
                AppHogaresAplication.navegacion = Navegacion(mutableListOf<Integrante>(), 0, 10, 5 )

                var hogarInfoApp = HogarListaPreguntas(
                    AppHogaresAplication.Infohogar.hogar!!.idHogar,
                    AppHogaresAplication.integranteSeleccionado,
                    Gson().toJson(AppHogaresAplication.Infohogar)
                )
                repository.insert(hogarInfoApp)
                AppHogaresAplication.funNav(RoutesNav.loginScreen.route)
            }catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "HogarContactoViewModel", "cerrarSesionHogar")
            }
        }

    }
}