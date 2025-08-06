package com.example.apphogares.frontEnd.screens.ruta.Ruta

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.contenidosRuta.Ruta
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRutaEnum
import com.example.apphogares.frontEnd.screens.ruta.ObjImg
//import com.example.apphogares.frontEnd.screens.ruta.calculatePos
import com.example.apphogares.frontEnd.screens.ruta.rutaViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.VerdeCorrectoTwo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ruta(
    categoria: Categoria,
    screenWidthDp: Int,
    catIndex: Int,
    objImg: MutableList<ObjImg>,
    funNav: (String) -> Unit,
    viewModel: rutaViewModel,
    categorias: List<Categoria>
) {

    val square = (screenWidthDp / 3) - 20
    val margen = (screenWidthDp - (square * 3) - 20) / 2
    //val posColumn = rememberSaveable { mutableStateOf(1) }
    var postColumn: PosColumn = PosColumn()

    var valores = Array(2) { 0 }

    println("Recomposition Ruta")
    Column {
        categoria.rutas.forEachIndexed { index, ruta ->
            Row {
                Column(

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(VerdeCorrectoTwo)
                            .padding(start = 5.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(categoria.nombre, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text(ruta.nombre, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        imagenGamificacion(viewModel, ruta)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height((ruta.tematicas.size * (square + margen)).dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {

                            //Reiniciamos el valor del contador de contenedores que define cuantos hay a la derecha y a la izquierda
                            //posColumn.value = 0

                            ruta.tematicas.forEachIndexed { indexRuta, tematicas ->
                                valores =
                                    calculatePos(indexRuta + 1, square + margen, postColumn)
                                //Si la tematica esta ubicada en los extremos izquiero o derecha, decoramos creando un box a su lado
                                if (valores[2] < 100000) {
                                    var posY = valores[1]
                                    var heightAux = square
                                    var widthAux = (square * 2) + 25
                                    if (indexRuta + 1 > 1) {
                                        posY = (valores[1] - 20)
                                        heightAux = square + 35
                                    }

                                    val imgId: List<ObjImg> =
                                        objImg.filter { it.codigo == tematicas.codigo }
                                    //posImg.value = posImg.value + 1

                                    Box(
                                        modifier = Modifier.absoluteOffset(
                                            valores[2].dp,
                                            posY.dp
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(widthAux.dp)
                                                .height(heightAux.dp)
                                                .fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            setpImgDecoration(
                                                imgId[0].idImg,
                                                catIndex,
                                                indexRuta,
                                                valores
                                            )
                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier.absoluteOffset(
                                        valores[0].dp,
                                        valores[1].dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width((square).dp)
                                            .height(square.dp)
                                    ) {
                                        stepImg(
                                            viewModel.GamiEstadoTematica(tematicas.codigo),
                                            ruta.tematicas,
                                            indexRuta,
                                            tematicas.codigo,
                                            funNav,
                                            viewModel
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

}

@Composable
private fun imagenGamificacion(
    viewModel: rutaViewModel,
    ruta: Ruta
) {
    val imageBitmap = viewModel.ObternerImagenGamificacion(ruta.codigo)
    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap, // Replace with the card back image
            contentDescription = "Imagen Ruta",
            modifier = Modifier.size(50.dp) //,
        )
    }
}

fun calculatePos(pos: Int, square: Int, postColumn: PosColumn):Array<Int>{

    //Definimos un array para almacenar la posicion en x y y de la temática
    val valXY = Array(4){0}

    //La posición en Y siempre será la posición de la tematica x el alto de cada espacio destinado para el icono de la tematica
    valXY[1] = ((pos-1) * square)

    //Identificamos si la temática se encuenta en una posición par o impar, esto ya que las par siempre se ubican en el centro
    //Las impares estaran siempre en los dos extremos uno en el derecho y otro en el izquierdo
    val posImpar = pos%2

    //Creamos una vandera que nos permite identifica si la posición impar de la tematica esta para la derecha o izquierda
    var posDerIzq = false

    //Si la posición es impar identificamos en que extremo tiene que estar
    if(posImpar == 1){
        //Acumulamos el número total de elementos impares
        postColumn.posColumn = postColumn.posColumn + 1

        //Si el número total de elementos impares en esta posición es impar, se alineara el objeto a la izquierda de lo contrario sera a la derecha
        var tmp = postColumn.posColumn % 2
        if(tmp == 0){ posDerIzq =  true }
        else posDerIzq =  false
    }

    //Se realiza la definición de la posición en X que el objeto tendra
    if(posImpar == 0) {
        valXY[0] = square
        valXY[2] = 100000
        valXY[3] = 0      //Dirección Centro
    }
    else{
        if(posDerIzq) {
            valXY[0] = square*2
            valXY[2] = 0
            valXY[3] = 1    //Dirección Izquierda
        }
        else{
            valXY[0] = 0
            valXY[2] = square
            valXY[3] = 2      //Dirección derecha
        }
    }

    return valXY
}

class PosColumn{
    var posColumn = 0

}

