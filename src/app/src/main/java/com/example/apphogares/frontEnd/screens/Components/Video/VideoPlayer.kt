package com.example.apphogares.frontEnd.screens.Components.Video

import android.media.MediaPlayer
import android.net.Uri
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.apphogares.AppHogaresAplication

@Composable
fun VideoPlayer(
    urlVideo: String,
    modifier: Modifier,
    allowRepeat: Boolean = false,
    onCompletedVideo: () -> Unit
) {
    var videoReady by rememberSaveable { mutableStateOf(false) }
    //var videoCompleted by rememberSaveable { mutableStateOf(false) }

/*    if (videoCompleted) {

        Text("Video Completed")
    } else {

    }*/

    AndroidView(
        modifier = modifier.then(
            if (videoReady) Modifier else Modifier.size(0.dp) // Oculta mientras no está listo
        ),
        factory = { context ->
            VideoView(context).apply {
                setBackgroundColor(Color.Transparent.toArgb())
                clipToOutline = true

                val mediaController = MediaController(context)
                mediaController.setAnchorView(this)
                setMediaController(mediaController)

                setOnPreparedListener { mp ->
                    mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                    if (allowRepeat) {
                        mp.isLooping = true
                    }
                    videoReady = true
                    mp.start()
                }

                setOnCompletionListener {
                    onCompletedVideo()
                }

                setVideoURI(Uri.parse(urlVideo))
            }
        }
    )
}
