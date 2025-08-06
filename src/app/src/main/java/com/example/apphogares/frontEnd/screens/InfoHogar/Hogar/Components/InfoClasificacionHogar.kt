package com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.hogarMain.Hogar
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray


@Composable
fun InfoClasificacionHogar(hogar: Hogar) {
    if (hogar.clasificacion.isNotEmpty()) {
        val clasificacion = hogar.clasificacion.trim()
        val fontSize = when (clasificacion.length) {
            1 -> 48.sp
            2 -> 36.sp
            else -> 28.sp
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)) // aplica el borde
                .background(Color(0xFF333333))   // fondo oscuro
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Fuente", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(hogar.fuente ?: "", color = Color.White, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Divider(
                    modifier = Modifier
                        .height(65.dp)
                        .width(2.dp)
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .height(70.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Fecha de Corte", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(hogar.fechaCorte, color = Color.White, fontSize = 12.sp)
                }

                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(70.dp)
                        .background(Color(0xFFD32F2F), shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val fontSize = when (hogar.clasificacion.length) {
                        1 -> 48.sp
                        2 -> 36.sp
                        else -> 28.sp
                    }
                    Text(
                        hogar.clasificacion,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize
                    )
                }
            }
        }


    }
}





@Preview
@Composable
fun PreviewInfoClasificacionHogar() {
    InfoClasificacionHogar(
        hogar = Hogar(
            fuente = "Sisben IV",
            clasificacion = "A",
            fechaCorte = "2023-10-01"
        )
    )
}