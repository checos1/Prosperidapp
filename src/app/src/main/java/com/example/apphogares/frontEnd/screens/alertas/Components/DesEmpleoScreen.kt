package com.example.apphogares.frontEnd.screens.alertas.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun DesEmpleoScreen( notificacion: Notificacion) {
    Column(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
        Text(
            //text = notificacion.asunto,
            text = notificacion.mensaje,
            fontSize = 16.sp,
            fontStyle = FontStyle( R.font.roboto),
            color = Gray,
            modifier = Modifier.padding(7.dp),
            textAlign = TextAlign.Justify
        )
    }

}