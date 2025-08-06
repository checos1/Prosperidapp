package com.example.apphogares.frontEnd.screens.Components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.R
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.screens.Components.Video.VideoPlayer
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus

@Composable
fun LoadingScreen() {
    var stop = 0

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.prosperidad))
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
/*        VideoPlayer(urlVideo = "android.resource://${AppHogaresAplication.context.packageName}/${R.raw.vuela}",
            modifier = Modifier.fillMaxHeight().background(BackGroundTopLoginPlus), allowRepeat = true
        ) {

        }*/
        LottieAnimation(
            composition = composition,
            iterations = if(stop == 1) 1 else 10000,
            contentScale = ContentScale.FillWidth,
        )
    }
}


