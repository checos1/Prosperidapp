package com.example.apphogares.frontEnd.screens.ruta.Tematica

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class historietaViewModel @Inject constructor(
                                    public val navegacionApp: NavegacionApp,
                                    private val logError: LogError
): ViewModel() {

    val playAudioCompleted : MutableState<Boolean> = mutableStateOf(false)
    val activityCompleted : MutableState<Boolean> = mutableStateOf(false)
    var objIteration: MutableState<List<Float>> = mutableStateOf(listOf())
    val msgError: MutableState<String> = mutableStateOf("")


    fun ActualizaActividaTematica(tematicaCode: String, indexActividadEnCurso: Int) {

        try {
            viewModelScope.launch {
                navegacionApp.ActualizaActividaTematica(
                    tematicaCode,
                    AppHogaresAplication.indexActividadEnCurso
                )

            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(
                    e.message.orEmpty(),
                    "LoginViewModel",
                    "ActualizaActividaTematica"
                )
            }
            msgError.value = e.message.orEmpty()
        }
    }

    fun CompletarAudio(frase: String, duracion: Long) {
        Audio().startAudioMayo(frase, duracion)
        playAudioCompleted.value = true
    }

}