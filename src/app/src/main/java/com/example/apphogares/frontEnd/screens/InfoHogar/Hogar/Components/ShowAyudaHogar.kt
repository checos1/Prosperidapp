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
import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI

@Composable
fun  ShowAyudaHogar(showAyudaHogar: MutableState<Boolean>) {
    Dialog(onDismissRequest = { showAyudaHogar.value = false} ) {
        CustomDialogUI(openDialogCustom =  showAyudaHogar,
            title = "Ayuda – Módulo Mi Hogar",
            onClick = { showAyudaHogar.value = false},
            modifier = Modifier.clip(RoundedCornerShape(0, 0, 10, 10))
        ){
            Column(modifier = Modifier.padding(16.dp))
            {
                Text(
                    text = "En este módulo podrás consultar la información que Prosperidad Social tiene registrada sobre ti y tu núcleo familiar.\n" +
                            "\n" +
                            "Es muy importante que revises la fuente y especialmente, la fecha de corte de los datos.\n" +
                            "La fecha de corte indica el momento en que Prosperidad Social tomó la información desde las bases de datos oficiales. Esto significa que los datos pueden haber cambiado después de esa fecha y no coincidir con tu situación actual.\n" +
                            "\n" +
                            "Si encuentras errores en la información y la fuente es el Sisbén, debes acudir al enlace municipal del Sisbén para solicitar la actualización.\n" +
                            "\n" +
                            "Recuerda que desde este módulo solo puedes actualizar tu número de contacto.",
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal
                )

            }
        }
    }
}