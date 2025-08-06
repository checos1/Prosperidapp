package com.example.apphogares.frontEnd.screens.PeriodicoVida


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.PeriodicoVida.periodicoVidaList
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.titles.BarTopScreen
import com.example.apphogares.frontEnd.screens.PeriodicoVida.Components.PDFViewerScreen
import com.example.apphogares.frontEnd.screens.PeriodicoVida.Components.PdfViewerScreenWeb
import com.example.apphogares.frontEnd.screens.PeriodicoVida.Components.PeriodicosGrid
import com.example.apphogares.frontEnd.screens.PeriodicoVida.Components.WebBrowser
import com.example.apphogares.frontEnd.screens.alertas.Components.NotificationList
import com.example.apphogares.frontEnd.screens.alertas.Components.TabsCategories
import com.example.apphogares.frontEnd.screens.alertas.alertaViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta




@Composable
fun PeriodicoVidaScreen(periodicoVidaViewModel: periodicoVidaViewModel = hiltViewModel()){

    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.funNav(RoutesNav.gestionaScreen.route)
    }

    if (periodicoVidaViewModel.msgError.value != "") {
        ErrorScreen(messageError = periodicoVidaViewModel.msgError.value)
    }

    if (periodicoVidaViewModel.mostrarPDF.value) {
        WebBrowser(modifier = Modifier.fillMaxSize())
    }else{
        PeriodicosGrid(periodicoVidaList)
    }


}

