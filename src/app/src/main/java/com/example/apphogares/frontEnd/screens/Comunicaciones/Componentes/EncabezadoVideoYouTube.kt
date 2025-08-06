package com.example.apphogares.frontEnd.screens.Comunicaciones.Componentes

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.frontEnd.screens.Components.Video.VideoYouTubeConFallback
import com.example.apphogares.frontEnd.screens.Comunicaciones.comunicacionesViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun EncabezadoVideoYouTube(comunicacionesViewModel: comunicacionesViewModel = hiltViewModel()) {
/*    val videoId = remember(comunicacionesViewModel.comunicacion.value!!.videoUrl) {
        Uri.parse(comunicacionesViewModel.comunicacion.value!!.videoUrl).getQueryParameter("v")
    }*/
    val utilities = utilities()
    val videoId = utilities.obtenerVideoIdDeUrl(comunicacionesViewModel.comunicacion.value!!.videoUrl) ?: ""
    VideoYouTubeConFallback(
        videoId = videoId,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        imagenAuxiliar = R.drawable.imagenauxiliar4,
        hayInternet =  AppHogaresAplication.estadoDispositivo.isInternetConectivity
    )
/*    val lifecycleOwner = LocalLifecycleOwner.current
    if (AppHogaresAplication.estadoDispositivo.isInternetConectivity) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            if (videoId != null) {
                                youTubePlayer.cueVideo(videoId, 0f)
                            }
                        }
                    })
                }
            }
        )
    }else {
        Image(
            painter = painterResource(id = R.drawable.imagenauxiliar4),
            contentDescription = "Imagen auxiliar sin conexión",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }*/
}