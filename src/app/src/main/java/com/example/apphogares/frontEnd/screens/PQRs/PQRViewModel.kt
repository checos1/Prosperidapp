package com.example.apphogares.frontEnd.screens.PQRs

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.apphogares.backEnd.Services.LogError

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import androidx.compose.ui.graphics.Color

import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.servicesAPI.PQRApi
import com.example.apphogares.backEnd.Services.servicesAPI.PQRDocumentAPI
import com.example.apphogares.backEnd.core.models.PQR.PQRs

import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Blue
import com.example.apphogares.frontEnd.theme.BlueSky
import com.example.apphogares.frontEnd.theme.Red
import com.example.apphogares.frontEnd.theme.RedLight
import com.example.apphogares.frontEnd.theme.VerdeCorrecto
import com.example.apphogares.frontEnd.theme.VerdeCorrectoTwo
import com.google.gson.Gson
import kotlinx.coroutines.launch

@HiltViewModel
class PQRViewModel @Inject constructor(private val apiPQR: PQRDocumentAPI, private val logError: LogError): ViewModel() {

    val items : MutableState<List<PQRDocument>> = mutableStateOf(listOf())
    val showDialogSinInternet: MutableState<Boolean> = mutableStateOf(false)

    init {
        viewModelScope.launch {
            try {
                if (AppHogaresAplication.listaPQRs.size == 0) {
                    AppHogaresAplication.listaPQRs = apiPQR.ObtenerPQRs(AppHogaresAplication.Infohogar.idHogar)
                }
                items.value = AppHogaresAplication.listaPQRs
                println("PQRiewModel init ->> ${Gson().toJson(items.value)} ")
            } catch (e: Exception) {
                println("PQRiewModel init error ->> ${e.message}")
                logError.RegistrarError(e.message.orEmpty(), "PQRViewModel", "init")
            }
        }
    }

    fun GetStatusColor(status: String?): Color {
        if (status == null) {
            return BackGroundTopLogin
        }
        val estadoPQR = status.uppercase().replace("_", " ")
        println("PQRiewModel GetStatusColor ->> ${estadoPQR}")
        return PQRs.listStatusColor.filter { it.status.uppercase() == estadoPQR }[0].statusColor
    }

    fun GetTipoColor(tipo: String?): Color {
        if (tipo == null) {
            return BackGroundTopLogin
        }
        println("PQRiewModel GetTipoColor ->> ${tipo}")
        var tiposPQR = PQRs.tipos.filter { it.tipo.uppercase() == tipo.uppercase() }
        if (tiposPQR.isEmpty()) {
            println("PQRiewModel GetTipoColor ->> Tipo no encontrado: ${tipo}")
            return BackGroundTopLogin
        }
        return tiposPQR[0].tipoColor
    }

    fun ShowInformacionPQR(item: PQRDocument) {
        AppHogaresAplication.selectedPQR = item
        AppHogaresAplication.funNav(RoutesNav.InformacionPQRScreen.route)
    }
}

