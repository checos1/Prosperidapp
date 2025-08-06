package com.example.apphogares.frontEnd.screens.alertas


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.alerts.AlertSimple
import com.example.apphogares.frontEnd.screens.Components.titles.BarTopScreen
import com.example.apphogares.frontEnd.screens.alertas.Components.NotificationList
import com.example.apphogares.frontEnd.screens.alertas.Components.TabsCategories
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.Blue
import com.example.apphogares.frontEnd.theme.Gray


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertaScreen(alertaViewModel: alertaViewModel = hiltViewModel()){

    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
    }

    if (alertaViewModel.msgError.value != "") {
        ErrorScreen(messageError = alertaViewModel.msgError.value)
    }

    if (alertaViewModel.showDialogDelete.value) {
        Dialog(onDismissRequest = { alertaViewModel.showDialogDelete.value = false }) {
            AlertSimple(
                title = "Está seguro de eliminar la alerta?",
                textoButonAceptar = "Aceptar",
                onClickCerrar = {
                    alertaViewModel.showDialogDelete.value = false
                },
                onClickButonAceptar = {
                    alertaViewModel.eliminarNotificacion(alertaViewModel.notificacion.value)
                },
                contentDialogo = {

                }
            )
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = BackGroundTopLogin)){
/*             Spacer(modifier = Modifier.padding(16.dp))
             BarTopScreen(id = R.drawable.icon_bell, text = "Alertas")
             Divider(color = Gray, thickness = 1.dp)*/
/*             TabsCategories(
                 onSelect = {
                     alertaViewModel.seleccionarCategoria(it)
             })*/
             NotificationList(alertaViewModel.listAlerts.value)
    }
}




