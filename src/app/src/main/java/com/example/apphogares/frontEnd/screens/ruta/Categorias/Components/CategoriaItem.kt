package com.example.apphogares.frontEnd.screens.ruta.Categorias.Components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.apphogares.frontEnd.screens.ruta.Categorias.ItemCategoria
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border


@Composable
fun CategoriaItem(
    modifier: Modifier = Modifier,
    itemCategoria: ItemCategoria,
    onClick: () -> Unit = {}
) {
    val progress = if (itemCategoria.totalTematicas > 0) {
        itemCategoria.tematicasCompletadas / itemCategoria.totalTematicas.toFloat()
    } else 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(18.dp))
            .border(
                BorderStroke(1.4.dp, Color(0xFFCCCCCC)), // Gris claro
                RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 13.dp, end = 16.dp, top = 13.dp, bottom = 13.dp)
        ) {
            AsyncImage(
                model = itemCategoria.imagen,
                contentDescription = itemCategoria.nombre,
                modifier = Modifier
                    .size(140.dp)
                    .padding(end = 11.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = itemCategoria.nombre,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222),
                    maxLines = 2 // Por si el nombre es largo
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${itemCategoria.tematicasCompletadas} de ${itemCategoria.totalTematicas} temáticas",
                    fontSize = 13.sp,
                    color = Color(0xFF888888)
                )
                Spacer(modifier = Modifier.height(7.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    color = Color(0xFF3DBE2B), // Verde
                    trackColor = Color(0xFFE0E0E0)
                )
            }
        }
    }
}

