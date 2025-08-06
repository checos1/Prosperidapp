package com.example.apphogares.frontEnd.screens.oferta

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.core.models.hogarMain.Oferta
import com.example.apphogares.backEnd.core.models.hogarMain.OfertaInfo
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.hogarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


enum class Opciones {
    LOGROS,
    OFERTADESCRIPCION
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class infoOfertaViewModel  @Inject constructor(private val navegacionApp: NavegacionApp, private val network: network,
                                               private val utilities: utilities,
                                               private val logError: LogError,):  ViewModel() {

    val opcion: MutableState<Opciones> = mutableStateOf(Opciones.LOGROS)
    val ofertaInfoHogar: MutableState<OfertaInfo> = mutableStateOf(OfertaInfo())

    var ofertaSeleccionada: MutableState<Oferta> = mutableStateOf(Oferta())

    val showOferta: MutableState<Boolean> = mutableStateOf(false)
    val showDialog: MutableState<Boolean> = mutableStateOf(false)
    val idOferta: MutableState<Int> = mutableStateOf(0)

    var msgError: MutableState<String> = mutableStateOf("")


    init {
        setOfertaInfoHogar()
        AppHogaresAplication.screenActual = RoutesNav.ofertaHogarScreen.route
        agregarVisitaHija("OfertaHogar", "PaginaInicial")
    }

    fun setOption(opcion: Opciones){
        this.opcion.value = opcion
    }
    fun setOfertaInfoHogar(){
        try {
            ofertaInfoHogar.value.idHogar = AppHogaresAplication.Infohogar.idHogar
            ofertaInfoHogar.value.listaOfertas = AppHogaresAplication.Infohogar.hogar!!.listaOferta?.filterIndexed { index, oferta ->
                utilities.ConvertToDate(
                    oferta.fechaFin
                ) >= LocalDate.now()
            }

        }catch (e: Exception){
            msgError.value = e.message.toString()
        }

    }

    fun ActualizarEstoyInteresado(idOferta: Int?, idHogar: String) {
        viewModelScope.launch {
            try {
                var ofertaInteresado = AppHogaresAplication.Infohogar.hogar!!.listaOferta!!.first { it.idOferta == idOferta && it.idHogar == idHogar }.let {
                    it.interesado = true
                    it.fechaActualizacion = LocalDate.now().toString()
                }
                network.GrabarInfoHogar()
            }catch (e: Exception){
                msgError.value = e.message.toString()
            }
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
                logError.RegistrarError(e.message.orEmpty(), "infoOfertaViewModel", "setOfertaInfoHogar")
            }
            msgError.value = "Ha ocurrido un error agregarVisitaHija!!" + e.message
        }
    }

}