package com.example.apphogares.frontEnd.screens.PeriodicoVida

import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.Downloads
import com.example.apphogares.backEnd.core.models.PeriodicoVida.PeriodicoVida
import com.example.apphogares.backEnd.core.models.navegacion.Notificacion
import com.example.apphogares.frontEnd.RoutesNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import android.os.Environment
import androidx.annotation.RequiresApi
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.hogarState
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class periodicoVidaViewModel @Inject constructor(private val navegacionApp: NavegacionApp,
                                                 private val logError: LogError): ViewModel()  {

    val notificacion: MutableState<Notificacion> = mutableStateOf(Notificacion())

    val mostrarPDF: MutableState<Boolean> = mutableStateOf(false)

    val msgError: MutableState<String> = mutableStateOf("")

    val periodicoSeleecionado: MutableState<PeriodicoVida> = mutableStateOf(PeriodicoVida())

    init {
        AppHogaresAplication.screenActual = RoutesNav.PeriodicoVidaScreen.route
        viewModelScope.launch {
            agregarVisitaHija("PeriodicoVida", "PeriodicoVida")
        }
    }

/*    fun downloadPdf(
        url: String = periodicoSeleecionado.value.urlPDF,
        fileName: String = "periodicoVida.pdf"
    ): String {
        var urlPDF = ""
        //val downloads = Downloads(AppHogaresAplication.context)
        //val idDownloadedFile = downloads.downloadPDF(url)
        //Thread.sleep(2000L)
        AppHogaresAplication.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let {
            val filePdf = File(it, fileName)
            if (filePdf.exists()) {
                urlPDF = it.path + "/periodicoVida.pdf"
            }
        }

        println("URL PDF: $urlPDF")
        return urlPDF
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarVisitaHija(vistaPadre:String, vistaHija:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                navegacionApp.AgregarVisitaHija(vistaPadre, vistaHija)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "periodicoVidaViewModel", "agregarVisitaHija")
            }
            msgError.value = "Ha ocurrido un error agregarVisitaHija!!" + e.message
        }
    }
}
