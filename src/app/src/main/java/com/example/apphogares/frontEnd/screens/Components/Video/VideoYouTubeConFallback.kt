package com.example.apphogares.frontEnd.screens.Components.Video

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoYouTubeConFallback(
    modifier: Modifier = Modifier,
    videoId: String,
    imagenAuxiliar: Int,
    hayInternet: Boolean
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    if (hayInternet) {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    })
                }
            }
        )
    } else {
        Image(
            painter = painterResource(id = imagenAuxiliar),
            contentDescription = "Imagen auxiliar sin conexión",
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}
