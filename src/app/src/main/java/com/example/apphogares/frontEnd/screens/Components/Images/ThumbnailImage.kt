package com.example.apphogares.frontEnd.screens.Components.Images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R

@Composable
fun ThumbnailImage(imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(AppHogaresAplication.context)
            .data(imageUrl)
            .crossfade(true)
            .transformations(CircleCropTransformation())
            .build()
    )

    Box(modifier = Modifier.size(100.dp)) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }
            is AsyncImagePainter.State.Error -> {
                // Handle error state, e.g., show a placeholder or error image
                Image(
                    painter = painterResource(id = R.drawable.barra_progreso1),
                    contentDescription = "Error loading image",
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Thumbnail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun ThumbnailImagePreview() {
    val urlImage = "https://sapsdafchogaresqa.blob.core.windows.net/cntpsdafchogaresqa/PeriodicoVida/27.jpg?sv=2022-11-02&ss=bfqt&srt=o&sp=rwdlacupiytfx&se=2028-06-11T22:16:51Z&st=2024-06-11T14:16:51Z&spr=https&sig=0uPuzsC0yl5pgJZmpMcZMUWJcM6dci3Zr0teVeK8gio%3D"
    ThumbnailImage(urlImage)
}

