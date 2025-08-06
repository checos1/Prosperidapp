package com.example.apphogares.frontEnd.screens.Components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.White

@Composable
fun ButtonAcceptJuegos(
    text: String,
    onClick: () -> Unit,
    estado: MutableState<Boolean> = mutableStateOf(true),
    backgroundColor: Color = BackGroundTopLoginPlus
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background( if(estado.value) backgroundColor else Gray, shape = RoundedCornerShape(50.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = White),
            onClick = onClick
        ){
            Text(text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = if(estado.value) Color.Black else Color.White
            )
        }
    }
}