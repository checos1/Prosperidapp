package com.example.apphogares.frontEnd.screens.Informate.Componentes

import android.webkit.URLUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apphogares.backEnd.core.models.Comunicaciones.Publicacion
import com.example.apphogares.frontEnd.screens.Informate.informateViewModel
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.White

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R

@Composable
fun CarruselPublicacion(pub: Publicacion, informateViewModel: informateViewModel = hiltViewModel()) {
    if (pub.carrusel.isNotEmpty()) {

        val pagerState = rememberPagerState(pageCount = { pub.carrusel.size })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Gray, RoundedCornerShape(12.dp))
                .background(White)
                .padding(12.dp)
        ) {
            // Título con hashtag
/*            Text(text = "#ProsperidadEnLasRegiones ${pub.titulo}", color = Gray, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${pub.descripcion.take(50)}... Ver más",
                color = Gray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))*/
            EncabezadoPublicacion(modifier = Modifier.fillMaxWidth(), pub = pub)
            Box {
                HorizontalPager(state = pagerState) { page ->
                    val painter = if (AppHogaresAplication.estadoDispositivo.isInternetConectivity) {
                        rememberAsyncImagePainter(pub.carrusel[page])
                    } else {
                        painterResource(id = R.drawable.imagenauxiliar1)
                    }
                    Image(
                        painter = painter,
                        contentDescription = "Imagen carrusel",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                // Contador de páginas (ej. 1/14)
                Text(
                    text = "${pagerState.currentPage + 1}/${pub.carrusel.size}",
                    color = White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Indicadores de página (dots)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(pub.carrusel.size) { index ->
                    val color = if (index == pagerState.currentPage) Color.Black else Color.LightGray
                    Canvas(modifier = Modifier.size(8.dp).padding(2.dp)) {
                        drawCircle(color = color)
                    }
                }
            }

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



/*
@Preview(showBackground = true)
@Composable
fun CarruselPublicacionPreview() {
    val samplePub = Publicacion(
        titulo = "Información Financiera",
        descripcion = "Aprende a manejar tu dinero con estas herramientas visuales.",
        banner = "",
        videoYoutube = "",
        tipo = "ComponentHerramientasCarrusel",
        carrusel = listOf(
            "https://sapsdafchogaresprod.blob.core.windows.net/cntpsdafchogaresprod/assets/alcancia_91401fb712.jpeg",
            "https://sapsdafchogaresprod.blob.core.windows.net/cntpsdafchogaresprod/assets/billetera_9b79422e87.jpeg",
            "https://sapsdafchogaresprod.blob.core.windows.net/cntpsdafchogaresprod/assets/calculadora_721361bfff.jpeg"
        ),
        ordenar = 1,
        fechaVencimiento = "2025-06-30T14:00:00Z"
    )
    CarruselPublicacion(pub = samplePub)
}*/

