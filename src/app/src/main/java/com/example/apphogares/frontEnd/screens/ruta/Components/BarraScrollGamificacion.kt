package com.example.apphogares.frontEnd.screens.ruta.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.frontEnd.screens.ruta.Tematica.BoxGamificacion
import com.example.apphogares.frontEnd.screens.ruta.rutaViewModel
import com.google.gson.Gson


@Composable
fun BarraScrollGamificacion(
    modifier: Modifier,
    visibleBarra: Boolean,
    viewModel: rutaViewModel = hiltViewModel()
) {

    Row(
        modifier.fillMaxWidth()
                .padding(10.dp)
                .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =  Arrangement.SpaceBetween
    ){
        println("BarraScrollGamificacion ->> ${Gson().toJson(AppHogaresAplication.listaEstadosRutas)}")
        println("BarraScrollGamificacion existeEstadosRuta ->> ${viewModel.existeEstadosRuta.value}")
        if (viewModel.existeEstadosRuta.value) {
            AppHogaresAplication.listaEstadosRutas.forEach {
                val imageBitmap = viewModel.ObternerImagenGamificacion(it.ruta)
                imageBitmap?.let { bitmap ->
                    BoxGamificacion(
                        it, bitmap, Modifier
                            .size(100.dp, 100.dp)
                            .padding(end = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun DisplayImageFromUrl(imageUrl: String) {
    val painter = // Enable a crossfade animation when loading images
        rememberAsyncImagePainter(ImageRequest.Builder // Display a placeholder while loading
        // Display an error image if there's a problem loading
            (LocalContext.current).data(data = imageUrl).apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
            crossfade(true) // Enable a crossfade animation when loading images
            //placeholder(R.drawable.placeholder) // Display a placeholder while loading
            //error(R.drawable.error) // Display an error image if there's a problem loading
        }).build()
        )

    Image(
        painter = painter,
        contentDescription = "Loaded Image",
        modifier = Modifier.fillMaxWidth(), // Takes the full width available
        contentScale = ContentScale.Crop // Crop the image if not completely fitting the size
    )
}

