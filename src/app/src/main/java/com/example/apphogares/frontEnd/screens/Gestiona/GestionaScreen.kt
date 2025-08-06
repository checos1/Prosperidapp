package com.example.apphogares.frontEnd.screens.Gestiona

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.RoutesNav
import androidx.compose.foundation.layout.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apphogares.AppHogaresAplication.Companion.funNav
import com.example.apphogares.R
import com.example.apphogares.frontEnd.screens.Gestiona.Components.GestionItem
import com.example.apphogares.frontEnd.theme.BackGroundRuta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin


@Composable
fun  GestionaScreen(gestionaViewModel: GestionaViewModel = hiltViewModel()){


    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.screenActual = RoutesNav.wellcomeScreen.route
        funNav(RoutesNav.encuestaScreen.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGroundTopLogin)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        GestionItem( R.drawable.icono_pqr,"PQRSDF", onClick = {
            funNav(RoutesNav.PQRScreen.route)
        })
/*        GestionItem("CER", "Tus certificados", onClick = {  })*/
        if (gestionaViewModel.encuestasCategoriasAbiertas.value.isNotEmpty()) {
            GestionItem(R.drawable.icono_encuesta,"Responder Encuesta", onClick = {
                funNav(RoutesNav.encuestaCategoriaScreen.route)
            })
        } else {
            GestionItem(R.drawable.icono_encuesta,"Solicita encuesta", onClick = {
                gestionaViewModel.GetEncuestaCategoria()
            })
        }
/*        GestionItem(R.drawable.icono_aboutapp,"Periodico Vida", onClick = {
            funNav(RoutesNav.PeriodicoVidaScreen.route)
        })*/
        GestionItem(R.drawable.icono_aboutapp,"Conoce sobre la APP", onClick = {
            funNav(RoutesNav.wellcomeScreen.route)
        })
    }
}

@Preview
@Composable
fun GestionaScreenPreview() {
    GestionaScreen()
}