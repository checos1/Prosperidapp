package com.example.apphogares.frontEnd.screens.alertas.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.R
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.apphogares.frontEnd.theme.White
import com.example.apphogares.frontEnd.theme.blueOval

@Composable
fun TabsCategories(
    onSelect: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)  {
            OvalIcon(
                BackGroundTopLogin, R.drawable.icon_spiral, Color.Black
            ) {
                onSelect("empleo")
            }
            Text(text = "Acompañamiento", modifier = Modifier.padding(8.dp), color = Color.Black, fontSize = 13.sp)
        }
        Column(modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            OvalIcon(
                blueOval, R.drawable.icon_spiral, Color.Black
            ) {
                onSelect("interes")
            }
            Text(text = "Interés", modifier = Modifier.padding(8.dp), color = Color.Black, fontSize = 13.sp)
        }
        Column(modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            OvalIcon(
                BlueTwo, R.drawable.icon_spiral, BackGroundTopLogin,
            ) {
                onSelect("prosperidad")
            }
            Text(text = "Prosperidad", modifier = Modifier.padding(8.dp), color = Color.Black, fontSize = 13.sp)
        }
    }
}