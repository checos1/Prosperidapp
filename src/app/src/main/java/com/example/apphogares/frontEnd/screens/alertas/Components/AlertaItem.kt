package com.example.apphogares.frontEnd.screens.alertas.Components

import android.os.Build
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.example.apphogares.frontEnd.screens.alertas.alertaViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.blueOval
import androidx.compose.ui.window.Dialog
import com.example.apphogares.frontEnd.screens.Components.alerts.AlertSimple
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertaItem(notificacion: Notificacion) {
    val alertaViewModel: alertaViewModel = viewModel()

    println("AlertaItem notificacion: ${notificacion}")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(DefineColorSpiral(notificacion.categoria)),
            //.border(0.5.dp, color = Gray),
        verticalAlignment = Alignment.CenterVertically)
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(0.dp)
            .weight(7f)
            .background(color = Color.White),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = notificacion.mensaje.substring(0, minOf(notificacion.mensaje.length, 50)) + if (notificacion.mensaje.length > 50) "..." else "",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2, // O usa 2 si quieres dos líneas
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.padding(2.dp))

                }
                // Botón eliminar a la derecha
                Icon(
                    painter = painterResource(id = R.drawable.icondelete),
                    contentDescription = "Eliminar notificación",
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(28.dp)
                        .clickable {
                            alertaViewModel.notificacion.value = notificacion
                            alertaViewModel.showDialogDelete.value = true
                        }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)    ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = alertaViewModel.formatFecha(notificacion.fechaFin.substring(0, 10)),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.weight(1f))
                // Botón "VER MÁS"
                Button(
                    onClick = {
                        if (notificacion.linkExterno != "") {
                            alertaViewModel.notificacion.value = notificacion
                            alertaViewModel.mostrarPDF.value = true
                        } else {
                            alertaViewModel.showEmpleo.value = true
                            alertaViewModel.notificacion.value = notificacion
                        }
                    },
                    colors = buttonColors(containerColor = BackgroundBottomInTo),
                    shape = MaterialTheme.shapes.small,
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    elevation = null
                ) {
                    Text(
                        "VER MÁS",
                        color = Color.Black,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

        }
        Box(
            modifier = Modifier
                .width(10.dp) // ancho de la barra, puedes ajustar
                .fillMaxHeight()
                .background(DefineColorSpiral(notificacion.categoria))
        )
/*        Column(modifier = Modifier
            .fillMaxSize()
            .background(DefineColorSpiral(notificacion.categoria))
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .weight(0.3f, true),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center

        ) {

        }*/
    }
}

fun DefineColorSpiral(categoria: String): Color {

    return when (categoria) {
        "empleo" -> BackGroundTopLogin
        "interes" -> blueOval
        "prosperidad" -> BlueTwo
        else -> BlueTwo
    }
}

