package com.example.apphogares.frontEnd.screens.PQRs.Components.General

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.core.models.PQR.BlqInformacionPeticion
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.backEnd.core.models.PQR.PQRs
import com.example.apphogares.backEnd.core.models.PQR.Response
import com.example.apphogares.frontEnd.screens.PQRs.PQRViewModel


import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun ListItemPQRView(item: PQRDocument, PQRViewModel: PQRViewModel = hiltViewModel()) {

    //val pqrRequest =
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp, 0.dp, 10.dp)
                .background(PQRViewModel.GetTipoColor(item.blqInformacionPeticion.asunto))
                .border(0.5.dp, color = Gray)
                .clickable {
                    PQRViewModel.ShowInformacionPQR(item)
                },
    verticalAlignment = Alignment.CenterVertically)
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            //.fillMaxHeight()
            .weight(7f)
            .background(color = Color.White)
            .padding(5.dp, 5.dp, 5.dp, 5.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                 Text(item.blqInformacionPeticion.asunto, fontSize = 14.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(PQRViewModel.GetStatusColor(item.estado))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if(item.consultarPQRResponse.resultado.estado == null) "" else item.consultarPQRResponse.resultado.estado.uppercase(),
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Text(if(item.response.radicado == "") "XX-XXXXXXX'" else item.response.radicado.uppercase() , fontSize = 14.sp, color = Color.Black)
                Text(item.response.fechaRadicacion.split('T')[0], fontSize = 14.sp, color = Color.Black)
            }
        }
        Column(modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .weight(0.2f, true),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center

        ) {

        }
    }
}




@Preview
@Composable
fun ListItemPQRViewPreview() {
    ListItemPQRView(
        item = PQRDocument(
            radicado = "XX-XXXXXXX",
            blqInformacionPeticion = BlqInformacionPeticion(
                asunto = "Petición"
            ),
            response = Response(
                radicado = "XX-XXXXXXX",
                fechaRadicacion = "2023-10-01T00:00:00"
            ),
            estado = "POR ASIGNAR"
        )
    )
}
