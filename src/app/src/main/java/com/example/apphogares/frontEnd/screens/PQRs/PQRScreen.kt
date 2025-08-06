package com.example.apphogares.frontEnd.screens.PQRs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.AppHogaresAplication.Companion.funNav
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.Images.ThumbnailImage
import com.example.apphogares.frontEnd.screens.Components.alerts.AlertSimple
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.encabezadoPQR
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.listaPQRs
import com.example.apphogares.frontEnd.theme.BackGroundRuta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.White

@Composable
fun PQRScreen(PQRViewModel: PQRViewModel = hiltViewModel()) {


    if (PQRViewModel.showDialogSinInternet.value) {
        Dialog(onDismissRequest = { PQRViewModel.showDialogSinInternet.value = false }) {
            AlertSimple(
                title = "No está conectado a Internet?",
                textoButonAceptar = "Aceptar",
                onClickCerrar = {
                    PQRViewModel.showDialogSinInternet.value = false
                },
                onClickButonAceptar = {
                    PQRViewModel.showDialogSinInternet.value = false
                },
                contentDialogo = {

                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGroundTopLogin),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        encabezadoPQR("", "Peticiones, Quejas, Reclamos, Sugerencias, Denuncias y Felicitaciones")
        Divider(modifier = Modifier.padding(0.dp).background(BackGroundTopLogin), color = Color.Gray, thickness = 1.dp)
        Row(modifier = Modifier.background(BackGroundTopLogin).fillMaxWidth().height(70.dp) ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                    if (AppHogaresAplication.estadoDispositivo.isInternetConectivity){
                        PQRViewModel.showDialogSinInternet.value = false
                        funNav(RoutesNav.CrearPQRScreen.route)
                    } else {
                        PQRViewModel.showDialogSinInternet.value = true
                    }
                 },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = White),
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .padding(16.dp)
                    .background(BackGroundTopLoginPlus, shape = RoundedCornerShape(50.dp)),
            ) {
                Text(text = "+ Crear PQR", style = MaterialTheme.typography.titleSmall, color = BackGroundRuta)
            }
        }
        listaPQRs(PQRViewModel.items.value)
    }
    ThumbnailImage("https://sapsdafchogaresqa.blob.core.windows.net/cntpsdafchogaresqa/PQRs/a019cfa6-7518-4433-af04-8d19b422149b/a019cfa6-7518-4433-af04-8d19b422149b.jpg?sv=2022-11-02&ss=bfqt&srt=o&sp=rwdlacupiytfx&se=2028-06-11T22:16:51Z&st=2024-06-11T14:16:51Z&spr=https&sig=0uPuzsC0yl5pgJZmpMcZMUWJcM6dci3Zr0teVeK8gio%3D")
}

@Preview
@Composable
fun PQRScreenPreview() {
    PQRScreen()
}

