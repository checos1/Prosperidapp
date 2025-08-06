package com.example.apphogares.frontEnd.screens.Index

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.InitScreen
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.LoadingScreen
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.apphogares.frontEnd.theme.circlePerson4


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IndexScreen(funNav: (String) -> Unit, indexViewModel: IndexViewModel = hiltViewModel()) {

    when (indexViewModel.stateIndex.value) {
        indexState.withoutConectivity -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGroundTopLoginPlus),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorScreen(messageError = "No hay conexión a internet.")
            }
        }
        indexState.loading -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(BackGroundTopLogin),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom) {
                InitScreen() {
                    indexViewModel.endShowMayo.value = true
                    indexViewModel.validarestado()
                }
            }
        }
        indexState.msgError -> {
            ErrorScreen(messageError = indexViewModel.msgError.value)
        }
        indexState.proximoPaso -> {
            if(indexViewModel.nextScreen.value == RoutesNav.rutaScreen.route){
                if (indexViewModel.existeContenidosRuta.value)
                    funNav(indexViewModel.nextScreen.value)
                else
                    indexViewModel.validarContenidosRuta()
            }
            else{
                funNav(indexViewModel.nextScreen.value)
            }
        }
        else -> {}
    }

}