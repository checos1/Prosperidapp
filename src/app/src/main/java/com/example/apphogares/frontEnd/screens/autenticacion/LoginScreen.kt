package com.example.apphogares.frontEnd.screens.autenticacion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.R
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.LoadingScreen
import com.example.apphogares.frontEnd.screens.autenticacion.Components.Form
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.White
import com.example.apphogares.frontEnd.MainViewModel

@Composable
fun LoginScreen(funNav: (String) -> Unit) {
    val viewModel = hiltViewModel<MainViewModel>().loginViewModel

    //val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.identidad))

    //LottieAnimation(
    //    composition = composition,
    //    iterations = 1000,
    //)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundTopLogin)
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.prosperidad_logo),
                    contentDescription = "login",
                    modifier = Modifier
                        .fillMaxSize(),
                        //.aspectRatio(1f), // Maintain aspect ratio (adjust based on your logo's dimensions)
                    contentScale = ContentScale.Fit // Ensure the logo fits within the bounds without stretching
                )
            }
        }
        Column(modifier = Modifier.weight(7f)) {
            Column(Modifier.fillMaxSize()){

                if (!viewModel.loading.value) {
                    Row(modifier = Modifier.fillMaxWidth().weight(6f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Form()
                    }

                    Row(modifier = Modifier.fillMaxWidth().weight(4f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                        ){
/*                        Image(
                            painter = painterResource(id = R.drawable.logo_gov),
                            contentDescription = "login",
                            modifier = Modifier.scale(2.5f)
                        )*/
                    }

                }
            }
        }

        if (viewModel.loading.value) {
           LoadingScreen()
        } else if (viewModel.isLoggedIn.value) {
                viewModel.isLoggedIn.value = false
            funNav(RoutesNav.questionScreen.route)
        }

        if(viewModel.isNotHogar.value) {
            viewModel.document.value = ""
            viewModel.typeDocument.value = ""
            var errorInfo = "Su documento no hace parte de un  hogar acompañado"
            var title = "Ups, Error"

            AlertDialog(
                onDismissRequest = {},
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
                                        viewModel.isNotHogar.value = false
                                    },
                                    tint = White,
                                )
                            }
                        }

                        Row(modifier = Modifier.weight(6f)
                            .background(BackgroundBottomInTo, shape = RoundedCornerShape(0.dp, 0.dp,20.dp, 20.dp))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(funNav = {})
}


fun ContentMain(){}
