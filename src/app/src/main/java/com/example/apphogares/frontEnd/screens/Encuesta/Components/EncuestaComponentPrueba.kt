package com.example.apphogares.frontEnd.screens.Encuesta.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo

@Composable
fun EncuestaComponentPrueba(encuesta: Encuesta) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally ) {
        Row(modifier = Modifier
            .padding(30.dp, 10.dp, 10.dp, 30.dp)
            .background(Color.Red),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = encuesta.pregunta,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(30.dp, 10.dp, 10.dp, 30.dp),
                color = Color.Yellow
            )
        }
        Spacer(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp))
        Row(modifier = Modifier
            .padding(30.dp, 10.dp, 10.dp, 30.dp)
            .background(Color.Blue),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Button(
                modifier = Modifier
                    .background(
                        color = BackgroundBottomInTo,
                        shape = RoundedCornerShape(50.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                enabled = true,
                onClick = {
                    AppHogaresAplication.encuestas.filterIndexed { index, survey ->
                        survey.id == encuesta.id
                    }.forEach() {
                        it.respuestaSeleccionada = "Respondida"
                        it.estado = EstadoEncuesta.RESPONDIDA
                    }
                    AppHogaresAplication.funNav(RoutesNav.encuestaCategoriaScreen.route)
                }
            ){
                Text(
                    text =  "ACEPTAR" ,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}