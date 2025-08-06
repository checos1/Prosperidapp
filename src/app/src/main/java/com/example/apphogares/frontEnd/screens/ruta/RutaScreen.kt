package com.example.apphogares.frontEnd.screens.ruta

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.R
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.gamificacion.EstadosTematicas
import com.example.apphogares.backEnd.core.models.contenidosRuta.Tematica
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoTematicaEnum
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRutaEnum
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.Video.ErrorScreen
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components.InformacionIntegranteScreen
import com.example.apphogares.frontEnd.screens.ruta.Ruta.BuildRuta
import java.util.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RutaScreen(screenWidthDp: Int, screenHeightDp: Int, funNav: (String) -> Unit, viewModel: rutaViewModel = hiltViewModel()) {

    val isPopupDialog = remember { mutableStateOf(false) }
    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
    }

    if (viewModel.msgError.value != "") {
        println("RutaScreen msgError->> ${viewModel.msgError.value}")
        ErrorScreen(messageError = viewModel.msgError.value)
    }

    BuildRuta(AppHogaresAplication.contenidoCMS, screenWidthDp, funNav)

/*   if (viewModel.showDialogPopUp.value) {
        println("RutaScreen rutaMedallaSeleccionada->> ${viewModel.rutaMedallaSeleccionada.value}")
    }*/
    if (viewModel.showDialogPopUp.value) {
        isPopupDialog.value = true
        val medalla = viewModel.ObtenerMedalla(viewModel.rutaMedallaSeleccionada.value)

        if (medalla != null) {
            println("RutaScreen medalla->> ${medalla}")
            Dialog(onDismissRequest = { viewModel.SetDialogMedalla("") } ) {
                CustomDialogUI(
                    openDialogCustom = isPopupDialog,
                    title = medalla.nombre,
                    onClick = {
                        viewModel.SetDialogMedalla("")
                    },
                    contentDialogo = {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = medalla.mensaje1,
                                style = MaterialTheme.typography.labelMedium,
                                textAlign = TextAlign.Justify
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = medalla.mensaje2,
                                style = MaterialTheme.typography.labelMedium,
                                textAlign = TextAlign.Justify
                            )
/*                            ButtonAccept(
                                text = "Aceptar",
                                onClick = {
                                    viewModel.SetDialogMedalla("")
                                }
                            )*/
                        }
                    }
                )
            }
        }
    }
}



//Se defini un data class que nos ayude a definir las imagenes que se requieren en la tura
data class ObjImg(
    val codigo: String,
    val idImg: Int
)

//Construye un objeto con las imagenes que se requieren en la ruta
fun calculaImg(categorias: List<Categoria>): MutableList<ObjImg> {

    var imgsList = listOf(R.raw.animal_cow, R.raw.animal_dog, R.raw.animal_lamp, R.raw.animal_pig, R.raw.animal_rabbit,
        R.raw.arbol_2, R.raw.arbol_3, R.raw.arbol_4,
        R.raw.palmera_1, R.raw.palmera_2,
        R.raw.fruta_banano, R.raw.fruta_cafe, R.raw.fruta_coco, R.raw.fruta_maiz, R.raw.fruta_zanahoria
    ).shuffled(Random())

    var posImpar = 0
    var posImgsList = 0
    val newVectImg = mutableListOf<ObjImg>()
    var posIndex = 0

    //Recordemos que solo se colocan imagenes en las posiciones te las tematicas que sean impares
    categorias.forEach{ cat ->
        cat.rutas.forEach{ruta ->
            posImpar = 0
            posIndex = 0
            ruta.tematicas.forEach{tema ->
                posImpar = posImpar+1
                if((posImpar%2) == 1){
                    newVectImg.add(
                        ObjImg(tema.codigo, imgsList.get(posImgsList)),
                    )
                    posImgsList = posImgsList+1
                    if(posImgsList == imgsList.size) posImgsList = 0
                    posIndex = posIndex+1
                }
            }
        }
    }

    return newVectImg
}

