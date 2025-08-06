package com.example.apphogares.frontEnd.screens.ruta

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.GamificacionTematica
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import com.example.apphogares.backEnd.core.models.gamificacion.EstadosTematicas
import com.example.apphogares.backEnd.core.models.gamificacion.Gamificacion
import com.example.apphogares.backEnd.core.models.hogarMain.HogarPreguntas
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.GestionImages
import com.example.apphogares.backEnd.core.models.contenidosRuta.Medalla
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRuta
import com.example.apphogares.frontEnd.RoutesNav
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.asImageBitmap
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.enums.RutaImagenEstado
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRutaEnum


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class rutaViewModel @Inject constructor(
                                        private val navegacionApp: NavegacionApp,
                                        private val logError: LogError
): ViewModel() {


    //Objeto Gamificación
    //val Gamification: MutableState<Gamificacion> = mutableStateOf(Gamificacion())

    private val error: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> = mutableStateOf(false)

    val msgError: MutableState<String> = mutableStateOf("")

    val existeContenidosRuta: MutableState<Boolean> = mutableStateOf(false)

    val existeEstadosRuta: MutableState<Boolean> = mutableStateOf(false)

    val contadorFelicitaciones: MutableState<Int> = mutableStateOf(0)

    //val rutaMedallaSeleccionada: MutableState<String> = mutableStateOf("")

    // Private mutable state that can only be modified within this ViewModel
    private val _showDialogPopUp: MutableState<Boolean> = mutableStateOf(false)

    // Public read-only access to the showDialogPopUp state
    val showDialogPopUp: State<Boolean> = _showDialogPopUp

    private val _rutaMedallaSeleccionada: MutableState<String> = mutableStateOf("")

    // Public read-only access to the showDialogPopUp state
    val rutaMedallaSeleccionada: State<String> = _rutaMedallaSeleccionada

    init {
        AppHogaresAplication.screenActual = RoutesNav.rutaScreen.route
        viewModelScope.launch(Dispatchers.IO) {
            try {
                println("rutaViewModel Init ->> ${Gson().toJson(AppHogaresAplication.contenidoCMS)}")
                navegacionApp.EstadoRutaMedallas()
                navegacionApp.CargarContenidosRuta()

                existeEstadosRuta.value = AppHogaresAplication.listaEstadosRutas.size > 0
                loading.value = true
            } catch (e: Exception) {
                println("contenidosRuta ->> errorvrutaViewModel: ${Gson().toJson(AppHogaresAplication.Infohogar.hogar!!)}")
                println("rutaViewModel Error: ${e.message}")
                //logError.RegistrarError(e.message.toString(), "LoginViewModel", "init")
            }
        }
    }

    fun validarContenidosRuta(){
        try {
            existeContenidosRuta.value = AppHogaresAplication.contenidoCMS.Categorias?.size!! > 0
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "LoginViewModel", "GetContenidosRuta")
            }
            msgError.value = e.message.orEmpty()
        }
    }


    fun GamiEstadoTematica(code: String): EstadosTematicas {

        try {
            val estadosTematicas = navegacionApp.ObtenerEstadoTematica(code)!!
            return estadosTematicas
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "LoginViewModel", "GamiEstadoTematica")
            }
            error.value = true
            msgError.value = e.message.orEmpty()
        }
        return EstadosTematicas()
    }

    fun ActualizaActividaTematica(tematicaCode: String, indexActividadEnCurso: Int) {

        try {
            //agregarVisitaHija("Ruta", tematicaCode)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarVisitaHija(vistaPadre:String, vistaHija:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                navegacionApp.AgregarVisitaHija(vistaPadre, vistaHija)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "rutaViewModel", "agregarVisitaHija")
            }
            msgError.value = "Ha ocurrido un error!! ${e}"
        }
    }

    fun ObternerImagenGamificacion(ruta: String) : ImageBitmap?{
        println("Ruta ViewModel ruta: ${ruta}")
        var imageBitmap: ImageBitmap? = null
        try {
            val estadoRuta = ObtenerEstadoRuta(ruta)
            println("Ruta ViewModel estadoRuta: ${estadoRuta}")
            if (estadoRuta != null) {
                println("Ruta ViewModel estadoRuta: ${Gson().toJson(estadoRuta)}")
                if (estadoRuta.estadoRuta == EstadoRutaEnum.Realizado) {
                    estadoRuta.imagenEstado = RutaImagenEstado.LLAVE
                    imageBitmap = loadImage(AppHogaresAplication.context, R.drawable.ic_key)
                } else {
                    val index = AppHogaresAplication.listaEstadosRutas.indexOfFirst { it.ruta == ruta }
                    if(index == 0) {
                        estadoRuta.imagenEstado = RutaImagenEstado.LLAVE
                        imageBitmap = loadImage(AppHogaresAplication.context, R.drawable.ic_key)
                    } else {
                        estadoRuta.imagenEstado = RutaImagenEstado.LLAVE
                        imageBitmap = loadImage(AppHogaresAplication.context, R.drawable.ic_candado)
                    }
                }
            } else {
                println("Ruta ViewModel estadoRuta es nulo")
            }

            if (estadoRuta.imagenEstado == RutaImagenEstado.CANDADO && ObtenerEstadoRutaAnterior(ruta) != RutaImagenEstado.LLAVE) {
                estadoRuta.imagenEstado = RutaImagenEstado.LLAVE
                imageBitmap = loadImage(AppHogaresAplication.context, R.drawable.ic_key)

            }
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    navegacionApp.ActualizarEstadosRutas(estadoRuta)
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    logError.RegistrarError(e.message.orEmpty(), "rutaViewModel", "agregarVisitaHija")
                }
                msgError.value = "Ha ocurrido un error!! ${e}"
            }
/*            println("Ruta ViewModel listaMedallasRuta: ${Gson().toJson(AppHogaresAplication.listaMedallasRuta)}")
            val medallaRuta = AppHogaresAplication.listaMedallasRuta.filterIndexed() { index, medalla ->
                medalla.ruta == ruta
            }.firstOrNull()
            println("Ruta ViewModel medallaRuta: ${Gson().toJson(medallaRuta)}")
            if (medallaRuta != null && medallaRuta.ruta != "") {
                imageBitmap = ObtenerMedallaEstadoRuta(ruta, estadoRuta, medallaRuta)
            }*/
        } catch (e: Exception) {
            println("Ruta ViewModel ObternerImagenGamificacion Error: ${e.message}")
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "rutaViewModel", "ObternerImagenGamificacion")
            }
            //msgError.value = "Ha ocurrido un error!! ObternerImagenGamificacion ${e}"
        }
        return imageBitmap
    }

    private fun ObtenerMedallaEstadoRuta(ruta: String, estadoRuta: EstadoRuta, medallaRuta: Medalla): ImageBitmap? {
        var imageBitmap: ImageBitmap? = null
        if (estadoRuta.estadoRuta == EstadoRutaEnum.Realizado) {
            if (medallaRuta.imagenActiva != null) {
                imageBitmap = medallaRuta.imagenActiva
            } else {
                imageBitmap = GestionImages().obtenerRutaFisicaImagenRuta(AppHogaresAplication.context, ruta, EstadoRutaEnum.Realizado)
            }
        } else {
            if (medallaRuta.imagenInactiva != null) {
                imageBitmap = medallaRuta.imagenInactiva
            } else {
                imageBitmap = GestionImages().obtenerRutaFisicaImagenRuta(AppHogaresAplication.context, ruta, EstadoRutaEnum.Incompleto)
            }
        }
        return imageBitmap
    }

    fun ObtenerEstadoRuta(ruta: String): EstadoRuta {
        var estadoRuta = EstadoRuta()
        try {
            println("rutaViewModel ObtenerEstadoRuta ruta: ${Gson().toJson(AppHogaresAplication.listaEstadosRutas)}")
            estadoRuta = AppHogaresAplication.listaEstadosRutas.filterIndexed(
                { index, estadoRuta -> estadoRuta.ruta == ruta }
            ).firstOrNull()!!
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "rutaViewModel", "ObtenerEstadoRuta")
            }
            msgError.value = "Ha ocurrido un error!! ObtenerEstadoRuta ${e}"
        }
        return estadoRuta
    }

    fun ObtenerMedalla(ruta: String): Medalla? {
        var medalla: Medalla? = null
        try {
            medalla = AppHogaresAplication.listaMedallasRuta.filterIndexed(
                { index, medalla -> medalla.ruta == ruta }
            ).firstOrNull()
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "rutaViewModel", "ObtenerMedalla")
            }
            msgError.value = "Ha ocurrido un error!! ObtenerMedalla ${e}"
        }
        return medalla
    }

    fun SetDialogMedalla(rutaMedalla: String){
        _rutaMedallaSeleccionada.value = rutaMedalla
        existeMedallaSeleccionada(rutaMedalla)
    }

    fun existeMedallaSeleccionada(rutaSeleecionada: String){
        if(rutaSeleecionada != ""){
            _rutaMedallaSeleccionada.value = rutaSeleecionada
            _showDialogPopUp.value = true
        }
    }

    fun loadImage(context: Context, drawableId: Int): ImageBitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
        return bitmap.asImageBitmap()
    }

    fun ObtenerEstadoRutaAnterior(ruta: String): RutaImagenEstado {
        var estadoRuta = RutaImagenEstado.CANDADO
        try {
            val index = AppHogaresAplication.listaEstadosRutas.indexOfFirst { it.ruta == ruta }
            if (index > 0) {
                estadoRuta = AppHogaresAplication.listaEstadosRutas[index - 1].imagenEstado
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "rutaViewModel", "ObtenerEstadoRutaAnterior")
            }
            msgError.value = "Ha ocurrido un error!! ObtenerEstadoRutaAnterior ${e}"
        }
        return estadoRuta
    }

    fun ObtenerImagenEstadoRuta(indexRuta: Int): RutaImagenEstado {
        var imagenEstado: RutaImagenEstado = RutaImagenEstado.CANDADO
        try {
            if (indexRuta < AppHogaresAplication.listaEstadosRutas.size) {
                imagenEstado = AppHogaresAplication.listaEstadosRutas[indexRuta].imagenEstado
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "rutaViewModel", "ObtenerImagenEstadoRuta")
            }
            msgError.value = "Ha ocurrido un error!! ObtenerImagenEstadoRuta ${e}"
        }
        return imagenEstado
    }
}