package com.example.apphogares.frontEnd.screens.autenticacion.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.R
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.screens.Components.Video.VideoPlayer
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta



@Composable
fun WellcomeScreen(){
    var stop = 0

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.prosperidad))
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundTopLogin),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)){
            Image(
                painter = painterResource(id = R.drawable.prosperapp_logo),
                contentDescription = "login",
                modifier = Modifier.fillMaxSize(),
                //alignment = Alignment.Center,
                //contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(BackGroundTopLogin)
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(BackGroundTopLogin)
            .weight(2f)
        ){
            VideoPlayer(urlVideo = "android.resource://${AppHogaresAplication.context.packageName}/${R.raw.ingresow}",
                modifier =  Modifier.matchParentSize().background(BackGroundTopLogin)
            ) {
                //funNav(RoutesNav.loginScreen.route)
            }
/*            LottieAnimation(
                composition = composition,
                iterations = if(stop == 1) 1 else 10000,
                contentScale = ContentScale.FillWidth,
            )*/
        }
    }
}
