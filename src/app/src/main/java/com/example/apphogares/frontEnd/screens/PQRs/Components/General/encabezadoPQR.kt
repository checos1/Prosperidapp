package com.example.apphogares.frontEnd.screens.PQRs.Components.General

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.frontEnd.theme.BackGroundRuta
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.White
import com.example.apphogares.frontEnd.screens.Components.layouts.nonScaledSp
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun encabezadoPQR(modulo: String, text: String)  {

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(60.dp)
            .background(BackGroundTopLogin)
    )
    {
        if (modulo != "") {
            Column(
                modifier = Modifier.weight(2.5F).height(60.dp).padding(15.dp, 5.dp, 5.dp, 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = modulo, color = Gray, fontSize = 16.nonScaledSp,
                    fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
            }
        }

        Column(
            modifier = Modifier.weight(8.0f).height(60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.padding(15.dp, 5.dp, 5.dp, 5.dp))
            {
                Text(text = text, color = Gray, fontSize = 15.nonScaledSp)
            }
        }
    }
}

@Preview
@Composable
fun encabezadoPQRPreview() {
    encabezadoPQR("PQRSDF", "Peticiones, Quejas, Reclamos, Sugerencias, Denuncias y Felicitaciones")
}