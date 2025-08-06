package com.example.apphogares.frontEnd.screens.Encuesta.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.screens.Components.layouts.nonScaledSp
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus

@Composable
fun  EncuestaSelecionUnica(
    onDismiss : () -> Unit = { },
    onConfirmButton: (String) -> Unit = { },
    encuesta: Encuesta,
    encuestaRespuestaViewModel: encuestaRespuestaViewModel = hiltViewModel()
){
    if (encuesta == null) {
        return
    }
    var icon = 1
    val respuestas = encuesta.respuestas?.split(";") ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundTopLogin)
    ) {
        Row(modifier = Modifier
            .weight(1.8f, true)
            .padding(30.dp, 0.dp, 30.dp, 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,

            ){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Row(modifier = Modifier.weight(1f, true),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom){
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = encuesta.nombre,
                        textAlign = if(encuesta.pregunta.length > 100)  TextAlign.Justify else TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.weight(1f, true),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom){
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = encuesta.pregunta,
                        textAlign = if (encuesta.pregunta.count() > 60) {TextAlign.Justify} else{TextAlign.Center},
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = if(encuesta.pregunta.length > 60) 19.nonScaledSp else 19.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        //Raya
        Row(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
            Spacer(modifier = Modifier.height(10.dp))

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
                color = Gray
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        //RadioButtons
        Column(modifier = Modifier
            .weight(2f, true)
            .padding(30.dp, 0.dp, 30.dp, 0.dp)
            .align(Alignment.CenterHorizontally)) {
            respuestas.forEach { item ->
                Row(
                    modifier = Modifier
                        .clickable() {
                            encuestaRespuestaViewModel.respuestaSeleccionada.value = item
                        }
                        .fillMaxWidth()
                        .background(
                            BackGroundTopLogin,
                            shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp)
                        )
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = encuestaRespuestaViewModel.respuestaSeleccionada.value == item,
                        onClick = {
                            encuestaRespuestaViewModel.respuestaSeleccionada.value = item
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = BackGroundTopRuta,
                            unselectedColor = Gray,
                            disabledSelectedColor = Gray,
                            disabledUnselectedColor = Gray
                        )
                    )
                    Text(text = item, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(5.dp))

            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        //Boton
        Row(modifier = Modifier
            .weight(1f, true)
            .align(Alignment.CenterHorizontally)) {
            Button(
                modifier = Modifier
                    .width(200.dp)
                    .background(
                        color = BackGroundTopLoginPlus,
                        shape = RoundedCornerShape(50.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                enabled = encuestaRespuestaViewModel.respuestaSeleccionada.value != "",
                onClick = {
                    if (encuestaRespuestaViewModel.respuestaSeleccionada.value != ""){
                        onConfirmButton(encuestaRespuestaViewModel.respuestaSeleccionada.value)
                    }
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

