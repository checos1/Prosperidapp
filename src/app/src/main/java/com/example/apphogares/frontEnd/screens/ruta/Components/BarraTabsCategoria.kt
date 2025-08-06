package com.example.apphogares.frontEnd.screens.ruta.Components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.screens.ruta.rutaViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.apphogares.R
import com.example.apphogares.frontEnd.theme.BordeTab
import com.example.apphogares.frontEnd.theme.VerdeCorrecto
import com.example.apphogares.frontEnd.theme.VerdeCorrectoTwo


@Composable
fun BarraTabsCategoria(
    modifier: Modifier,
    onRutasClick: () -> Unit,
    onInfoClick: () -> Unit,
    //viewModel: rutaViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(VerdeCorrecto)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón "Tus Rutas"
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(18.dp))
                .background(VerdeCorrectoTwo)
                .border(2.dp, BordeTab, RoundedCornerShape(18.dp))
                .clickable { onRutasClick() }
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_rutas),
                    contentDescription = "Tus Rutas",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tús Rutas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
        // Botón "Información"
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(18.dp))
                .background(VerdeCorrectoTwo)
                .border(2.dp, BordeTab, RoundedCornerShape(18.dp))
                .clickable { onInfoClick() }
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Circulo con la "i" minúscula
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF7A7673)), // Gris similar a la imagen, puedes ajustar
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "i",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Información",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BarraTabsCategoriaPreview() {
    BarraTabsCategoria(
        modifier = Modifier.fillMaxWidth(),
        onRutasClick = { /* Acción al hacer clic en "Tus Rutas" */ },
        onInfoClick = { /* Acción al hacer clic en "Información" */ }
    )
}