package com.example.apphogares.frontEnd.screens.ruta.Ruta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun setpImgDecoration(
    imgList: Int, catIndex: Int, indexRuta: Int, valores: Array<Int>
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(imgList))

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement =  Arrangement.Center,
        verticalAlignment =  Alignment.CenterVertically
    ){
        LottieAnimation(
            composition = composition,
            iterations =  10000,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(120.dp)
                .height(150.dp),
            // .padding( start = if(valores[3] == 1) 20.dp else 50.dp, end = if(valores[3] == 1) 0.dp else 0.dp),
            alignment = if(valores[3] == 1) Alignment.CenterStart else Alignment.CenterEnd
        )
    }

}