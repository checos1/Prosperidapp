package com.example.apphogares.frontEnd.screens.InfoHogar.Hogar

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.R
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.screens.Components.titles.BarTopScreen
import com.example.apphogares.frontEnd.screens.InfoHogar.ContactoScreen.HogarContactoScreen
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components.InfoClasificacionHogar
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components.ItemIntegranteHogar
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components.ShowAyudaHogar
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components.ShowInformationPrograma
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components.ShowMemberInformation
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun HogarInfoScreen(infoHogarViewModel: infoHogarViewModel = hiltViewModel()) {

    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
    }

    when (infoHogarViewModel.stateHogar.value) {
        hogarState.Loading -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = BackGroundTopLogin)) {
                BarTopScreen(id = R.drawable.icon_home, text = "Mi Hogar")
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(BackGroundTopLoginPlus)
                    .padding(top = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(modifier = Modifier
                        //                 .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 5.dp)) {
                        Text("Jefe del Hogar",
                            style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("${infoHogarViewModel.jefeHogar.value.nombre} ${infoHogarViewModel.jefeHogar.value.apellidos}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 16.dp)
                            .align(Alignment.CenterVertically)
                            .clickable(
                                onClick = {
                                    infoHogarViewModel.ShowAyudaHogar.value = true
                                }
                            )
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Text("Integrantes del Hogar",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    )
                    {
/*                        InfoClasificacionHogar(
                            hogar = AppHogaresAplication.Infohogar.hogar!!
                        )*/
                        if (AppHogaresAplication.Infohogar.hogar != null) {
                            InfoClasificacionHogar(
                                hogar = AppHogaresAplication.Infohogar.hogar!!
                            )
                        } else {
                            // Aquí podrías mostrar un mensaje de "No hay información del hogar" o simplemente un placeholder.
                            Text(
                                text = "No hay información del hogar para mostrar.",
                                color = Color.Red,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Lista scrollable
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        items(infoHogarViewModel.listaIntegrantesHogar) {
                            ItemIntegranteHogar(
                                "${it.nombre} ${it.apellidos}",
                                onClick = {
                                    infoHogarViewModel.ShowMemberInformation(it.idIntegranteHogar!!)
                                }
                            )
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) } // espacio al final
                    }

                    // Botón fijo
                    ButtonAccept(
                        text = "Información de contacto",
                        onClick = {
                            infoHogarViewModel.ShowContactInformation()
                        }
                    )
                }
            }
        }
        hogarState.ShowContactInformation -> {
            HogarContactoScreen(AppHogaresAplication.Infohogar.hogar!!, infoHogarViewModel.jefeHogar.value)
        }
        hogarState.Error -> {
            ErrorScreen(messageError = infoHogarViewModel.msgError.value)
        }
        else -> {}
    }

    if (infoHogarViewModel.showMemberInformation.value){
/*        val integrante = infoHogarViewModel.listaIntegrantesHogar.first { it.idIntegranteHogar == infoHogarViewModel.idIntegrante.value }
        ShowMemberInformation(
            infoHogarViewModel.showMemberInformation,
            integrante = integrante,
        )*/
        val integrante = infoHogarViewModel.listaIntegrantesHogar.firstOrNull { it.idIntegranteHogar == infoHogarViewModel.idIntegrante.value }
        if (integrante != null) {
            ShowMemberInformation(
                infoHogarViewModel.showMemberInformation,
                integrante = integrante,
            )
        } else {
            // Manejar el caso de error aquí (por ejemplo, mostrar un mensaje)
        }

    }

    if (infoHogarViewModel.showInformationPrograma.value){
        println("Hogar Info Screen programaSeleccionado -> ${infoHogarViewModel.programaSeleccionado.value}")
        println("Hogar Info Screen programas -> ${AppHogaresAplication.contenidoCMS.Programas}")
/*        val programa = AppHogaresAplication.contenidoCMS.Programas!!.first { it.nombre.uppercase() == infoHogarViewModel.programaSeleccionado.value.uppercase() }
        println("Hogar Info Screen programa -> ${programa}")
        ShowInformationPrograma(
            infoHogarViewModel.showInformationPrograma,
            programa = programa,
        )*/
        val programa = AppHogaresAplication.contenidoCMS.Programas!!.firstOrNull { it.nombre.uppercase() == infoHogarViewModel.programaSeleccionado.value.uppercase() }
        if (programa != null) {
            ShowInformationPrograma(
                infoHogarViewModel.showInformationPrograma,
                programa = programa,
            )
        } else {
            // Manejar el caso de error aquí
        }

    }

    if (infoHogarViewModel.ShowAyudaHogar.value){
        ShowAyudaHogar(infoHogarViewModel.ShowAyudaHogar)
    }
}






