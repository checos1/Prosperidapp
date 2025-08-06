package com.example.apphogares.frontEnd.screens.oferta

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI
import com.example.apphogares.frontEnd.screens.Components.titles.BarTopScreen
import com.example.apphogares.frontEnd.screens.Components.titles.TitleScreen
import com.example.apphogares.frontEnd.screens.oferta.Components.DescripcionOfertaScreen
import com.example.apphogares.frontEnd.screens.oferta.Components.mostrarOfertasLogro
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OfertaHogarScreen(infoOfertaViewModel: infoOfertaViewModel = hiltViewModel()) {

    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
    }

        Column(modifier = Modifier
            .background(color = BackGroundTopLogin)
            .fillMaxSize())
        {
            BarTopScreen(id = R.drawable.icon_offer, text = "Mis Ofertas")

            if(infoOfertaViewModel.showOferta.value)
            {
                TitleScreen(text = infoOfertaViewModel.ofertaSeleccionada.value.nombreOferta)
                Row(modifier = Modifier
                    .fillMaxSize()
                    .weight(9.0f))
                {
                    DescripcionOfertaScreen(infoOfertaViewModel.ofertaSeleccionada.value )
                }
                Row(modifier = Modifier
                    .fillMaxSize()
                    .weight(1.5f))
                {
                    ButtonAccept(
                        text = "Estoy interesado",

                        onClick = {
                            infoOfertaViewModel.ActualizarEstoyInteresado(infoOfertaViewModel.ofertaSeleccionada.value.idOferta, infoOfertaViewModel.ofertaSeleccionada.value.idHogar)
                            infoOfertaViewModel.showDialog.value = true
                        }
                    )
                }

            }else {
                TitleScreen(text = "Listado de ofertas")
                if(infoOfertaViewModel.ofertaInfoHogar.value.listaOfertas.isNullOrEmpty())
                {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(modifier= Modifier.padding(20.dp), text = "Por el momento no tienes ofertas disponibles para tu hogar!.", color = Color.Black)
                }
                else {
                    mostrarOfertasLogro(infoOfertaViewModel.ofertaInfoHogar.value.listaOfertas!!,
                        onSelectOferta = {
                            infoOfertaViewModel.showOferta.value = true
                            infoOfertaViewModel.setOption(Opciones.OFERTADESCRIPCION)
                            infoOfertaViewModel.idOferta.value = it
                            infoOfertaViewModel.ofertaSeleccionada.value = infoOfertaViewModel.ofertaInfoHogar.value.listaOfertas!!.first { it.idOferta == infoOfertaViewModel.idOferta.value }
                            infoOfertaViewModel.agregarVisitaHija("OfertaHogar", infoOfertaViewModel.idOferta.value.toString())
                        }
                     )
                }
            }

        }

    if(infoOfertaViewModel.showDialog.value){
        Dialog(onDismissRequest = { infoOfertaViewModel.showDialog.value = false}) {
            CustomDialogUI(openDialogCustom = infoOfertaViewModel.showDialog,
                title = "Confirma tu interés",
                onClick = {
                    infoOfertaViewModel.showDialog.value = false
                    infoOfertaViewModel.showOferta.value = false
                    infoOfertaViewModel.agregarVisitaHija("OfertaHogar", "Estoy Interesado")
                          },
            ){
                Column(modifier = Modifier
                    .fillMaxWidth()
                ){
                    Text(text = "${infoOfertaViewModel.ofertaSeleccionada.value.nombreIntegrante} tu aplicación fue exitosa, es importante que el día de la presentación lleves tu documento de identidad.",
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.CenterHorizontally),
                        //style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )
                }
            }
        }
    }


    if (infoOfertaViewModel.msgError.value != "") {
        ErrorScreen(messageError = infoOfertaViewModel.msgError.value)
    }
}




