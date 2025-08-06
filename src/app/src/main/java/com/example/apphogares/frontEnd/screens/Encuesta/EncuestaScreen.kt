package com.example.apphogares.frontEnd.screens.Encuesta


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.screens.Components.LoadingScreen
import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaInformacion
import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaNPS
import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaSeleccionMultiple
import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaSelecionUnica
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin


@Composable
fun  EncuestaScreen(encuestaViewModel: encuestaViewModel = hiltViewModel()){

    if (encuestaViewModel.loading.value) {
        LoadingScreen()
    }

    when (encuestaViewModel.encuesta.value.tipoPregunta) {
        "Unica" -> {
            EncuestaSelecionUnica(onDismiss = {

            }, onConfirmButton = {
                encuestaViewModel.responderEncuesta(it)
            }, encuesta = encuestaViewModel.encuesta.value)
        }
        "Multiple" -> {
            EncuestaSeleccionMultiple(onDismiss = {

            }, onConfirmButton = {
                encuestaViewModel.responderEncuesta(it)
            }, encuesta = encuestaViewModel.encuesta.value)
        }
        "NPS" -> {
            EncuestaNPS(onDismiss = {

            }, onConfirmButton = {
                encuestaViewModel.responderEncuesta(it)
            }, encuesta = encuestaViewModel.encuesta.value)
        }
    }

}