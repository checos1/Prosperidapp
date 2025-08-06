package com.example.apphogares.frontEnd.screens.Encuesta


import androidx.compose.runtime.Composable

import androidx.hilt.navigation.compose.hiltViewModel

import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaAbierta

import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaInformacion
import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaNPS
import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaSeleccionMultiple
import com.example.apphogares.frontEnd.screens.Encuesta.Components.EncuestaSelecionUnica



@Composable
fun  EncuestaCategoriaScreen(encuestaViewModel: encuestaViewModel = hiltViewModel()){

    if (encuestaViewModel.encuestaFinalizada.value) {
        encuestaViewModel.finalizarEncuesta()
    }

    when (encuestaViewModel.encuesta.value.tipoPregunta) {
        "Unica" -> {
                EncuestaSelecionUnica(onDismiss = {
                }, onConfirmButton = {
                    encuestaViewModel.responderEncuestaCategoria(it, encuestaViewModel.encuesta.value)
                }, encuesta = encuestaViewModel.encuesta.value)
        }
        "Multiple" -> {
            EncuestaSeleccionMultiple(onDismiss = {
            }, onConfirmButton = {
                encuestaViewModel.responderEncuestaCategoria(it, encuestaViewModel.encuesta.value)
            }, encuesta = encuestaViewModel.encuesta.value)
        }
        "NPS" -> {
            EncuestaNPS(onDismiss = {
            }, onConfirmButton = {
                encuestaViewModel.responderEncuestaCategoria(it, encuestaViewModel.encuesta.value)
            }, encuesta = encuestaViewModel.encuesta.value)
        }
        "Informacion" -> {
            EncuestaInformacion(onDismiss = {
            }, onConfirmButton = {
                encuestaViewModel.responderEncuestaCategoria(it, encuestaViewModel.encuesta.value)
            }, encuesta = encuestaViewModel.encuesta.value)
        }
        "Abierta" -> {
            EncuestaAbierta(onDismiss = {
            }, onConfirmButton = {
                encuestaViewModel.responderEncuestaCategoria(it, encuestaViewModel.encuesta.value)
            }, encuesta = encuestaViewModel.encuesta.value)
        }
    }
}

