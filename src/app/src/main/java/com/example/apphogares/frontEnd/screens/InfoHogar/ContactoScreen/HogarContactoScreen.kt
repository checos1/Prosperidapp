package com.example.apphogares.frontEnd.screens.InfoHogar.ContactoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.hogarMain.Hogar
import com.example.apphogares.backEnd.core.models.hogarMain.Integrante
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.alerts.AlertSimple
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.screens.Components.layouts.PopupInput
import com.example.apphogares.frontEnd.screens.Components.titles.BarTopScreen
import com.example.apphogares.frontEnd.screens.Components.titles.TitleScreen
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo

@Composable
fun HogarContactoScreen(
    hogar: Hogar,
    integrante: Integrante,
    hogarContactoViewModel: HogarContactoViewModel = hiltViewModel()
) {

    if (hogarContactoViewModel.msgError.value != "") {
        ErrorScreen(messageError = hogarContactoViewModel.msgError.value)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(color = BackGroundTopLogin)) {
        BarTopScreen(id = R.drawable.icon_home, text = "Mi Hogar")

        TitleScreen(text = "Datos del Hogar")
        Column(modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp)){
            Text(
                "Ciudad: ${hogar.contacto?.municipio}",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Text(
                "Departamento: ${hogar.contacto?.departamento}",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Text(
                "Direccion: ${hogar.contacto!!.ubicacionHogar}",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Text(
                "Teléfono: ${hogar.contacto!!.telefono}",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Text(
                "Celular: ${hogar.contacto!!.telefono}",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Text(
                "Correo: ${hogar.contacto!!.correoElectronico}",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
        }
        Column {
            Text(
                "Datos pendientes",
                style = MaterialTheme.typography.titleLarge,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Text(
                "Números de Contacto:",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 32.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Text(
                "GeLocalización: ${AppHogaresAplication.latitud}, ${AppHogaresAplication.longitud}",
                style = MaterialTheme.typography.titleMedium,
                //fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 32.dp, top = 10.dp, bottom = 10.dp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(10.dp))
            hogarContactoViewModel.numerosDeContacto.value.forEach() {
                Text(
                    it,
                    style = MaterialTheme.typography.titleMedium,
                    //fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 48.dp, top = 10.dp, bottom = 10.dp),
                    color = Color.Black
                )
            }
        }
        ButtonAccept(
            text = "Agregar Número de Contacto",
            onClick = {
                hogarContactoViewModel.isPromptVisible.value = true
            }
        )
        ButtonAccept(
            text = "Cerrar Sesión Hogar",
            onClick = {
                hogarContactoViewModel.cerraSesion.value = true
            }
        )

    }

    if (hogarContactoViewModel.isPromptVisible.value) {
        Dialog(onDismissRequest = { hogarContactoViewModel.isPromptVisible}) {
            PopupInput(
                title = "Digite el número de contacto",
                onInputEntered = { input ->
                    val existeInput = hogarContactoViewModel.numerosDeContacto.value.any { it == input }
                    if (!existeInput) {
                        hogarContactoViewModel.numeroContacto.value = input
                        hogarContactoViewModel.addItemToContactList(hogarContactoViewModel.numeroContacto.value)
                    }
                    hogarContactoViewModel.isPromptVisible.value = false
                },
            ) {
                hogarContactoViewModel.isPromptVisible.value = false
            }
        }
    }

    if (hogarContactoViewModel.cerraSesion.value){

        Dialog(onDismissRequest = { hogarContactoViewModel.cerraSesion.value = false}) {
            AlertSimple(
                title = "Está seguro de cerrar la sesión?",
                textoButonAceptar = "Aceptar",
                onClickCerrar = {
                    hogarContactoViewModel.cerraSesion.value = false
                },
                onClickButonAceptar = {
                    hogarContactoViewModel.cerrarSesionHogar()
                },
                contentDialogo = {

                }
            )
        }

    }
}




