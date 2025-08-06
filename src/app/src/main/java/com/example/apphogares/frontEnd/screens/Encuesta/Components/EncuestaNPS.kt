package com.example.apphogares.frontEnd.screens.Encuesta.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.example.apphogares.frontEnd.screens.Components.alerts.AlertSimple
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.lineShape


@Composable
fun  EncuestaNPS (
    onDismiss : () -> Unit = { },
    onConfirmButton: (String) -> Unit = { },
    encuesta: Encuesta
){
    EncuestaNPSScreen(
        onConfirmButton = onConfirmButton,
        encuesta = encuesta
    )
}


@Composable
fun NPSScale(onScoreSelected: (Int) -> Unit, selectedScore: Int) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 10.dp),
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            (1..4).forEach { score ->
                Button(
                    modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp).size(45.dp),
                    onClick = { onScoreSelected(score) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (score == selectedScore) Color.Blue else Color.White
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        "$score",
                        color = if (score == selectedScore) Color.White else Color.Blue,
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            (5..7).forEach { score ->
                Button(
                    modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp).size(45.dp),
                    onClick = { onScoreSelected(score) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (score == selectedScore) Color.Blue else Color.White
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        "$score",
                        color = if (score == selectedScore) Color.White else Color.Blue,
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            (8..9).forEach { score ->
                Button(
                    modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp).size(45.dp),
                    onClick = { onScoreSelected(score) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (score == selectedScore) Color.Blue else Color.White
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        "$score",
                        color = if (score == selectedScore) Color.White else Color.Blue,
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            (10..10).forEach { score ->
                Button(
                    modifier = Modifier.size(50.dp),
                    onClick = { onScoreSelected(score) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (score == selectedScore) Color.Blue else Color.White
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        "$score",
                        color = if (score == selectedScore) Color.White else Color.Blue,
                        fontSize = MaterialTheme.typography.body2.fontSize
                    )
                }
            }
        }
    }
}

@Composable
fun DialogoEncuestaNPS(
    onDismiss : () -> Unit = { },
    onConfirmButton: (String) -> Unit = { },
    encuesta: Encuesta,
    encuestaRespuestaViewModel: encuestaRespuestaViewModel = hiltViewModel()
){
//    var respuestaSeleccionada by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss } )  {
        AlertSimple(
            title = encuesta.pregunta,
            textoButonAceptar = "Aceptar",
            onClickCerrar = {

            },
            onClickButonAceptar = {
                if (encuestaRespuestaViewModel.respuestaSeleccionada.value != ""){
                    onConfirmButton(encuestaRespuestaViewModel.respuestaSeleccionada.value)
                }
            },
            contentDialogo = {
                NPSScale(
                    onScoreSelected = { score ->
                        encuestaRespuestaViewModel.respuestaSeleccionada.value = "$score"
                    },
                    selectedScore = encuestaRespuestaViewModel.respuestaSeleccionada.value.toIntOrNull() ?: 0
                )
            }
        )
    }
}

@Composable
fun EncuestaNPSScreen(
    onConfirmButton: (String) -> Unit = { },
    encuesta: Encuesta,
    encuestaRespuestaViewModel: encuestaRespuestaViewModel = hiltViewModel()
) {
//    var respuestaSeleccionada by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundTopLogin)
    ) {
        //Icono
        //Pregunta
        Row(
            modifier = Modifier
                .weight(1f, true)
                .padding(30.dp, 0.dp, 30.dp, 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = encuesta.pregunta,
                textAlign = TextAlign.Center,
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        //Raya
        Row(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
            Spacer(modifier = Modifier.height(10.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                color = Gray
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        //RadioButtons
        Column(
            modifier = Modifier
                .weight(2.5f, true)
                .padding(20.dp, 0.dp, 20.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            NPSScale(
                onScoreSelected = { score ->
                    encuestaRespuestaViewModel.respuestaSeleccionada.value = "$score"
                },
                selectedScore = encuestaRespuestaViewModel.respuestaSeleccionada.value.toIntOrNull() ?: 0
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        //Boton
        Row(
            modifier = Modifier
                .weight(1f, true)
                .align(Alignment.CenterHorizontally)
        ) {
            androidx.compose.material3.Button(
                modifier = Modifier
                    .width(200.dp)
                    .background(
                        color = BackgroundBottomInTo,
                        shape = RoundedCornerShape(50.dp)
                    ),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                enabled = encuestaRespuestaViewModel.respuestaSeleccionada.value != "",
                onClick = {
                    if (encuestaRespuestaViewModel.respuestaSeleccionada.value != "") {
                        onConfirmButton(encuestaRespuestaViewModel.respuestaSeleccionada.value)
                    }
                }
            ) {
                Text(
                    text = "ACEPTAR",
                    textAlign = TextAlign.Center,
                    style = androidx.compose.material3.MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EncuestaNPSScreen(
        onConfirmButton = {},
        encuesta = Encuesta(
            id = "1",
            pregunta = "¿Cómo calificaría la atención recibida?",
            tipoPregunta = "NPS",
            respuestas = "Muy buena, Buena, Regular, Mala, Muy mala",
            estado = EstadoEncuesta.ABIERTA
        )
    )
}