package com.example.apphogares.frontEnd.screens.Encuesta.Components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun  EncuestaAbierta(
    onDismiss : () -> Unit = { },
    onConfirmButton: (String) -> Unit = { },
    encuesta: Encuesta,
    encuestaRespuestaViewModel: encuestaRespuestaViewModel = hiltViewModel()
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundTopLogin)
    ) {
        Row(modifier = Modifier
            .weight(1f, true)
            .padding(30.dp, 0.dp, 30.dp, 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Row(modifier = Modifier.weight(1f, true),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom){
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = encuesta.nombre,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.weight(1f, true),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom){
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = encuesta.pregunta,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
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

        Column(modifier = Modifier
            .weight(1f, true)
            .padding(20.dp, 0.dp, 20.dp, 0.dp)
            .align(Alignment.CenterHorizontally)) {
            TextField(
                value = encuestaRespuestaViewModel.respuestaSeleccionada.value,
                onValueChange = { encuestaRespuestaViewModel.respuestaSeleccionada.value = it },
                label = { Text("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // Adjust the height to allow for more lines
                textStyle = TextStyle(fontSize = 16.sp),
                maxLines = 8, // Set max lines or leave it unset for no limit
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default
                )
            )
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