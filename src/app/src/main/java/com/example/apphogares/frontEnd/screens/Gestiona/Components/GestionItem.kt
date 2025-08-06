package com.example.apphogares.frontEnd.screens.Gestiona.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.R
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.White

@Composable
fun GestionItem(
    iconResId: Int, // ID del recurso PNG
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        // Columna para el icono
        Column(
            modifier = Modifier
                .weight(3.0f)
                .fillMaxSize()
                .background(BackGroundTopLoginPlus),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = text,
                //modifier = Modifier.size(256.dp),
                contentScale = ContentScale.Fit,

            )
        }
        // Columna para el texto
        Column(
            modifier = Modifier
                .weight(10.0f)
                .fillMaxHeight()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.padding(10.dp, 5.dp, 5.dp, 10.dp)) {
                Text(
                    text = text,
                    color = White,
                    fontSize = 16.sp
                )
            }
            Row(modifier = Modifier.padding(10.dp, 0.dp, 5.dp, 5.dp)) {
                Text(
                    text = "Gestiona Aquí",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun GestionItemPreview() {
    // Ejemplo de uso con un icono PNG
    // Asegúrate de tener este recurso en tu proyecto
    GestionItem(
        iconResId = R.drawable.icono_gestiona, // Icono PNG
        text = "Peticiones, Quejas y Reclamos",
        onClick = { /* Acción al hacer clic */ }
    )
}