package com.example.apphogares.frontEnd.screens.Juegos.MemoriaPalabras


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.CardMemory
import com.example.apphogares.frontEnd.theme.CardMemoryTwo
import com.example.apphogares.frontEnd.theme.Gray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class Card(
    val id: Int,
    val content: String,
    val imageBitmap: ImageBitmap,
    var isMatched: Boolean = false,
    var levantarCard: Boolean = false,

)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemoriaScreen(
    funNav: (String) -> Unit, catCode: String, rutaCode: String, tematicaCode: String,
    memoriaViewModel: MemoriaViewModel = hiltViewModel()) {

    memoriaViewModel.getListaCard(LocalContext.current)
    var selectedCard by remember { mutableStateOf<Card?>(null) }
    var selectedCard_2 by remember { mutableStateOf<Card?>(null) }
    var matchedPairs by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    //val memoriaViewModel: MemoriaViewModel = viewModel()
    var renderGamification =  remember { mutableStateOf(0) }
    val audio = Audio()
    val utilities = utilities()

    if (memoriaViewModel.msgError.value != "") {
        ErrorScreen(messageError = memoriaViewModel.msgError.value)
    }

    if (memoriaViewModel.rutaCompletada.value){
        audio.startAudioMayo("felicitacionescontinuemos", 3000)
        CelebracionScreen(modifier = Modifier) {
            funNav(RoutesNav.rutaScreen.route)
        }
    }

    if (memoriaViewModel.activityCompleted.value){
        audio.startAudioMayo("felicitaciones", 2000)
        AlegriaScreen(modifier = Modifier) {
            utilities.cambiarActividad(
                AppHogaresAplication.listaActividadesTematicaEnCurso.get(
                    AppHogaresAplication.indexActividadEnCurso).tipo, funNav, catCode,rutaCode, tematicaCode)
        }
    }

    Box(modifier = Modifier.background(BackGroundTopLogin).fillMaxSize())
    {
        if (memoriaViewModel.contentVisible.value)
        {
            Box(
                modifier = Modifier.zIndex(1f).fillMaxSize()
                    .offset {
                        IntOffset(0, 0)
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                if (memoriaViewModel.contador.value != memoriaViewModel.cards.size / 2) {
                    AnimatedVisibility(visible = memoriaViewModel.contentVisible.value) {
                        BoxAlert(
                            " !Excelente! ${memoriaViewModel.contador.value}/${memoriaViewModel.cards.size / 2}",
                            BackGroundTopLoginPlus, R.drawable.check, "EmparejarPalabrasScreen"
                        )
                    }
                }
            }
        }

        if (!memoriaViewModel.contentVisible.value)
        {
            if (matchedPairs > 1)
            {
                Box(
                    modifier = Modifier.zIndex(1f).fillMaxSize()
                        .offset {
                            IntOffset(0, 0)
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    if (memoriaViewModel.contador.value != memoriaViewModel.cards.size / 2) {
                        AnimatedVisibility(visible = !memoriaViewModel.contentVisible.value) {
                            BoxAlert(
                                " !Vuelve a intentarlo! ${memoriaViewModel.contador.value}/${memoriaViewModel.cards.size / 2}",
                                Gray, R.drawable.reintentar, "EmparejarPalabrasScreen"
                            )
                        }
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
                        .background(BackGroundTopLoginPlus)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                    ) {

                       BarraScrollGamificacion(
                            modifier = Modifier
                                .weight(1.8f)
                                .background(BackGroundTopLoginPlus),
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
                        Row(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Encuentra la pareja",
                                color = BackGroundTopLogin,
                                modifier = Modifier.padding(5.dp),
                                fontSize = 19.sp, textAlign = TextAlign.Start
                            )
                        }
                        Row(modifier = Modifier
                            .weight(9f)
                            .padding(10.dp, 0.dp, 10.dp, 0.dp)){
                            Column( modifier = Modifier.fillMaxSize()) {


                                // Show the cards in a grid
                                val gridItems = memoriaViewModel.cards.chunked(3)
                                // Second column
                               gridItems.forEach { row ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            row.forEach { card ->

                                                val realColor by animateColorAsState(
                                                    targetValue = if (card.levantarCard) CardMemoryTwo else CardMemory,
                                                    animationSpec = tween(durationMillis = 2000), label = ""
                                                )

                                                Box(
                                                    modifier = Modifier
                                                        .width(105.dp)
                                                        .height(100.dp)
                                                        .padding(10.dp)
                                                        .clickable(enabled = !card.isMatched) {
                                                            //onCardClick()
                                                            card.levantarCard = !card.levantarCard
                                                            if (card.levantarCard && selectedCard == null) {
                                                                card.levantarCard = true
                                                                selectedCard = card
                                                                matchedPairs++
                                                            } else if (!card.levantarCard && selectedCard != null) //cuando oprime la misma carta
                                                            {
                                                                if (card.content == selectedCard!!.content && !card.isMatched) {
                                                                    card.levantarCard = false
                                                                    selectedCard = null
                                                                    matchedPairs = 0
                                                                }

                                                            } else if (card.levantarCard && selectedCard_2 == null) { //selecciona la segunda carta
                                                                card.levantarCard = true
                                                                selectedCard_2 = card
                                                                matchedPairs++
                                                            }

                                                            if (selectedCard != null && selectedCard_2 != null &&
                                                                selectedCard!!.content == selectedCard_2!!.content
                                                            ) {
                                                                memoriaViewModel.cards.forEachIndexed { index, card ->
                                                                    card.isMatched =
                                                                        if (card.content == selectedCard!!.content) true else card.isMatched

                                                                }
                                                                isLoading = true
                                                                memoriaViewModel.contentVisible.value = true
                                                                selectedCard = null
                                                                selectedCard_2 = null
                                                                matchedPairs++
                                                            } else if (selectedCard != null && selectedCard_2 != null &&
                                                                selectedCard!!.content != selectedCard_2!!.content
                                                            ) {
                                                                isLoading = true
                                                                memoriaViewModel.contentVisible.value = false
                                                                matchedPairs++
                                                                // Simula un tiempo de espera de 2 segundos
                                                                coroutineScope.launch {
                                                                    if (isLoading) {
                                                                        delay(1000)
                                                                        isLoading = false
                                                                        card.levantarCard = false
                                                                        matchedPairs++
                                                                    }
                                                                    memoriaViewModel.cards.forEachIndexed { index, card_1 ->
                                                                        card_1.levantarCard =
                                                                            if (card_1.id == selectedCard!!.id) false else card_1.levantarCard

                                                                    }
                                                                    //Reiniciar Opciones
                                                                    selectedCard = null
                                                                    selectedCard_2 = null
                                                                }
                                                            }
                                                        }
                                                        .size(100.dp)
                                                        .background(
                                                            realColor,
                                                            RoundedCornerShape(8.dp)
                                                        )
                                                ) {
                                                    if (card.levantarCard) {
                                                        card.imageBitmap?.let {
                                                            Image(
                                                                bitmap = it, // Replace with the card back image
                                                                contentDescription = "Card",
                                                                modifier = Modifier.fillMaxWidth().fillMaxSize().clip(shape = RoundedCornerShape(10.dp))
                                                            )
                                                        }
                                                    } else {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.memoria), // Replace with the card back image
                                                            contentDescription = "Card Back",
                                                            modifier = Modifier.fillMaxSize()
                                                        )
                                                    }
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
                    if (memoriaViewModel.contentVisible.value == false)
                    {

                        if (matchedPairs > 1)
                        {
                            //quitar monedas
                            LaunchedEffect(Unit){
                                memoriaViewModel.viewModelScope.launch(Dispatchers.IO) {

                                    withContext(Dispatchers.Main){
                                        audio.startAudioFileFromPositionGamificacion(3)
                                        delay(1200)
                                    }
                                    memoriaViewModel.navegacionApp.QuitarMonedas("Memoria",renderGamification)
                                }
                            }

                            // Simula un tiempo de espera de 2 segundos
                            LaunchedEffect(key1 = isLoading,) {
                                //println("Card--> 1.INCORRECTO ${isLoading}")
                                if (isLoading) {
                                    delay(1500)
                                    isLoading = false
                                }
                                isLoading = true
                                memoriaViewModel.contentVisible.value = false
                                matchedPairs=0
                            }
                            //Reiniciar Opciones
                        }
                    }
                    else
                    {
                        //Add monedas
                        LaunchedEffect(Unit){
                            memoriaViewModel.viewModelScope.launch(Dispatchers.IO) {
                                memoriaViewModel.navegacionApp.AdicionarMonedas("Memoria",renderGamification)
                            }
                        }


                        if (memoriaViewModel.contador.value == memoriaViewModel.cards.size/2)
                        {
                            LaunchedEffect(Unit){
                                memoriaViewModel.viewModelScope.launch(Dispatchers.IO) {
                                    withContext(Dispatchers.Main){
                                        audio.startAudioFileFromPositionGamificacion((1..2).random())
                                        delay(1200)
                                    }
                                }
                            }

                            ButtonAcceptJuegos(text = "Continuar",

                                onClick = {
                                    //Reiniciar opcion
                                    AppHogaresAplication.indexActividadEnCurso++
                                    memoriaViewModel.ActualizaActividaTematica(tematicaCode, AppHogaresAplication.indexActividadEnCurso)
                                    if (AppHogaresAplication.indexActividadEnCurso < 2){
                                        memoriaViewModel.activityCompleted.value = true
                                    }else{
                                        memoriaViewModel.rutaCompletada.value = true
                                    }

                                }, backgroundColor = BackGroundTopLoginPlus)
                        }
                        else
                        {
                            // Simula un tiempo de espera de 2 segundos
                            LaunchedEffect(key1 = isLoading,) {
                                //println("Card--> 1.INCORRECTO ${isLoading}")
                                if (isLoading) {
                                    delay(1500)
                                    isLoading = false
                                }
                                memoriaViewModel.contentVisible.value = false
                                selectedCard = null
                                selectedCard_2 = null
                                matchedPairs = 0
                                memoriaViewModel.contador.value++
                                //Reiniciar opcion
                            }
                        }

                    }
                }

            }
        }
    }

    //Fin Box

}

