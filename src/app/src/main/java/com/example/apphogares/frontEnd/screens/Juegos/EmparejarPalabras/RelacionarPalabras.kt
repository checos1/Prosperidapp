package com.example.apphogares.frontEnd.screens.Juegos.EmparejarPalabras

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.backEnd.core.models.juegos.Card_2
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.GrayThree


@Composable
fun TwoColumnLayout(viewmodel: EmparejarPalabrasViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RowLayout(viewmodel.cards,viewmodel.codigo,viewmodel.codigo) // Columna 1
        Divider(
            color = GrayThree,
            modifier = Modifier
                .height(360.dp)
                .width(1.dp)
        )
    }
}

@Composable
fun RowLayout(cards: List<Card_2>, seleccionado: MutableState<Int>, seleccionado_2: MutableState<Int>) {
    var selectedCardIndex by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            cards.forEach() {  card ->

                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 140.dp, height = 40.dp),
                    colors = ButtonDefaults.buttonColors( if (seleccionado.value == card.id) BackGroundTopLogin  else BlueTwo),
                    onClick = {

                        seleccionado.value = if (seleccionado.value == 0) card.id else seleccionado.value
                        seleccionado_2.value = if(seleccionado.value == 0) 0 else card.id
                    }
                ) {
                    Text(text = card.word, fontSize = 17.sp, color = if (seleccionado.value == card.id) Gray else BackGroundTopLogin)
                }
            }
    }
}

@Composable
fun CButton(id: Int,text: String, onClick: () -> Unit) {
    var selected by remember { mutableStateOf(false) }
    var selectedCardIndex by remember { mutableStateOf(0) }

    Button(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 140.dp, height = 40.dp),
        colors = ButtonDefaults.buttonColors( if (selected) BackGroundTopLogin  else BlueTwo),
        onClick = onClick
    ) {
        Text(text = text, fontSize = 17.sp, color = if (selected) Gray else BackGroundTopLogin)
    }

}