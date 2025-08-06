package com.example.apphogares.frontEnd.screens.Components.Video

import LottieAnimationWithCompletion
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.R
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta

@Composable
fun AlegriaScreen(modifier: Modifier, onCompleted: () -> Unit) {
    var stop = 1

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.prosperidad))
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundTopLogin),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Column(modifier = Modifier.fillMaxWidth()
            .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Felicitaciones...",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Box(modifier = Modifier
            .fillMaxSize()
            .background(BackGroundTopLogin)
            .weight(4f)
        ){
            //Audio().startAudioMayo("felicitaciones", 2000)
            VideoPlayer(urlVideo = "android.resource://${AppHogaresAplication.context.packageName}/${R.raw.alegriaw}",
                modifier =  Modifier.matchParentSize().background(BackGroundTopLogin)
            ) {
                onCompleted()
            }
/*            LottieAnimationWithCompletion(
                composition = composition,
                onAnimationComplete = {
                    onCompleted()
                }
            )*/
/*            LottieAnimation(
                composition = composition,
                iterations = if(stop == 1) 1 else 10000,
                contentScale = ContentScale.FillWidth,
            )*/
        }
    }
}

