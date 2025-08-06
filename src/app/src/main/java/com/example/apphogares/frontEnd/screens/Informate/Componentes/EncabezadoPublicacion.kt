package com.example.apphogares.frontEnd.screens.Informate.Componentes

import android.webkit.URLUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun EncabezadoPublicacion(
    modifier: Modifier = Modifier,
    pub: Publicacion,
    informateViewModel: informateViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = pub.titulo,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = pub.descripcion,
            color = Gray,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            maxLines = if (expanded) Int.MAX_VALUE else 2,
        )


        if (pub.descripcion.length > 100) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (expanded) "Ver menos" else "Ver más",
                color = androidx.compose.ui.graphics.Color.Blue,
                fontSize = 14.sp,
                modifier = Modifier.clickable { expanded = !expanded }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}


/*
@Preview(showBackground = true)
@Composable
fun EncabezadoPublicacionPreview() {
    val pub = Publicacion(
        titulo = "Título de la Publicación",
        descripcion = "Descripción breve de la publicación.",
        banner = "https://example.com/banner.jpg",
        videoYoutube = "",
        carrusel = emptyList(),
        ordenar = 1,
        fechaVencimiento = "2023-10-01",
        tipo = "ComponentHerramientasBanner"
    )
    EncabezadoPublicacion(modifier = Modifier.fillMaxWidth(), pub = pub)
}
*/
