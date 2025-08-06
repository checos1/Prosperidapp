package com.example.apphogares.frontEnd.screens.oferta.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.backEnd.core.models.hogarMain.Oferta
import com.example.apphogares.frontEnd.screens.Components.layouts.DescriptionDetail
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.White


@Composable
fun DescripcionOfertaScreen( oferta: Oferta) {

    DescriptionDetail(backgroundColorDetailBox = BackGroundTopLogin) {
        LazyColumn {
            item {

                    Text(
                        text = oferta.nombreLogro,
                        fontSize = 25.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = "Institución : ${oferta.nombreOferente}",
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = "Ciudad : ${oferta.nombreMunicipio}",
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = "Fechas : ${oferta.fechaInicio} y  ${oferta.fechaFin}",
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = "Dias de atención : ${oferta.diasAtencion}",
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = "Horario : ${oferta.horarioAtencion}",
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = "Dirección : ${oferta.direccion}",
                        color = Color.Black,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = oferta.descripcionOferta,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(10.dp)

                    )
                }
            }
        }
}