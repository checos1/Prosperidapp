package com.example.apphogares.frontEnd.screens.ruta.Tematica

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.R
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.models.contenidosRuta.Actividade
import com.example.apphogares.backEnd.core.models.contenidosRuta.Iteraccione
import com.example.apphogares.backEnd.core.models.contenidosRuta.Tematica
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.screens.Components.Video.AlegriaScreen
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.apphogares.frontEnd.theme.lineShape
import com.example.apphogares.frontEnd.RoutesNav
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import com.example.apphogares.frontEnd.screens.Components.layouts.nonScaledSp
import com.example.apphogares.frontEnd.screens.ruta.Components.BarraScrollGamificacion
import com.example.apphogares.frontEnd.theme.BackGroundBottomRuta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.Gray
import kotlinx.coroutines.delay


@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistorietaScreen(
    screenWidthDp: Int,
    screenHeightDp: Int,
    funNav: (String) -> Unit,
    code: String,
    context: Context,
    viewModel: historietaViewModel = hiltViewModel()
) {
    //val viewModel = hiltViewModel<MainViewModel>().loginViewModel
    val data = AppHogaresAplication.contenidoCMS

    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
    }

    var flagBreak = false
    println("historietaScreen --> Hitorieta")

    for(cat in data.Categorias.orEmpty()){
        for(ruta in cat.rutas){
            for(tematica in ruta.tematicas){
                if(tematica.codigo.toString() == code.toString()){
                    //TODO: Validar que exista los audios
                    AppHogaresAplication.listaActividadesTematicaEnCurso = tematica.actividades
                        BuildHistorieta(tematica.iteracciones, tematica.actividades, screenWidthDp, screenHeightDp, funNav, code, tematica, cat.codigo, ruta.codigo, tematica.codigo, context )
                    flagBreak = true
                }
                if(flagBreak) break
            }
            if(flagBreak) break
        }
        if(flagBreak) break
    }
}


@Composable
fun diaPersonaje(
    dialog: Iteraccione,
    circlePerson: Int,
    pos: Int,
    personajesImg: List<Int>
) {

    val unscaledTextStyle = TextStyle.Default.copy(
        fontSize = 16.sp, // Or your desired fixed font size
    )

    if(pos == 1){
        Row(modifier =
        Modifier
            .fillMaxSize()
        ){
            Column( modifier = Modifier
                .width(circlePerson.dp)
                .height(circlePerson.dp)
            ){
                Row(modifier = Modifier.fillMaxSize()){
                    Image(
                        painter = painterResource(ImPersonId(personajesImg, dialog.personaje)),
                        contentDescription = "Img Aux",
                        modifier = Modifier
                            .fillMaxSize()
                            .align(alignment = Alignment.CenterVertically)
                    )
                }
            }
            Column( modifier = Modifier
                .width((circlePerson * 7).dp)
                .padding(10.dp, 0.dp, 0.dp, 0.dp)){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = lineShape,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 20.dp,
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                        .padding(15.dp)
                ){
                    Text(
                        text = dialog.dialogo.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }
    }else{
        Row(modifier =
        Modifier
            .fillMaxSize()
        ){
            Column( modifier = Modifier
                .width((circlePerson * 6.5).dp)
                .padding(0.dp, 0.dp, 10.dp, 0.dp)){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = lineShape,
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 0.dp,
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                        .padding(15.dp)
                ){
                    Text(
                        text = dialog.dialogo.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
            Column( modifier = Modifier
                .width(circlePerson.dp)
                .height(circlePerson.dp)
            ){
                Row(modifier = Modifier.fillMaxSize()){
                    Image(
                        painter = painterResource(ImPersonId(personajesImg, dialog.personaje)),
                        contentDescription = "Img Aux",
                        modifier = Modifier
                            .fillMaxSize()
                            .align(alignment = Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BuildHistorieta(
    dialogos: List<Iteraccione>,
    actividades: List<Actividade>,
    screenWidthDp: Int,
    screenHeightDp: Int,
    funNav: (String) -> Unit,
    code: String,
    tematica: Tematica,
    catCode: String,
    rutaCode: String,
    tematicaCode: String,
    context: Context,
    viewModel: historietaViewModel = hiltViewModel()
) {
    var personajesImg = listOf(
        R.drawable.mati,
        R.drawable.dani,
        R.drawable.lili,
        R.drawable.prospero,
        R.drawable.pacho,
        R.drawable.tata,
        R.drawable.transito,
        R.drawable.toby
    )

    val scrollState = rememberScrollState()
    val estadoPlay = rememberSaveable{ mutableStateOf(true) }
    val estadoRender = rememberSaveable{ mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var renderGamification =  rememberSaveable { mutableStateOf(0) }
    var posDialogo = rememberSaveable { mutableStateOf(0) }
    val circlePerson = (screenWidthDp - 30)/8
    var isLoading =  rememberSaveable { mutableStateOf(false) }
    val utilities = utilities()
    val audio = Audio()

    AppHogaresAplication.listaActividadesTematicaEnCurso = actividades

    if (viewModel.activityCompleted.value){
        if(!viewModel.playAudioCompleted.value) {
            viewModel.CompletarAudio("felicitaciones", 2000)
        }
        AlegriaScreen(modifier = Modifier) {
            utilities.cambiarActividad(
                AppHogaresAplication.listaActividadesTematicaEnCurso.get(
                    AppHogaresAplication.indexActividadEnCurso).tipo, funNav, catCode,rutaCode, tematicaCode)
        }
    }

    Column(Modifier.fillMaxSize()){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
                .background(BackGroundBottomRuta)
        ){
            Column(
                Modifier
                    .fillMaxSize()
            ) {

/*                if(renderGamification.value >= 0){
                    BarraScrollGamificacion(
                        modifier = Modifier
                            .weight(1.8f)
                            .background(BackGroundTopLogin),
                        visibleBarra = false
                    )
                }*/

            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)
        println("HistorietaScreen -> Estamos por entrar")

        var posIni = 0
        var posFinal = 0

        if(estadoPlay.value && estadoRender.value && posDialogo.value < dialogos.size){

            estadoRender.value = false
            estadoPlay.value = false
            var minutosIni:Any
            var minutosFin:Any

            //Retirar este Si cuando se modifique el CMS y se agregue los campos Audio_inicio y Audio_Fin
            minutosIni = dialogos.get(posDialogo.value).audio_inicio.split(":");
            minutosFin = dialogos.get(posDialogo.value).audio_fin.split(":");

            posIni = ((minutosIni[0].toInt()) * 60) + minutosIni[1].toInt()
            posFinal = ((minutosFin[0].toInt()) * 60) + minutosFin[1].toInt()

            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).let {
                val fileName = tematica.audio.substringAfterLast("/")
                val audioFile = File(it, fileName)
                if (audioFile.exists()) {
                    AppHogaresAplication.audio = Audio()
                    AppHogaresAplication.audio.startAudioFileFromPosition(
                        audioFile,
                        posIni,
                        posFinal,
                        estadoPlay
                    )
                }
            }
            posDialogo.value = posDialogo.value + 1;

        }

        //Llamamos a la funcion que cambia de actividad en la pantalla

        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(9f)
            .background(BackGroundTopLogin)
        )
        {
            Column(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .verticalScroll(scrollState)
            ) {

                Text(
                    text = tematica.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 10.dp, 0.dp),
                    textAlign = TextAlign.End,
                    color = Color.Black
                )

                Spacer(Modifier.height(30.dp))

                dialogos.forEachIndexed { index, dialogo ->
                    estadoRender.value = false
                    //println("HistorietaScreen -> playIndex ${index}, posDialogo ${posDialogo.value}")
                    if(index < posDialogo.value || index == 0){

                        if (dialogos.get(index).tipo == "ComponentHerramientasDialogo") {

                            if (dialogos.get(index).personaje == "narrador") diaNarrador( dialogos.get(index), viewModel.objIteration, index)
                            else diaPersonaje( dialogos.get(index),circlePerson,index%2, personajesImg)

                            if(index+1 < posDialogo.value) Spacer(Modifier.height(30.dp))
                            else Spacer(Modifier.height((screenHeightDp/3).dp))

                        }
                        else {
                            diaPregunta(dialogos.get(index), viewModel.objIteration, index, estadoRender, renderGamification, estadoPlay)
                            if(index+1 >= posDialogo.value) Spacer(Modifier.height((screenHeightDp/10).dp))
                        }

                        scope.launch {
                            scrollState.animateScrollTo(scrollState.maxValue)
                            if(estadoRender.value == false && dialogos.get(index).tipo == "ComponentHerramientasDialogo") estadoRender.value = true
                        }
                    }
                    if(posDialogo.value+1 == dialogos.size){
                        isLoading.value = true
                    }
                }
            }
        }

        if (isLoading.value) {
            viewModel.ActualizaActividaTematica(tematicaCode, AppHogaresAplication.indexActividadEnCurso)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(2.0f)
                    .background(BackGroundTopLogin)
            ){

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(
                                    BackGroundTopLoginPlus,
                                    shape = RoundedCornerShape(50.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            ),
                            onClick = {
                                viewModel.activityCompleted.value = true
                            }
                        ) {
                            Text(
                                "Continuar",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                        }
                    }
        }
    }
}


/*
* RELACION: Requiere los campos *palabras y *relacion
* COMPLETE: Requiere el campo *complete
* SOPALETRAS: palabras
* MEMORIA: Requiere campo *tarjetas
* */

@Composable
fun diaNarrador(
    dialog: Iteraccione,
    objIteration: MutableState<List<Float>>,
    index: Int
) {

    Row(){
        Text(
            text = dialog.dialogo.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
        )
    }



}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun diaPregunta(
    itera: Iteraccione,
    objIteration: MutableState<List<Float>>,
    index: Int,
    estadoRender: MutableState<Boolean>,
    renderGamification: MutableState<Int>,
    estadoPlay: MutableState<Boolean>,
    viewModel: historietaViewModel = hiltViewModel()
) {

    val audio = Audio()
    Row(modifier =
    Modifier
        .fillMaxSize()
    ){
        Column(modifier = Modifier.fillMaxSize()){
            Row(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = itera.pregunta,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
            Spacer(Modifier.height(40.dp))
            var numerales = listOf<String>("A","B","C","D","E","F","G")

            itera.respuestas.forEachIndexed {
                    index, resp ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (estadoPlay.value) {
                            //AppHogaresAplication.audio.stopAudio()
                            viewModel.viewModelScope.launch(Dispatchers.IO) {
                                if (resp.verdadera) {
                                    withContext(Dispatchers.Main) {
                                        audio.startAudioFileFromPositionGamificacion((1..2).random())
                                        delay(2000)
                                    }
                                    viewModel.navegacionApp.AdicionarMonedas(
                                        "Historieta",
                                        renderGamification
                                    )
                                    estadoRender.value = true
                                } else {
                                    withContext(Dispatchers.Main) {
                                        audio.startAudioFileFromPositionGamificacion(3)
                                        delay(2500)
                                    }
                                    viewModel.navegacionApp.QuitarMonedas(
                                        "Historieta",
                                        renderGamification
                                    )
                                }

                            }
                        }

                    }){
                    Column(modifier = Modifier
                        .weight(3F)
                        .height(60.dp)) {
                        Row(
                            horizontalArrangement =  Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (estadoPlay.value) BackGroundTopLoginPlus else Gray,
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterVertically),
                                verticalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text =numerales.get(index),
                                    fontSize = 28.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = if (estadoPlay.value) Color.Black else Color.White,
                                )
                            }

                        }
                    }

                    //println("historietaScreen --> longitud palabras ${resp.opcion.length}")
                    val sizeFont = if(resp.opcion.length >= 70) 12.nonScaledSp else 14.nonScaledSp
                    val lineHeigh = if(resp.opcion.length >= 70) 15.nonScaledSp else 20.nonScaledSp

                    Column(Modifier.weight(0.5F)){Spacer(Modifier.height(20.dp))}
                    Column(modifier = Modifier
                        .weight(15F)
                        .height(60.dp)) {
                        Row(modifier = Modifier){
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (estadoPlay.value) BackGroundTopLoginPlus else Gray,
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .fillMaxWidth()
                                .padding(start = 25.dp, end = 25.dp)
                                .align(alignment = Alignment.CenterVertically),
                                verticalArrangement = Arrangement.Center
                            ){
                                Text(text = resp.opcion,
                                    lineHeight =  lineHeigh,
                                    style =  MaterialTheme.typography.titleSmall,
                                    color = if (estadoPlay.value) Color.Black else Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Left,
                                    fontSize = sizeFont
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
            Spacer(Modifier.height(40.dp))
        }
    }

}

fun ImPersonId(personajesImg: List<Int>, personaje: String): Int {
    var idPerson = personajesImg.get(0)
    if(personaje == "mati")  idPerson = personajesImg.get(0)
    if(personaje == "aleja")  idPerson = personajesImg.get(1)
    if(personaje == "lili")  idPerson = personajesImg.get(2)
    if(personaje == "don-prospero")  idPerson = personajesImg.get(3)
    if(personaje == "don-pacho")  idPerson = personajesImg.get(4)
    if(personaje == "la-tata")  idPerson = personajesImg.get(5)
    if(personaje == "transito")  idPerson = personajesImg.get(6)
    if(personaje == "toby")  idPerson = personajesImg.get(7)

    return  idPerson
}


