package com.example.apphogares.frontEnd.screens.Informate.Componentes

import android.webkit.URLUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.Comunicaciones.Publicacion
import com.example.apphogares.frontEnd.screens.Informate.informateViewModel
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.White
import coil.compose.SubcomposeAsyncImage
import kotlin.compareTo
import kotlin.text.toFloat

@Composable
fun BannerPublicacion(pub: Publicacion, informateViewModel: informateViewModel = hiltViewModel()) {
    if (pub.banner.isNotBlank() && URLUtil.isValidUrl(pub.banner)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Gray, RoundedCornerShape(12.dp))
                .background(White)
                .padding(12.dp)
        ) {
/*            Text(text = pub.titulo, color = Gray, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = pub.descripcion, color = Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))*/
            EncabezadoPublicacion(modifier = Modifier.fillMaxWidth(), pub = pub)
            val painter = if (AppHogaresAplication.estadoDispositivo.isInternetConectivity) {
                rememberAsyncImagePainter(pub.banner)
            } else {
                painterResource(id = R.drawable.imagenauxiliar2)
            }
            Image(
                painter = painter,
                contentDescription = "Imagen banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)) //,
                    //.height(200.dp)
                    //.wrapContentHeight(),
                //contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))

            InteraccionesPublicacion(
                likesIniciales = pub.likes ?: 0,
                compartidosIniciales = pub.compartidos ?: 0,
                mensajeCompartir = "${pub.titulo} - ${pub.descripcion}",
                onLike = {
                    informateViewModel.IncrementarInteraccion(pub.id, pub.titulo, "like")
                },
                onCompartir = {
                    informateViewModel.IncrementarInteraccion(pub.id, pub.titulo, "compartir")
                }
            )
        }
    }
}


@Composable
fun BannerPublicacionAutoHeight(pub: Publicacion, informateViewModel: informateViewModel = hiltViewModel()) {
    var aspectRatio by remember { mutableStateOf<Float?>(null) }

    if (pub.banner.isNotBlank() && URLUtil.isValidUrl(pub.banner)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Gray, RoundedCornerShape(12.dp))
                .background(White)
                .padding(12.dp)
        ) {
/*            Text(text = pub.titulo, color = Gray, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = pub.descripcion, color = Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))*/
            EncabezadoPublicacion(modifier = Modifier.fillMaxWidth(), pub = pub)
            val painter = if (AppHogaresAplication.estadoDispositivo.isInternetConectivity) {
                rememberAsyncImagePainter(pub.banner)
            } else {
                painterResource(id = R.drawable.imagenauxiliar2)
            }
            SubcomposeAsyncImage(
                model = pub.banner,
                contentDescription = "Imagen banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (aspectRatio != null) Modifier.aspectRatio(aspectRatio!!) else Modifier
                    ),
                contentScale = ContentScale.Fit,
                onSuccess = { state ->
                    // Al cargar la imagen, calculamos el aspect ratio
                    val width = state.result.drawable.intrinsicWidth
                    val height = state.result.drawable.intrinsicHeight
                    if (width > 0 && height > 0) {
                        aspectRatio = width.toFloat() / height.toFloat()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))

            InteraccionesPublicacion(
                likesIniciales = pub.likes ?: 0,
                compartidosIniciales = pub.compartidos ?: 0,
                mensajeCompartir = "${pub.titulo} - ${pub.descripcion}",
                onLike = {
                    informateViewModel.IncrementarInteraccion(pub.id, pub.titulo, "like")
                },
                onCompartir = {
                    informateViewModel.IncrementarInteraccion(pub.id, pub.titulo, "compartir")
                }
            )
        }
    }
}
