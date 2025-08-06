package com.example.apphogares.frontEnd.screens.ruta.Tematica

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRuta

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRutaEnum
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI
import com.example.apphogares.frontEnd.screens.ruta.rutaViewModel


@Composable
fun BoxGamificacion(estadoRuta: EstadoRuta,
                    imagenRuta: ImageBitmap?,
                    modifier: Modifier = Modifier,
                    viewModel: rutaViewModel = hiltViewModel()
) {

    if (imagenRuta != null) {
        Box(
            modifier = modifier
            ){
            Image(
                bitmap = imagenRuta, // Replace with the card back image
                contentDescription = "Imagen Ruta",
                modifier = Modifier.fillMaxWidth().fillMaxSize().clip(shape = RoundedCornerShape(10.dp)).clickable {
                    //viewModelRuta.NavegarRuta(estadoRuta.ruta)
                    viewModel.existeMedallaSeleccionada(estadoRuta.ruta)
                } //,
/*               colorFilter = if (estadoRuta.estadoRuta != EstadoRutaEnum.Realizado) {
                    ColorFilter.tint(
                        color = Color.Gray,
                        blendMode = BlendMode.Saturation // This blend mode desaturates the color
                    )
                } else {
                    null
                }*/
            )
        }
    }
}