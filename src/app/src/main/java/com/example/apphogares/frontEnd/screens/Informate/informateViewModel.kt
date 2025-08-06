package com.example.apphogares.frontEnd.screens.Informate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.Services.servicesAPI.PublicacionAPI
import com.example.apphogares.backEnd.core.models.Comunicaciones.InteraccionRequest
import com.example.apphogares.backEnd.core.models.Comunicaciones.InteraccionesPublicacion
import com.example.apphogares.backEnd.core.models.Comunicaciones.Publicacion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class InformateScreenState {
    Loading,
    ShowInformateInformation,
    Error
}


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class informateViewModel @Inject constructor(
    private val navegacionApp: NavegacionApp,
    private val apiPublicaciones: PublicacionAPI,
    private val logError: LogError
) : ViewModel() {

    val msgError: MutableState<String> = mutableStateOf("")
    val isLoading = mutableStateOf(true)
    var likes: MutableState<Int> = mutableStateOf(0)
    var shared: MutableState<Int> = mutableStateOf(0)

    init {
        viewModelScope.launch {
            try {
                if (AppHogaresAplication.listaPublicaciones.size == 0) {
                    AppHogaresAplication.listaPublicaciones =
                        apiPublicaciones.ObtenerPublicaciones()
                }
                println("Publicaciones: ${AppHogaresAplication.listaPublicaciones}")
            } catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "informateViewModel", "init")
                msgError.value = "Ha ocurrido un error al cargar publicaciones: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }

        agregarVisitaHija("Comunicaciones", "Informate")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarVisitaHija(vistaPadre: String, vistaHija: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                navegacionApp.AgregarVisitaHija(vistaPadre, vistaHija)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(
                    e.message.orEmpty(),
                    "informateViewModel",
                    "agregarVisitaHija"
                )
            }
            msgError.value = "Error al registrar visita: ${e.message}"
        }
    }

    fun IncrementarInteraccion(id: String, titulo: String, tipo: String) {
        viewModelScope.launch {
            try {
                if(AppHogaresAplication.estadoDispositivo.isInternetConectivity){
                    var interaccionPublicacion = apiPublicaciones.InteraccionPublicacion(
                        InteraccionRequest(
                            idPublicacion = id,
                            titulo = titulo,
                            tipo
                        )
                    )
                    println("interaccion: ${interaccionPublicacion}")
                }

            } catch (e: Exception) {
                logError.RegistrarError(
                    e.message.orEmpty(),
                    "informateViewModel",
                    "IncrementarInteraccion"
                )
                msgError.value = "Ha ocurrido un error al Incrementar Interaccion: ${e.message}"
            }
        }
    }
}

