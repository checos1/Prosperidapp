package com.example.apphogares.frontEnd.screens.PQRs.Components.RespuestaPQR

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.apphogares.R
import com.example.apphogares.frontEnd.RoutesNav


import com.example.apphogares.frontEnd.theme.BackGroundRuta
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo

import com.example.apphogares.frontEnd.theme.White
import androidx.compose.runtime.*

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication.Companion.funNav
import com.example.apphogares.backEnd.core.models.PQRs.PQR
import com.example.apphogares.frontEnd.screens.PQRs.PQRViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus

@Composable
fun DetailsResponsePQR(item: PQR, PQRiewModel: PQRViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Estado:", fontSize = 14.sp, modifier = Modifier.padding(top=5.dp), color = White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(PQRiewModel.GetStatusColor(item.estado)),
                contentAlignment = Alignment.CenterStart


            ) {
                Text(item.estado.uppercase(), fontSize = 14.sp, color = White, modifier = Modifier.padding(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Fecha:", fontSize = 14.sp, modifier = Modifier.padding(top=5.dp), color = White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(item.fechaRadicacion, fontSize = 14.sp, color = White, modifier = Modifier.padding(5.dp))
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Radicado:", fontSize = 14.sp, modifier = Modifier.padding(top=5.dp), color = White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(item.radicado.uppercase(), fontSize = 14.sp, color = White, modifier = Modifier.padding(5.dp))
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text("Cordial saludo, \n\nSe da respuesta a solicitud, se adjunta a respuesta archivo con la información detallada esperamos ser de ayuda y en caso que lo requiera lo invitamos a visitar nuestro portal web. http://prosperidadsocial.gov.co", color = White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Camera Icon and text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.padding(start = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    // Files Icon and text
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { /* Handle file picker click */ }
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.file_arrow_up_alt_svgrepo_com),
                            contentDescription = "Archivos",
                            modifier = Modifier.size(48.dp),
                            tint = White
                        )
                        Text("Archivos", color = White)
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Send Button
                    Button(
                        onClick = {
                            funNav(RoutesNav.InformacionPQRScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = White),
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(BackGroundTopLoginPlus, shape = RoundedCornerShape(50.dp)),
                    ) {
                        Text(text = "VER PETICIÓN", style = MaterialTheme.typography.titleSmall, color = BackGroundRuta)
                    }
                }
            }
        }
    }

}

