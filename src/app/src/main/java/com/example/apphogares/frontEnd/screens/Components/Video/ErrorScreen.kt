package com.example.apphogares.frontEnd.screens.Components.Video

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.R
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta

@Composable
fun ErrorScreen(messageError: String) {
    var stop = 0

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

            ErrorDetailsSentence(error = "Ha ocurrido un error", details = messageError)
        }

        Spacer(modifier = Modifier.weight(0.5f).background(BackGroundTopLogin))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(4f)
            .background(BackGroundTopLogin)
        ){
            VideoPlayer(urlVideo = "android.resource://${AppHogaresAplication.context.packageName}/${R.raw.errorw}",
                modifier =  Modifier.matchParentSize().background(BackGroundTopLogin)
            ) {

            }
/*            LottieAnimation(
                composition = composition,
                iterations = if(stop == 1) 1 else 10000,
                contentScale = ContentScale.FillWidth,
            )*/
        }
    }
}

@Composable
fun ErrorDetailsSentence(error: String, details: String) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Red)) {
            append("Error: $error")
        }
        append("\n")
        withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Red)) {
            append("Detalles: $details")
        }
    }

    Text(text = annotatedString)
}
