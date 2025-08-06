package com.example.apphogares.frontEnd.screens.Encuesta.Components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.PreferencesManager
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class encuestaRespuestaViewModel @Inject constructor(private val preferencesManager: PreferencesManager): ViewModel()  {

    val respuestaSeleccionada: MutableState<String> = mutableStateOf("")
    val listaRespuestasSeleccionadas: MutableState<List<String>> = mutableStateOf(listOf())
    val preguntaRespondida: MutableState<Boolean> = mutableStateOf(false)

    fun addItem(item: String) {
        val currentList = listaRespuestasSeleccionadas.value
        val newList = currentList + item // This creates a new list with the added item
        listaRespuestasSeleccionadas.value = newList
    }

    fun removeItem(item: String) {
        val currentList = listaRespuestasSeleccionadas.value
        val newList = currentList.filter { it != item } // This creates a new list without the item
        listaRespuestasSeleccionadas.value = newList
    }

    fun responderPregunta() {
        preguntaRespondida.value = true
        respuestaSeleccionada.value = ""
    }
}