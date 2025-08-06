package com.example.apphogares.frontEnd.screens.Juegos.CompletarPalabras

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.R
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.BoxAlerts.BoxAlert
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.Video.AlegriaScreen
import com.example.apphogares.frontEnd.screens.Components.Video.CelebracionScreen
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAcceptJuegos
import com.example.apphogares.frontEnd.screens.ruta.Components.BarraScrollGamificacion
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.VerdeCorrecto
import com.example.apphogares.frontEnd.theme.VerdeCorrectoTwo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CompletarPalabrasScreen(
    funNav: (String) -> Unit,
    catCode: String,
    rutaCode: String,
    tematicaCode: String,
    screenWidthDp: Int,
    screenHeightDp: Int,
    completarViewModel: CompletarPalabrasViewModel = hiltViewModel()
) {
    val audio = Audio()
    val utilities = utilities()
    var renderGamification =  remember { mutableStateOf(0) }

    if (completarViewModel.msgError.value != "") {
        ErrorScreen(messageError = completarViewModel.msgError.value)
    }

    if (completarViewModel.rutaCompletada.value) {
        //audio.startAudioMayo("felicitacionescontinuemos", 3000)
        CelebracionScreen(modifier = Modifier) {
            funNav(RoutesNav.rutaScreen.route)
        }

    }else if (completarViewModel.activityCompleted.value){
        //audio.startAudioMayo("felicitaciones", 2000)
        AlegriaScreen(modifier = Modifier) {
            utilities.cambiarActividad(
                AppHogaresAplication.listaActividadesTematicaEnCurso.get(
                    AppHogaresAplication.indexActividadEnCurso).tipo, funNav, catCode,rutaCode, tematicaCode)
        }
    } else{
        Box(modifier = Modifier
            .background(BackGroundTopLogin)
            .fillMaxSize()){

            if (completarViewModel.isCorrect.value)
            {
                Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .fillMaxSize()
                        .offset {
                            IntOffset(0, 0)
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AnimatedVisibility(visible = completarViewModel.isCorrect.value) {
                        completarViewModel.playAudio((1..2).random())
                        BoxAlert(
                            "!Excelente! ${completarViewModel.contador.value}/${completarViewModel.answers.size}",
                            VerdeCorrectoTwo,
                            R.drawable.check,
                            "CompletarPalabrasScreen"
                        )
                    }
                }
            }

            if (!completarViewModel.isCorrect.value)
            {
                if (completarViewModel.contadorSelected.value >= 1) {
                    //println("completarViewModel ENTRA-->")
                    Box(
                        modifier = Modifier
                            .zIndex(1f)
                            .fillMaxSize()
                            .offset {
                                IntOffset(0, 0)
                            },
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AnimatedVisibility(visible = !completarViewModel.isCorrect.value) {
                            completarViewModel.playAudio(3)
                            BoxAlert(
                                "!Vuelve a intentarlo! ${completarViewModel.contador.value}/${completarViewModel.answers.size}",
                                Gray,
                                R.drawable.reintentar,
                                "CompletarPalabrasScreen"
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.zIndex(2f)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(BackGroundTopLogin)
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                        ) {

                            BarraScrollGamificacion(
                                modifier = Modifier
                                    .weight(1.8f)
                                    .background(BackGroundTopLogin),
                                visibleBarra = false
                            )
                        }
                    }

                    Divider(color = Gray)
                    //second Row juego
                    Row(modifier = Modifier
                        .weight(7.5f)
                        .fillMaxSize()
                    ){
                        // First column
                        Column(){
                            Row(modifier = Modifier.weight(1.2f)){
                                Text(
                                    text = "Completa la frase",
                                    color = Color.Black,
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 19.sp, textAlign = TextAlign.Start
                                )
                            }
                            Row(modifier = Modifier
                                .weight(9f)
                                .padding(5.dp, 0.dp, 10.dp, 0.dp)){
                                Column( modifier = Modifier.fillMaxSize()){
                                    Row(modifier = Modifier
                                        .weight(2f)
                                        .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment =  Alignment.CenterVertically
                                    ){
                                        if(completarViewModel.phrases[completarViewModel.currentPhraseIndex.value].length >= 60)
                                        {
                                            Text(
                                                text = completarViewModel.phrases[completarViewModel.currentPhraseIndex.value],
                                                color = Color.Black,textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontStyle = FontStyle( R.font.roboto), fontSize = 15.sp
                                            )
                                        }
                                        else
                                        {
                                            Text(
                                                text = completarViewModel.phrases[completarViewModel.currentPhraseIndex.value],
                                                color = Color.Black, textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontStyle = FontStyle( R.font.roboto)
                                            )
                                        }
                                    }
                                    Row(modifier = Modifier
                                        .weight(8f)
                                        .fillMaxWidth()){
                                        MessageList(completarViewModel.answers, completarViewModel.answer)
                                    }
                                }
                            }
                        }
                    }
                    // three Row botones
                    Row(modifier = Modifier
                        .weight(1.5f)
                        .padding(16.dp, 0.dp, 16.dp, 0.dp)
                    ){
                        if (!completarViewModel.isCorrect.value) {
                            if (completarViewModel.contadorSelected.value == 0) {
                                ButtonAcceptJuegos(text = "Comprobar", onClick = {
                                    completarViewModel.isCorrect.value = completarViewModel.isAnswerCorrect(
                                        completarViewModel.currentPhraseIndex.value,
                                        completarViewModel.answer.value
                                    )
                                    completarViewModel.contadorSelected.value =
                                        completarViewModel.answer.value.count()
                                })
                            }
                            if (completarViewModel.contadorSelected.value >= 1){

                                //quitar monedas
                                LaunchedEffect(Unit){
                                    completarViewModel.viewModelScope.launch(Dispatchers.IO) {
/*                                    withContext(Dispatchers.Main){
                                        audio.startAudioFileFromPositionGamificacion(3)
                                        delay(1200)
                                    }*/
                                        completarViewModel.navegacionApp.QuitarMonedas("CompletarPalabras",renderGamification)
                                    }
                                }

                                ButtonAcceptJuegos(text = "Reintentar", onClick = {
                                    completarViewModel.answer.value = ""
                                    completarViewModel.isCorrect.value = false
                                    completarViewModel.selected.value = false
                                    completarViewModel.contadorSelected.value = 0
                                })

                            }
                        }
                        if (completarViewModel.isCorrect.value) {

                            LaunchedEffect(Unit){
                                completarViewModel.viewModelScope.launch(Dispatchers.IO) {

                                    withContext(Dispatchers.Main){
                                        //audio.startAudioFileFromPositionGamificacion((1..2).random())
                                    }
                                    completarViewModel.navegacionApp.AdicionarMonedas("CompletarPalabras",renderGamification)
                                }
                            }

                            ButtonAcceptJuegos(text = "Continuar", onClick = {
                                if (completarViewModel.contador.value == completarViewModel.answers.size){
                                    AppHogaresAplication.indexActividadEnCurso++
                                    completarViewModel.ActualizaActividaTematica(tematicaCode, AppHogaresAplication.indexActividadEnCurso)
                                    if (AppHogaresAplication.indexActividadEnCurso < 2){
                                        completarViewModel.activityCompleted.value = true
                                    }else{
                                        completarViewModel.rutaCompletada.value = true
                                    }

                                }else{
                                    completarViewModel.InicializarPaso()
                                }
                            }, backgroundColor = VerdeCorrecto)

                        }
                    }

                }
            }

        }
    }




}


