package com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.apphogares.backEnd.core.models.Programa
import com.example.apphogares.backEnd.core.models.hogarMain.Integrante
import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI

@Composable
fun  ShowInformationPrograma(showInformationPrograma: MutableState<Boolean>, programa: Programa) {
    Dialog(onDismissRequest = { showInformationPrograma.value = false} ) {
        CustomDialogUI(openDialogCustom =  showInformationPrograma,
            title = programa.nombre,
            onClick = { showInformationPrograma.value = false},
            modifier = Modifier.clip(RoundedCornerShape(0, 0, 10, 10))
        ){
            Column(modifier = Modifier.padding(16.dp))
            {
                Text(
                    text = "${programa.descripcion}",
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal
                )

            }
        }
    }
}