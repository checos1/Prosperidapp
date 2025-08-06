package com.example.apphogares.frontEnd.screens.Components.Video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SplashScreenOverlay(
    showSplash: Boolean,
    urlVideo: String,
    onCompleted: () -> Unit
) {
    if (showSplash) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            VideoPlayer(
                urlVideo = urlVideo,
                modifier =  Modifier.matchParentSize()
            ) {
                onCompleted()
            }
        }
    }
}