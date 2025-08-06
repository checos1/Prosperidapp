package com.example.apphogares.frontEnd.screens.ruta.InformacionRuta

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.frontEnd.screens.Components.Video.VideoYouTubeConFallback
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.VerdeCorrecto

@Composable
fun InformacionRutaScreen(
    onBack: () -> Unit,
) {
    // Video de YouTube "quemado"
    val videoUrl = "https://www.youtube.com/watch?v=7ftK77WmaA0"
    val utilities = utilities()
    val videoId = utilities.obtenerVideoIdDeUrl(videoUrl) ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeCorrecto) // color fondo crema
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        // Header con fondo amarillo y botón atrás
        Divider(color = Color.Gray, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VerdeCorrecto)
                .padding(top = 20.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.Red,
                    modifier = Modifier.size(36.dp)
                )
            }
            //Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Como funciona la ruta",
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGroundTopLogin)
        ) {
            Column {
                // Espacio
                Spacer(modifier = Modifier.height(16.dp))

                // Texto introductorio
                Text(
                    text = "Conoce como navegar las rutas del conocimiento.",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(38.dp))

                // Video o imagen auxiliar
                VideoYouTubeConFallback(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    videoId = videoId,
                    imagenAuxiliar = R.drawable.imagenauxiliar4,
                    hayInternet = AppHogaresAplication.estadoDispositivo.isInternetConectivity
                )
            }
        }
    }
}
