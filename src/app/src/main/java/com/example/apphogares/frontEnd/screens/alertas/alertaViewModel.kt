package com.example.apphogares.frontEnd.screens.alertas

import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.Services.ServicesSystem.PreferencesManager
import com.example.apphogares.frontEnd.RoutesNav
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.utilities

enum class alertaScreenState {
    Loading,
    ShowContactInformation,
    Error
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class alertaViewModel @Inject constructor(private val repository: AppRepository,
                                          private val navegacionApp: NavegacionApp,
                                          private val netWork: network,
                                          private val preferencesManager: PreferencesManager,
                                          private val utilities: utilities,
                                          private val logError: LogError): ViewModel()  {

    val notificacion: MutableState<Notificacion> = mutableStateOf(Notificacion())
    var listAlerts : MutableState<List<Notificacion>> = mutableStateOf(emptyList())
    val showEmpleo: MutableState<Boolean> = mutableStateOf(false)
    val showDialog: MutableState<Boolean> = mutableStateOf(false)
    val categoriaSeleccionada: MutableState<String> = mutableStateOf("")
    val msgError: MutableState<String> = mutableStateOf("")

    val mostrarPDF: MutableState<Boolean> = mutableStateOf(false)
    val showDialogDelete: MutableState<Boolean> = mutableStateOf(false)

    val stateAlertaScreen: MutableState<alertaScreenState> = mutableStateOf(alertaScreenState.Loading)

    init {
        AppHogaresAplication.screenActual = RoutesNav.alertaScreen.route
        viewModelScope.launch {
            try {
                GetAlerts()


                    //AppHogaresAplication.alertas
/*                if (AppHogaresAplication.Infohogar.hogar?.notificaciones?.size!! > 0)
                {
                    repository.insert(
                        HogarListaPreguntas(
                            AppHogaresAplication.Infohogar.idHogar, AppHogaresAplication.integranteSeleccionado, Gson().toJson(
                                AppHogaresAplication.Infohogar)))
                }*/
            } catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "alertaViewModel", "init")
                msgError.value = "Ha ocurrido un error alertaViewModel init!!" + e.message
            }
        }
        agregarVisitaHija("Alerta", "PaginaInicial")
    }

    private fun GetAlerts() {
        if (AppHogaresAplication.Infohogar.hogar?.notificaciones?.size!! > 0){
            val filteredNotifications = AppHogaresAplication.Infohogar.hogar?.notificaciones
                ?.filter { it.estadoNotificacion != "Eliminada" }
                ?.toMutableList()
                ?: mutableListOf()

            listAlerts.value = filteredNotifications
        }
    }

    fun seleccionarCategoria(categoria: String) {
        try {
            categoriaSeleccionada.value = categoria
            listAlerts.value = AppHogaresAplication.alertas.filterIndexed() { index, notificacion ->
                notificacion.categoria == categoria
            }.toMutableList()
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "alertaViewModel", "seleccionarCategoria")
            }
            msgError.value = "Ha ocurrido un error seleccionarCategoria!!" + e.message
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
                logError.RegistrarError(e.message.orEmpty(), "alertaViewModel", "agregarVisitaHija")
            }
            msgError.value = "Ha ocurrido un error agregarVisitaHija!!" + e.message
        }
    }

    fun confirmarBoton(){
        try {
            notificacion.value.fechaConfirmacionBoton = LocalDate.now().toString()
            AppHogaresAplication.Infohogar.hogar?.notificaciones?.first {
                it.id == notificacion.value.id }!!.fechaConfirmacionBoton = notificacion.value.fechaConfirmacionBoton
            viewModelScope.launch(Dispatchers.IO) {
                netWork.GrabarInfoHogar(true)
            }
            showDialog.value = true
            showEmpleo.value = false
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "alertaViewModel", "ConfirmarBoton")
            }
            msgError.value = "Ha ocurrido un error confirmarBoton!!" + e.message
        }
    }

    fun eliminarNotificacion(notificacion: Notificacion) {
        AppHogaresAplication.Infohogar.hogar?.notificaciones?.first {
            it.id == notificacion.id
        }!!.estadoNotificacion = "Eliminada"

        val filteredNotifications = AppHogaresAplication.Infohogar.hogar?.notificaciones
            ?.filter { it.estadoNotificacion != "Eliminada" }
            ?.toMutableList()
            ?: mutableListOf()

        if(filteredNotifications.size > 0){
            val notificacionesJson = Gson().toJson(filteredNotifications)

            preferencesManager.saveData("notificaciones", notificacionesJson)
        }else{
            preferencesManager.saveData("notificaciones", "")
            clearNotification()
            //TODO: Limpiar icono de notificaciones
        }
        AppHogaresAplication.alertas = filteredNotifications
        GetAlerts()
        showDialogDelete.value = false
    }

    fun clearNotification() {
        val notificationManager = NotificationManagerCompat.from(AppHogaresAplication.context)
        notificationManager.cancel(notificacion.value.id.toInt()) // Cancel the notification
    }

    fun formatFecha(fechaIso: String): String{

        if (fechaIso.isNotEmpty()) {
            return utilities.formatFecha(fechaIso)
        }
        return ""
    }
}