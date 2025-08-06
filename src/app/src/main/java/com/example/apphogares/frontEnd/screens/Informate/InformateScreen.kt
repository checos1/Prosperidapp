package com.example.apphogares.frontEnd.screens.Informate

import android.net.Uri
import android.webkit.URLUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apphogares.backEnd.core.models.Comunicaciones.Publicacion
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.google.accompanist.pager.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import androidx.compose.ui.viewinterop.AndroidView
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.frontEnd.screens.Components.Video.VideoPlayer
import com.example.apphogares.frontEnd.screens.Informate.Componentes.BannerPublicacion
import com.example.apphogares.frontEnd.screens.Informate.Componentes.BannerPublicacionAutoHeight
import com.example.apphogares.frontEnd.screens.Informate.Componentes.CarruselPublicacion
import com.example.apphogares.frontEnd.screens.Informate.Componentes.VideoPublicacion
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun InformateScreen(informateViewModel: informateViewModel = hiltViewModel()) {
    val publicaciones = AppHogaresAplication.listaPublicaciones
    val isLoading = informateViewModel.isLoading.value
    val error = informateViewModel.msgError.value

    when {
        isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGroundTopLogin),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        error.isNotBlank() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGroundTopLogin),
                contentAlignment = Alignment.Center
            ) {
                Text(text = error, color = androidx.compose.ui.graphics.Color.Red)
            }
        }

        publicaciones.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGroundTopLogin),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No hay publicaciones disponibles", fontSize = 16.sp, color = Gray)
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGroundTopLogin)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(publicaciones) { publicacion ->
                    when (publicacion.tipo) {
                        "ComponentHerramientasCarrusel" -> CarruselPublicacion(publicacion)
                        "ComponentHerramientasBanner" -> BannerPublicacionAutoHeight(publicacion)
                        "ComponentHerramientasVideo" -> VideoPublicacion(publicacion)
                        else -> Text(text = "Tipo desconocido: ${publicacion.tipo}", fontSize = 16.sp, color = Gray)
                    }
                }
            }
        }
    }
}








