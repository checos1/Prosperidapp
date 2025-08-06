package com.example.apphogares.frontEnd.screens.Juegos.MemoriaPalabras

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.GestionImages
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.AppHogaresAplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoriaViewModel @Inject constructor(val navegacionApp: NavegacionApp, private val logError: LogError): ViewModel()  {

    val listaCard = mutableListOf<Card>()
    val cards = mutableListOf<Card>()

    val msgError: MutableState<String> = mutableStateOf("")
    val activityCompleted : MutableState<Boolean> = mutableStateOf(false)
    val rutaCompletada: MutableState<Boolean> = mutableStateOf(false)

    val contador: MutableState<Int> = mutableStateOf(1)
    //val matchedPairs: MutableState<Int> = mutableStateOf(1)
    val contentVisible: MutableState<Boolean> = mutableStateOf(false)
    //val contadorErrores: MutableState<Int> = mutableStateOf(1)
    var imageBitmap: ImageBitmap? = null

    fun getListaCard(context: Context)
    {
        try {
            if(listaCard.isNullOrEmpty() ) {
                var actividadTematica =
                    AppHogaresAplication.listaActividadesTematicaEnCurso.filterIndexed { index, actividade ->
                        actividade.tipo == "ComponentHerramientasMemoria"
                    }.firstOrNull()
                if (actividadTematica != null && actividadTematica!!.tarjetas != null && actividadTematica!!.tarjetas.size > 0) {
                    actividadTematica!!.tarjetas.forEachIndexed { index, tarjeta ->
                        var codigoTematica = AppHogaresAplication.listadoTarjetasTematica[0].idTematica

                        imageBitmap = GestionImages().obtenerRutaFisicaImagen(context, codigoTematica,  index)

                        cards.add(Card(actividadTematica!!.tarjetas.size + index, "1-${index}",imageBitmap!!))
                        listaCard.add(Card(index,  "1-${index}",imageBitmap!!))
                    }
                }
                cards.addAll(listaCard )
            }
            println("memoria--> listasize: " + listaCard.size)
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "MemoriaViewModel", "getListaCard")
            }
            msgError.value = "Ha ocurrido un error getListaCard!!" + e.message
        }
    }

    fun ActualizaActividaTematica(tematicaCode: String, indexActividadEnCurso: Int){

        viewModelScope.launch {
            try {
                navegacionApp.ActualizaActividaTematica(tematicaCode, indexActividadEnCurso)
            }catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "MemoriaViewModel", "ActualizaActividaTematica")
                msgError.value = "Ha ocurrido un error!!" + e.message
            }
        }
    }
}