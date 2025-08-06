package com.example.apphogares.frontEnd.screens.Comunicaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.screens.Comunicaciones.Componentes.BloqueContactoYRedes
import com.example.apphogares.frontEnd.screens.Comunicaciones.Componentes.EncabezadoVideoYouTube
import com.example.apphogares.frontEnd.screens.Comunicaciones.Componentes.TabsInformateAlertas

@Composable
fun ComunicacionesScreen(comunicacionesViewModel: comunicacionesViewModel = hiltViewModel()){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundTopLogin)
    ) {
/*        if (comunicacionesViewModel.selectedTab.value == 0) {

        }else{
            Spacer(modifier = Modifier.height(16.dp))
        }*/
        // Encabezado con video
        //Spacer(modifier = Modifier.height(16.dp))
        EncabezadoVideoYouTube()

        // Bloque de contacto y redes sociales
        BloqueContactoYRedes()
        // Tabs: Infórmate y Alertas
        TabsInformateAlertas()
    }
}