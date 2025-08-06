package com.example.apphogares.frontEnd.screens.PeriodicoVida.Components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.apphogares.backEnd.core.models.PeriodicoVida.PeriodicoVida
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.frontEnd.screens.PeriodicoVida.periodicoVidaViewModel


@Composable
fun PeriodicoVidaItem(periodico: PeriodicoVida, viewModel: periodicoVidaViewModel = hiltViewModel()) {

    println("URL PeriodicoVidaItem: $periodico")

    AsyncImage(
        model = periodico.imagen,
        contentDescription = periodico.titulo,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                viewModel.periodicoSeleecionado.value = periodico
                viewModel.mostrarPDF.value = true
                viewModel.agregarVisitaHija("PeriodicoVida", viewModel.periodicoSeleecionado.value.titulo)
            }
    )
}

fun getResourceIdFromName(context: Context, resourceName: String, resourceType: String = "drawable"): Int {
    return context.resources.getIdentifier(resourceName, resourceType, context.packageName)
}

@Preview
@Composable
fun PeriodicoVidaItemPreview() {
    val urlImage = "https://sapsdafchogaresqa.blob.core.windows.net/cntpsdafchogaresqa/PeriodicoVida/1.jpg" + Constants.ACCESS_TOKEN
    val periodico = PeriodicoVida(
        titulo = "Titulo",
        imagen = urlImage
    )
    PeriodicoVidaItem(periodico)
}