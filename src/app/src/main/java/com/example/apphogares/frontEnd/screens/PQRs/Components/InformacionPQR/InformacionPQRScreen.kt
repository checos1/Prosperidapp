package com.example.apphogares.frontEnd.screens.PQRs.Components.InformacionPQR

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.models.PQRs.PQR
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.encabezadoPQR
import com.example.apphogares.frontEnd.screens.PQRs.PQRViewModel
import com.example.apphogares.frontEnd.theme.BackGroundRuta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin

@Composable
fun InformacionPQRScreen(PQRiewModel: PQRViewModel = hiltViewModel()) {
    val item =  AppHogaresAplication.selectedPQR
    println("InformacionPQRScreen - item: $item")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGroundTopLogin),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        encabezadoPQR("PQRSDF", "Información de petición")
        Divider(modifier = Modifier.padding(0.dp).background(BackGroundTopLogin), color = Color.Gray, thickness = 1.dp)

        DetailsPQR(item)


    }
}

