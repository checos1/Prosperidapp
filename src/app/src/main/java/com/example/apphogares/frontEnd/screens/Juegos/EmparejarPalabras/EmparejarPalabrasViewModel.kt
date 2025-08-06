package com.example.apphogares.frontEnd.screens.Juegos.EmparejarPalabras

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.core.models.juegos.Card_2
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmparejarPalabrasViewModel  @Inject constructor(val navegacionApp: NavegacionApp, private val logError: LogError): ViewModel() {

    val lista = mutableListOf<Card_2>()

    val isCorrect: MutableState<Boolean> = mutableStateOf(false)
    val codigo: MutableState<Int> = mutableStateOf(0)
    val nombre: MutableState<String> = mutableStateOf("")
    var selectedStates : SnapshotStateList<Boolean> = mutableStateListOf(false)
    val contador: MutableState<Int> = mutableStateOf(1)
    val contadorSelected: MutableState<Int> = mutableStateOf(0)

    val msgError: MutableState<String> = mutableStateOf("")
    val rutaCompletada: MutableState<Boolean> = mutableStateOf(false)
    val activityCompleted : MutableState<Boolean> = mutableStateOf(false)


    init {
        getListas()
    }

    private fun getListas() {
        try {
            var actividadTematica = AppHogaresAplication.listaActividadesTematicaEnCurso.filterIndexed { index, actividade ->
                actividade.tipo == "ComponentHerramientasRelacion"
            }.firstOrNull()
            //println("emparejar 1: " + actividadTematica)
            if (actividadTematica != null){
                if (!actividadTematica.palabras.isNullOrEmpty() && actividadTematica.palabras.length > 0){
                    actividadTematica.palabras.split("|").forEachIndexed {index, iterator ->
                        //var index = 0

                        lista.add(Card_2(index,iterator))

                    }
                }

                if (!actividadTematica.relacion.isNullOrEmpty() && actividadTematica.relacion.length > 0){
                    actividadTematica.relacion.split("|").forEachIndexed {index, iterator ->
                        lista.add(Card_2(index,iterator))
                    }
                }

            }
            //Llenar lista
        }catch (e: Exception) {
            viewModelScope.launch {
                //logError.RegistrarError(e.message.orEmpty(), "CompletarPalabrasViewModel", "getFrases")
            }
            msgError.value = "Ha ocurrido un error!! getListas" + e.stackTrace
        }
    }

    val cards = lista

    private fun createListaRandom(words: List<Card_2>): List<Card_2> {
        val pairedWords = (words).shuffled()
        return pairedWords.shuffled()
    }

    fun ActualizaActividaTematica(tematicaCode: String, indexActividadEnCurso: Int){

        viewModelScope.launch {
            try {
                navegacionApp.ActualizaActividaTematica(tematicaCode, indexActividadEnCurso)
            }catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "CompletarPalabrasViewModel", "ActualizaActividaTematica")
                msgError.value = "Ha ocurrido un error!! ActualizaActividaTematica" + e.message
            }
        }
    }

    fun playAudio(i: Int) {
        viewModelScope.launch {
            Audio().startAudioFileFromPositionGamificacion(i)
            delay(2000)
        }
    }
}