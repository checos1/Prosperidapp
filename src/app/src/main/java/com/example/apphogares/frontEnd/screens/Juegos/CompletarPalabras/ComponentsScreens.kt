package com.example.apphogares.frontEnd.screens.Juegos.CompletarPalabras

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.R
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun AnimatedButton(
    palabra: String,
    respuesta: MutableState<String>
) {
    Button(
        onClick = {
            respuesta.value = palabra;
        },
        modifier = Modifier
            .padding(25.dp, 5.dp, 25.dp, 5.dp)
            .fillMaxWidth(),
        border= BorderStroke(width = 1.dp, color = if (respuesta.value == palabra)  BackGroundTopLogin else BlueTwo),
        colors = ButtonDefaults.buttonColors( if (respuesta.value == palabra) BackGroundTopLoginPlus  else Color.Transparent),
    ) {
        if(palabra.length > 15)
            Text(text = palabra, fontStyle = FontStyle( R.font.roboto), fontSize = 14.sp, color = if (respuesta.value == palabra) Gray else Color.Black)
        else{
            Text(text = palabra, fontStyle = FontStyle( R.font.roboto), fontSize = 17.sp, color = if (respuesta.value == palabra) Gray else Color.Black)
        }
    }
}


@Composable
fun MessageList(
    respuestasOpciones: List<String>,
    respuesta: MutableState<String>
) {

    Column(){
        respuestasOpciones.forEach(){  it ->
            Row( modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment =  Alignment.CenterVertically
            ){
                AnimatedButton(it,respuesta)
            }
        }
    }
}