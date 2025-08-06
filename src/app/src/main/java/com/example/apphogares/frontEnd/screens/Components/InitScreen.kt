package com.example.apphogares.frontEnd.screens.Components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.R
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.screens.Components.Video.VideoPlayer
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus

@Composable
fun InitScreen(onCompleted: () -> Unit) {

    var stop = 0

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.prosperidad))
    var loadingProgress by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundTopLogin),
        contentAlignment = Alignment.Center
    ){
        Column {
/*            VideoPlayer(urlVideo = "android.resource://${AppHogaresAplication.context.packageName}/${R.raw.vuela}",
                modifier = Modifier
                    .fillMaxHeight()
                    .background(BackGroundTopLogin),
                onCompletedVideo = {
                    loadingProgress = true
                    onCompleted()
                }
            )*/
/*            LottieAnimation(
                composition = composition,
                iterations =  10000,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp),
                // .padding( start = if(valores[3] == 1) 20.dp else 50.dp, end = if(valores[3] == 1) 0.dp else 0.dp),
                alignment = if(valores[3] == 1) Alignment.CenterStart else Alignment.CenterEnd
            )*/
            LottieAnimation(
                composition = composition,
                iterations = if(stop == 1) 1 else 10000,
                contentScale = ContentScale.FillWidth,
            )
        }

        if(loadingProgress){
            CircularProgressIndicator(Modifier.size(50.dp))
        }
    }

}