package com.example.apphogares.frontEnd.screens.autenticacion.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.*

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.hogarMain.Preguntas
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.LoadingScreen
import com.example.apphogares.frontEnd.screens.autenticacion.LoginViewModel
import com.example.apphogares.frontEnd.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay


@Composable
fun QuestionScreen(funNav: (String) -> Unit) {

    val viewModel = hiltViewModel<MainViewModel>().loginViewModel
    viewModel.setDataHogar()

    var icon = 1
    if(viewModel.posQuestion.value == 0) icon = R.raw.login_usuario
    if(viewModel.posQuestion.value > 0) icon = R.raw.login_buscar

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(icon))

    if(viewModel.isLoggedIn.value){
        viewModel.isLoggedIn.value = false
        funNav(RoutesNav.permissionsDeviceScreen.route)
    }

    Column(Modifier.fillMaxSize().background(BackGroundTopLogin)){
        if(viewModel.posQuestion.value <= 3){
            Row(modifier = Modifier
                .weight(3f, true)
                .padding(10.dp)){
                LottieAnimation(
                    composition = composition,
                    iterations = 10000,
                    contentScale = ContentScale.Fit
                )
            }
            Row(modifier = Modifier
                .weight(6f, true)
                .padding(30.dp, 0.dp, 30.dp, 0.dp),
                horizontalArrangement = Arrangement.Center,
            ){
                    if(viewModel.posQuestion.value == -1){  BlockedTime(funNav, viewModel) }
                    if(viewModel.posQuestion.value >= 0 && viewModel.posQuestion.value < 4){ TabQuestion(viewModel) }
                }
            Row(modifier = Modifier
                .weight(1f, true)
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 30.dp, 0.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                    Column(
                        Modifier
                            .weight(5f)
                            .fillMaxHeight()
                    ){
                        Row(
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp),
                            horizontalArrangement =  Arrangement.Center,
                            verticalAlignment =  Alignment.CenterVertically,
                        ){
                            val modifierCircle = Modifier
                                .size(10.dp)
                                .border(width = 1.dp, color = Gray, shape = CircleShape)
                            val modifierCircleFocus = Modifier
                                .size(15.dp)
                                .clip(shape)
                                .background(BackGroundTopLogin)
                            val modifier0 = if(viewModel.posQuestion.value == 0) modifierCircleFocus else modifierCircle
                            val modifier1 = if(viewModel.posQuestion.value == 1) modifierCircleFocus else modifierCircle
                            val modifier2 = if(viewModel.posQuestion.value == 2) modifierCircleFocus else modifierCircle
                            val modifier3 = if(viewModel.posQuestion.value == 3) modifierCircleFocus else modifierCircle

                            Column(modifier = Modifier.weight(1f)){Box(modifier0)}
                            Column(modifier = Modifier.weight(1f)){Box(modifier1)}
                            Column(modifier = Modifier.weight(1f)){Box(modifier2)}
                            Column(modifier = Modifier.weight(1f)){Box(modifier3)}
                        }
                    }
                    Column(
                        Modifier
                            .weight(5f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.End
                    ){
                        Row(
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp),
                            horizontalArrangement =  Arrangement.Center,
                            verticalAlignment =  Alignment.CenterVertically,

                            ) {
                            Text(text = "< ${viewModel.posQuestion.value + 1}/4 >", color = Gray, style = MaterialTheme.typography.titleMedium )
                        }
                    }
                }
        }
        else{
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                LoadingScreen()
            }
        }
    }


}

@Composable
fun TabQuestion(viewModel: LoginViewModel){

    var Title = "VALIDACIÓN DE IDENTIDAD"
    var Info = "Es necesario validar tu identidad, por favor ayúdanos a responder las siguientes preguntas."
    var expanded by remember { mutableStateOf(false) }

    val pre: List<Preguntas> = viewModel.getQuestions() ?: emptyList()
    val index = viewModel.posQuestion.value - 1

    println("TabQuestion pre: ${Gson().toJson(pre)}")
    println("TabQuestion viewModel.posQuestion.value: ${Gson().toJson(viewModel.posQuestion.value)}")

    Column {
            Text(modifier = Modifier.fillMaxWidth(),
                text = if(viewModel.posQuestion.value > 0 && index in pre.indices) pre.get(viewModel.posQuestion.value-1).pregunta.toString() else Title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
                color = Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            if(viewModel.posQuestion.value == 0){
                Text(modifier = Modifier.fillMaxWidth(),
                    text = Info,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )
            }else {
                println("QuestionScreen -> viewModel.posQuestion.value: ${viewModel.posQuestion.value}")
                println("QuestionScreen -> pree: ${Gson().toJson(pre)}")
                //Abc(pre.get(viewModel.posQuestion.value-1).respuestas.orEmpty(),viewModel.posQuestion.value, viewModel)
                //val index = viewModel.posQuestion.value - 1
                if (index in pre.indices) {
                    Abc(pre[index].respuestas.orEmpty(), viewModel.posQuestion.value, viewModel)
                } else {
                    // Puedes mostrar un mensaje de error, o simplemente nada
                    //Text("No hay preguntas disponibles", color = Color.Red)
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Button(
                modifier = Modifier
                    .width(200.dp)
                    .background(
                        color = BackGroundTopLoginPlus,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    if(viewModel.posQuestion.value >=1 && viewModel.posQuestion.value <= 3){
                        val selData = viewModel.getQuestions().get(viewModel.posQuestion.value-1).seleccion.orEmpty()
                        if(selData != "") {
                            viewModel.posQuestion.value = viewModel.posQuestion.value + 1
                            if(viewModel.posQuestion.value > 3){
                                try{
                                    expanded = true
                                    viewModel.sendValidation()
                                }catch (e:Exception){
                                    println("HistorietaScreen -> Error ${e}")
                                }
                            }
                        }
                        else {
                            expanded = true
                        }
                    }else if(viewModel.posQuestion.value == 0) {
                        viewModel.posQuestion.value = viewModel.posQuestion.value + 1
                    }
                }
            ){
                Text(
                    text =  if(viewModel.posQuestion.value > 0) "SIGUIENTE" else "INICIEMOS" ,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium
                )
            }


            //Valida que los campos sean diligenciados y informa con popUp al usuario
            if(expanded) {

                var errorInfo = "Por favor, selecciona una respuesta"
                var title = "Ups, Error"

                AlertDialog(
                    onDismissRequest = {
                        expanded = false
                    },
                    containerColor = Color.Transparent,
                    modifier =  Modifier.height(220.dp),
                    text = {
                        Column(
                        ){
                            Row(modifier = Modifier.weight(4f).background(Gray, shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)).padding(15.dp)){
                                Column(modifier = Modifier.weight(9f).fillMaxHeight()){
                                    Text(title,
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = White
                                    )
                                }
                                Column(modifier = Modifier.weight(1f).fillMaxHeight()){
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Icon Cerrar",
                                        Modifier.align(Alignment.End).clickable {
                                            expanded = false
                                        },
                                        tint = White,
                                    )
                                }
                            }

                            Row(modifier = Modifier.weight(6f)
                                .background(BackGroundTopLogin, shape = RoundedCornerShape(0.dp, 0.dp,20.dp, 20.dp))
                                .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column(modifier = Modifier.padding(10.dp)){
                                    Text(
                                        errorInfo,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Gray
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                    },
                    dismissButton = {
                    }
                )
            }
        }
}

@Composable
fun Abc(abc: List<String>, posQuestion: Int, viewModel: LoginViewModel){
    var state by remember { mutableStateOf("") }
    Column(
        Modifier
            .selectableGroup()
            .background(BackGroundTopLogin, shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))) {
        abc.forEach { item ->
            Row(
                modifier = Modifier
                    .clickable() {
                        viewModel.setQuestionsSeleccion(posQuestion - 1, item)
                        state = item
                    }
                    .fillMaxWidth()
                    .background(
                        BackGroundTopLogin,
                        shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp)
                    )
                ,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = state == item,
                    onClick = {
                        viewModel.setQuestionsSeleccion(posQuestion-1, item)
                        state = item
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = BackGroundTopLoginPlus,
                        unselectedColor = Gray,
                        disabledSelectedColor = Gray,
                        disabledUnselectedColor = Gray
                    )
                )
                Text(text = item, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun BlockedTime(funNav: (String) -> Unit, viewModel: LoginViewModel) {
    var Info = "Algunas de las preguntas no coinciden con la respuestas, espera 3 minutos e intentalo nuevamente."
    val remainingSeconds = remember { mutableStateOf(179) }

    LaunchedEffect(key1 = remember { mutableStateOf(true) }) {
        while (remainingSeconds.value > 0) {
            delay(1000L)
            remainingSeconds.value -= 1
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (remainingSeconds.value == 0) {
                remainingSeconds.value = 10000
                funNav(RoutesNav.loginScreen.route)
            } else {
                val minuto = (remainingSeconds.value / 60)
                val restSegundos = remainingSeconds.value - (minuto * 60)
                val addCero = if (restSegundos < 10) "0" else ""

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "0${minuto} : ${addCero}${restSegundos}",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = Gray
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = Info,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

