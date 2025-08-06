package com.example.apphogares.frontEnd.screens.alertas.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OvalIcon(
    colorOval: Color,
    iconResId: Int,
    tintIcon: Color,
    seleccionarCategoria: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape) // Clip the Box into a circle
            .background(colorOval)
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null, // You can provide a content description here
            tint = tintIcon, // You can set the tint color
            modifier = Modifier
                .padding(8.dp)
                .clickable { seleccionarCategoria() }
        )
    }
}