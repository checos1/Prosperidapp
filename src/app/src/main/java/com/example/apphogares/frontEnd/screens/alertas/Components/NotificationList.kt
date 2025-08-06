package com.example.apphogares.frontEnd.screens.alertas.Components

import android.os.Build
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI
import com.example.apphogares.frontEnd.screens.Components.layouts.DialogUICustom
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components.InformacionIntegranteScreen
import com.example.apphogares.frontEnd.screens.alertas.alertaViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationList(alerts: List<Notificacion>, alertaViewModel: alertaViewModel = hiltViewModel()) {
    //val alertaViewModel: alertaViewModel = viewModel()
    if(alertaViewModel.showEmpleo.value)
    {
        Dialog(onDismissRequest = { alertaViewModel.showEmpleo.value = false} ) {
            DialogUICustom(openDialogCustom =  alertaViewModel.showEmpleo,
                title = alertaViewModel.notificacion.value.asunto,
                textBoton = "Aceptar",
                onClick = { alertaViewModel.showEmpleo.value = false},
                modifier = Modifier.clip(RoundedCornerShape(0, 0, 10, 10))
            ){
                DesEmpleoScreen(alertaViewModel.notificacion.value)
            }
        }
/*        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White))
        {
            Row(modifier = Modifier
                .fillMaxSize()
                .weight(2.5f))
            {
                AlertaItem(alertaViewModel.notificacion.value)
            }
            Row(modifier = Modifier
                .fillMaxSize()
                .weight(6.5f))
            {
                println("alertaViewModel.notificacion.value: ${alertaViewModel.notificacion.value}")
                DesEmpleoScreen(alertaViewModel.notificacion.value)
            }
            Row(modifier = Modifier
                .fillMaxSize()
                .weight(2.0f))
            {
                ButtonAccept(
                    text = if (alertaViewModel.notificacion.value.botonAlerta == "") "Aceptar" else alertaViewModel.notificacion.value.botonAlerta,
                    onClick = {
                        alertaViewModel.confirmarBoton()
                    }
                )
            }
        }*/
    } else if (alertaViewModel.mostrarPDF.value){
        //mostrarPDF(alertaViewModel.notificacion.value.linkExterno)
        PdfViewer(alertaViewModel.notificacion.value.linkExterno)
    }
    else
    {

        Row(modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth())
        {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(alerts) {
                    AlertaItem(it)
                }
            }
        }
    }
}



@Composable
fun mostrarPDF(mUrl: String) {

    val urlLink = mUrl //+ AppHogaresAplication.accessToken
    println("mUrl: $mUrl")
    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://docs.google.com/gview?embedded=true&url=$urlLink")
        }
    }, update = {
        it.loadUrl("https://docs.google.com/gview?embedded=true&url=$urlLink")
    })
}

@Composable
fun PdfViewer(pdfUrl: String) {
    var urlLink = pdfUrl //+ AppHogaresAplication.accessToken

    println("urlLink: $urlLink")
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl("https://docs.google.com/gview?embedded=true&url=$urlLink")
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}