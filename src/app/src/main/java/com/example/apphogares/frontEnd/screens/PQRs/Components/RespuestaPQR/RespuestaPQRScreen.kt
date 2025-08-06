package com.example.apphogares.frontEnd.screens.PQRs.Components.RespuestaPQR

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apphogares.backEnd.core.models.PQRs.PQR
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.encabezadoPQR

import com.example.apphogares.frontEnd.theme.BackGroundRuta

@Composable
fun  RespuestaPQRScreen(item: PQR) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGroundRuta),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        encabezadoPQR("PQR", "Información de petición")
        Divider(modifier = Modifier.padding(0.dp).background(BackGroundRuta), color = Color.Gray, thickness = 1.dp)

        DetailsResponsePQR(item)

    }
}

