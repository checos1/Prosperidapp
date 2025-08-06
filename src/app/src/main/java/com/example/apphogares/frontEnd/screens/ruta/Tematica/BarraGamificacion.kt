package com.example.apphogares.frontEnd.screens.ruta.Tematica

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.navegacion.Navegacion
import com.example.apphogares.AppHogaresAplication


@Composable
fun BarraGamificacion(
    modifier: Modifier,
    navegacion: Navegacion,
    visibleBarra: Boolean
) {
    Row(
        modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =  Arrangement.SpaceBetween
    ){
        Box(
            Modifier.weight(2f)
                .fillMaxSize(),
            contentAlignment =  Alignment.BottomEnd
        ){
            Medallas(navegacion.monedas)
        }

        Box(modifier = Modifier.weight(6f)){
            //Spacer(modifier = Modifier.size(4.dp))
            if (visibleBarra) {
                BarraProgreso(1)
            }
        }

        Box(modifier = Modifier.weight(2f),
            contentAlignment =  Alignment.BottomStart
            ) {
            Esmeraldas(navegacion.vidas)
        }
    }
}

@Composable
fun BarraProgreso(posicionBarra: Int){

    Box(Modifier
        .padding(start = 8.dp, end = 8.dp)
    ){
        Image(
            painter = painterResource(id = ObtenerBarraProgreso(posicionBarra)),
            contentDescription = null,
            modifier = Modifier.scale(2.5f)
        )
    }
}

fun ObtenerBarraProgreso(posicionBarra: Int): Int {


    val barraProgreso = when (posicionBarra) {
        1 -> R.drawable.barra_progreso1
        2 -> R.drawable.barra_progreso2
        3 -> R.drawable.barra_progreso3
        4 -> R.drawable.barra_progreso4
        5 -> R.drawable.barra_progreso5
        else -> R.drawable.barra_progreso1
    }

    return barraProgreso

}

@Composable
fun Medallas(monedas: Int){
    Box(Modifier
        .zIndex(2f)
        .absoluteOffset(25.dp, 0.dp)
    ){
        Text(
            text = "${monedas}",
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.ranchers_regular)),
            fontSize = 18.sp
        )
    }

    Box(Modifier.fillMaxSize().zIndex(1f).absoluteOffset(0.dp, 0.dp)){
        Image(
            painter = painterResource(id = R.drawable.coin),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

    }

}

@Composable
fun Esmeraldas(vidas: Int){
    Box(Modifier.fillMaxSize().zIndex(1f).absoluteOffset(0.dp, 0.dp)){
        Image(
            painter = painterResource(id = R.drawable.esmeralda),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
    Box(Modifier
        .zIndex(2f)
        .absoluteOffset(-15.dp, 0.dp)
    ){
        Text(
            text = "x${vidas}",
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.ranchers_regular)),
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = false)
@Composable
fun MedallasPreview(){
    Medallas(100)
}

@Preview(showBackground = false)
@Composable
fun EsmeraldasPreview(){
    Esmeraldas(5)
}

@Preview(showBackground = false)
@Composable
fun ComponenteGamificacionPreview(){
    //BarraScrollGamificacion(modifier = Modifier.fillMaxWidth(), true, viewModel = hiltViewModel())
}

