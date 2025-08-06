package com.example.apphogares.frontEnd.screens.Juegos.EmparejarPalabras

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.apphogares.backEnd.core.models.juegos.Card_2
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.BoxAlerts.BoxAlert
import com.example.apphogares.frontEnd.screens.Components.Video.AlegriaScreen
import com.example.apphogares.frontEnd.screens.Components.Video.CelebracionScreen
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAcceptJuegos
import com.example.apphogares.frontEnd.screens.ruta.Components.BarraScrollGamificacion
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.VerdeCorrecto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmparejarPalabrasScreen(funNav: (String) -> Unit, catCode: String, rutaCode: String, tematicaCode: String, emparejarViewModel: EmparejarPalabrasViewModel = hiltViewModel()) {
    //val emparejarViewModel: EmparejarPalabrasViewModel = hitViewModel ()
    var selectedOptions = mutableListOf<Card_2>()
    var selectedCard by remember { mutableStateOf<Card_2?>(null) }
    var selectedCard_2 by remember { mutableStateOf<Card_2?>(null) }

    var matchedPairs by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }

    var renderGamification =  remember { mutableStateOf(0) }
    val audio  = Audio()
    val utilities = utilities()
    emparejarViewModel.selectedStates.addAll(List(emparejarViewModel.cards.size) { false })

    if (emparejarViewModel.msgError.value != "") {
        ErrorScreen(messageError = emparejarViewModel.msgError.value)
    }

    if (emparejarViewModel.rutaCompletada.value) {
        //audio.startAudioMayo("felicitacionescontinuemos", 3000)
        CelebracionScreen(modifier = Modifier) {
            funNav(RoutesNav.rutaScreen.route)
        }

    }

    if (emparejarViewModel.activityCompleted.value){
        audio.startAudioMayo("felicitaciones", 2000)
        AlegriaScreen(modifier = Modifier) {
            utilities.cambiarActividad(
                AppHogaresAplication.listaActividadesTematicaEnCurso.get(
                    AppHogaresAplication.indexActividadEnCurso).tipo, funNav, catCode,rutaCode, tematicaCode)
        }

    }

    Box(modifier = Modifier.background(BackGroundTopLogin).fillMaxSize()) {
        if (emparejarViewModel.isCorrect.value)
        {
            Box(
                modifier = Modifier.zIndex(1f).fillMaxSize()
                    .offset {
                        IntOffset(0, 0)
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                if(emparejarViewModel.contador.value != emparejarViewModel.lista.size/2){
                    emparejarViewModel.playAudio((1..2).random())
                    AnimatedVisibility(visible = emparejarViewModel.isCorrect.value) {
                        BoxAlert(" !Excelente! ${emparejarViewModel.contador.value}/${emparejarViewModel.lista.size/2}",BackGroundTopLoginPlus,R.drawable.check,"EmparejarPalabrasScreen")
                    }
                }
            }
        }

        if (!emparejarViewModel.isCorrect.value)
        {
            if (emparejarViewModel.contadorSelected.value >= 2)
            {
                Box(
                    modifier = Modifier.zIndex(1f).fillMaxSize()
                        .offset {
                            IntOffset(0, 0)
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    emparejarViewModel.playAudio(3)
                    AnimatedVisibility(visible = !emparejarViewModel.isCorrect.value) {
                        BoxAlert(" !Vuelve a intentarlo! ${emparejarViewModel.contador.value}/${emparejarViewModel.lista.size/2}",Gray,R.drawable.reintentar,"EmparejarPalabrasScreen")
                    }
                }
            }
        }

        Box(
            modifier = Modifier.zIndex(2f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //firts Row gamificacion
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
                ) {
                    // First column
                    Column() {
                        Row(modifier = Modifier.weight(1.5f)) {
                            Text(
                                text = "Relaciona las palabras que corresponden.",
                                modifier = Modifier.padding(5.dp),
                                fontSize = 19.sp,
                                textAlign = TextAlign.Start,
                                color = Color.Black
                            )
                        }
                        Row(modifier = Modifier
                            .weight(9f)
                            .padding(5.dp, 0.dp, 10.dp, 0.dp)){
                                Column( modifier = Modifier.fillMaxSize()) {

                                    LazyVerticalGrid(
                                        modifier = Modifier
                                            .padding(10.dp, top = 30.dp)
                                            .fillMaxWidth(),
                                        state = rememberLazyGridState(),
                                        columns = GridCells.Fixed(2),
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                    ) {
                                        itemsIndexed(emparejarViewModel.cards) { index, buttonItem ->
                                            Button(
                                                onClick = {
                                                    emparejarViewModel.selectedStates[index] =
                                                        !emparejarViewModel.selectedStates[index];
                                                    ///Implementar
                                                    //println("Comprobar-->0 ${emparejarViewModel.selectedStates[index]} -- ${selectedCard}")
                                                    if (emparejarViewModel.selectedStates[index] && selectedCard == null) // primer Click
                                                    {
                                                        emparejarViewModel.contadorSelected.value++
                                                        selectedCard = buttonItem
                                                        matchedPairs++
                                                        //println("Comprobar-->1 ${emparejarViewModel.selectedStates[index]} -- ${selectedCard}")

                                                    }
                                                    else if (!emparejarViewModel.selectedStates[index] && selectedCard != null) //cuando oprime la misma carta
                                                    {
                                                        selectedCard = null
                                                        matchedPairs++
                                                    }
                                                    else if (emparejarViewModel.selectedStates[index] && selectedCard_2 == null)  //selecciona la segunda carta
                                                    {
                                                        emparejarViewModel.contadorSelected.value++
                                                        selectedCard_2 = buttonItem
                                                        matchedPairs++
                                                    }

                                                    if (selectedCard != null && selectedCard_2 != null &&
                                                        selectedCard!!.id == selectedCard_2!!.id
                                                    ){
                                                        selectedOptions =
                                                            emparejarViewModel.cards.filterIndexed { index, _ -> emparejarViewModel.selectedStates[index] } as MutableList<Card_2>
                                                        //println("Comprobar--> options ${selectedOptions[0]} si${selectedOptions.size}")

                                                        if(selectedOptions.size >= 2)
                                                        {
                                                            val areEqual =
                                                                selectedOptions[1].id == selectedOptions[0].id

                                                            if (areEqual) {
                                                                // Las opciones seleccionadas son iguales
                                                                emparejarViewModel.isCorrect.value = true
                                                                //deshabilitar el boton
                                                                emparejarViewModel.cards.forEachIndexed { index, card2 ->
                                                                    card2.isMatched =
                                                                        if (card2.isMatched) true else card2.id == selectedOptions[0].id
                                                                    //emparejarViewModel.selectedStates = mutableStateListOf(false)
                                                                }
                                                            }
                                                        }
                                                        isLoading = true
                                                        matchedPairs++
                                                    }
                                                    else if (selectedCard != null && selectedCard_2 != null &&
                                                        selectedCard!!.id != selectedCard_2!!.id
                                                    ){
                                                        isLoading = true
                                                        matchedPairs++
                                                    }

                                                   // println("Comprobar-->2 ${selectedCard} -- ${selectedCard_2}")
                                                },
                                                modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 2.dp),
                                                border = BorderStroke(
                                                    width = 1.dp,
                                                    color = if (!buttonItem.isMatched) Gray else Color.Transparent
                                                ),
                                                colors = ButtonDefaults.buttonColors(if (emparejarViewModel.selectedStates[index]) BackGroundTopLoginPlus else Color.Transparent),
                                                enabled = !buttonItem.isMatched
                                            ) {
                                                if(buttonItem.word.length >= 15)
                                                {
                                                    Text(
                                                        text = buttonItem.word,
                                                        fontStyle = FontStyle(R.font.roboto),
                                                        fontSize = 13.sp,
                                                        textAlign = TextAlign.Start,
                                                        maxLines = 1,
                                                        color = if (emparejarViewModel.selectedStates[index]) Gray else Color.Black
                                                    )
                                                }
                                                else
                                                {
                                                    Text(
                                                        text = buttonItem.word,
                                                        fontStyle = FontStyle(R.font.roboto),
                                                        fontSize = 15.sp,
                                                        color = if (emparejarViewModel.selectedStates[index]) Gray else Color.Black
                                                    )
                                                }
                                            }

                                        }
                                    }
                            }
                        }
                    }
                }
                // three Row botones
                Row(modifier = Modifier
                    .weight(1.5f)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp)
                ) {
                    if (emparejarViewModel.isCorrect.value == false)
                    {

                        if (emparejarViewModel.contadorSelected.value >= 2)
                        {
                            //quitar monedas
                            LaunchedEffect(Unit){
                                emparejarViewModel.viewModelScope.launch(Dispatchers.IO) {

                                    emparejarViewModel.navegacionApp.QuitarMonedas("EmparejarPalabras",renderGamification)
                                }
                            }

                            //MensajeReintentar(contador = " !Vuelve a intentarlo! ${emparejarViewModel.contador.value}/${emparejarViewModel.lista.size}")
                            // Simula un tiempo de espera de 2 segundos
                            LaunchedEffect(key1 = isLoading,) {
                                //println("Card--> 1.INCORRECTO ${isLoading}")
                                if (isLoading) {
                                    delay(1500)
                                    isLoading = false
                                }
                                //println("Card--> 1.1 INCORRECTO")
                                matchedPairs=0
                                //isLoading = true
                                selectedCard = null
                                selectedCard_2 = null
                                selectedOptions.clear()
                                emparejarViewModel.contadorSelected.value = 0
                                emparejarViewModel.selectedStates = mutableStateListOf(false)
                            }

                        }
                    }
                    if (emparejarViewModel.isCorrect.value)
                    {
                       // println("-------------- Continuar if 1")
                        //Add monedas
                        LaunchedEffect(Unit){
                            emparejarViewModel.viewModelScope.launch(Dispatchers.IO) {

                                //withContext(Dispatchers.Main){
                                //    audio.startAudioFileFromPositionGamificacion((1..2).random())
                                //}
                                emparejarViewModel.navegacionApp.AdicionarMonedas("EmparejarPalabras",renderGamification)
                            }
                        }
                        if (emparejarViewModel.contador.value == emparejarViewModel.lista.size/2)
                        {

                            //println("-------------- Continuar if 2")
/*                            LaunchedEffect(Unit){
                                emparejarViewModel.viewModelScope.launch(Dispatchers.IO) {
                                    withContext(Dispatchers.Main){
                                        Audio().startAudioFileFromPositionGamificacion((1..2).random())
                                        delay(1200)
                                    }
                                    //emparejarViewModel.navegacionApp.AdicionarMonedas("EmparejarPalabras",renderGamification)
                                }
                            }*/
                            ButtonAcceptJuegos(text = "Continuar",
                                onClick = {
                                    AppHogaresAplication.indexActividadEnCurso++
                                    emparejarViewModel.ActualizaActividaTematica(tematicaCode, AppHogaresAplication.indexActividadEnCurso)
                                    println("EmparejarPalabrasScreen-->ActualizaActividaTematica")
                                    if (AppHogaresAplication.indexActividadEnCurso < 2){
                                        emparejarViewModel.activityCompleted.value = true
                                    //    utilities.cambiarActividad(AppHogaresAplication.listaActividadesTematicaEnCurso.get(AppHogaresAplication.indexActividadEnCurso).tipo,
                                    //    funNav, catCode, rutaCode, tematicaCode)
                                    }else{
                                        emparejarViewModel.rutaCompletada.value = true
                                    }

                                }, backgroundColor = VerdeCorrecto)
                        }
                        else{
                            //MensajeContinuar(contador = " !Excelente! ${emparejarViewModel.contador.value}/${emparejarViewModel.lista.size}")
                            // Simula un tiempo de espera de 2 segundos
                            LaunchedEffect(key1 = isLoading,) {
                                //println("Card--> 1.INCORRECTO ${isLoading}")
                                if (isLoading) {
                                    delay(1500)
                                    isLoading = false
                                }
                                selectedCard = null
                                selectedCard_2 = null
                                emparejarViewModel.isCorrect.value = false
                                emparejarViewModel.contador.value++
                                selectedOptions.clear()
                                emparejarViewModel.selectedStates = mutableStateListOf(false)
                                emparejarViewModel.contadorSelected.value = 0
                                //Reiniciar opcion
                            }
                        }

                    }
                }


            }
        }
    }


}
