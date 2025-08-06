package com.example.apphogares.frontEnd.screens.ruta.Ruta

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.enums.RutaImagenEstado
import com.example.apphogares.backEnd.core.models.contenidosRuta.Tematica
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoTematicaEnum
import com.example.apphogares.backEnd.core.models.gamificacion.EstadosTematicas
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.ruta.rutaViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun stepImg(
    actState: EstadosTematicas,
    prvState: List<Tematica>,
    indexRuta: Int,
    code: String?,
    funNav: (String) -> Unit,
    viewModel: rutaViewModel
) {
    var spinner = 0
    var stop = 0

    when (actState.estadoTematica) {
        EstadoTematicaEnum.Activo -> {
            spinner = R.raw.sp_2
        }
        EstadoTematicaEnum.Realizado -> {
            spinner = R.raw.sp_3
            stop = 1
        }
        EstadoTematicaEnum.Bloqueado -> {
            spinner = R.raw.sp_1
        }
    }

    val utilities = utilities()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(spinner))


    println("stepImg : listaActividadesTematicaEnCurso: ${AppHogaresAplication.listaActividadesTematicaEnCurso} ")
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .weight(3f)
                .clickable() {
                    when (actState.estadoTematica) {
                        EstadoTematicaEnum.Activo -> {
                            for (cat in AppHogaresAplication.contenidoCMS.Categorias.orEmpty()) {
                                for (ruta in cat.rutas) {
                                    for (tematica in ruta.tematicas) {
                                        if (tematica.codigo.toString() == code.toString()) {
                                            AppHogaresAplication.listaActividadesTematicaEnCurso =
                                                tematica.actividades
                                        }
                                    }
                                }
                            }
                            if (!actState.pasoUno) {
                                funNav("${RoutesNav.historietaScreen.route}/$code")
                            } else if (!actState.pasoDos) {
                                AppHogaresAplication.indexActividadEnCurso = 0
                                utilities.cambiarActividad(
                                    AppHogaresAplication.listaActividadesTematicaEnCurso.get(
                                        AppHogaresAplication.indexActividadEnCurso
                                    ).tipo,
                                    funNav, actState.categoria, actState.ruta, actState.codigo
                                )
                            } else if (!actState.pasoTres) {
                                AppHogaresAplication.indexActividadEnCurso = 1
                                utilities.cambiarActividad(
                                    AppHogaresAplication.listaActividadesTematicaEnCurso.get(
                                        AppHogaresAplication.indexActividadEnCurso
                                    ).tipo,
                                    funNav, actState.categoria, actState.ruta, actState.codigo
                                )
                            }
                        }

                        EstadoTematicaEnum.Realizado -> {
                            viewModel.ActualizaActividaTematica(actState.codigo, -1)
                            AppHogaresAplication.indexActividadEnCurso = 0
                            funNav("${RoutesNav.historietaScreen.route}/$code")
                        }

                        EstadoTematicaEnum.Bloqueado -> {

                        }
                    }
                })
        {
            LottieAnimation(
                composition = composition,
                iterations = if(stop == 1) 1 else 10000,
                contentScale = ContentScale.FillWidth,
            )
        }
        Row(modifier = Modifier
            .weight(1f)
            .padding(5.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .absoluteOffset(10.dp, 0.dp)) {
                Image(
                    painter = painterResource(
                        id =  if(actState.pasoUno) R.drawable.elipse_full else R.drawable.elipse_clean
                    ),
                    contentDescription = "login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
            Column(modifier = Modifier
                .weight(1f)
                .absoluteOffset(0.dp, 0.dp)) {
                Image(
                    painter = painterResource(
                        id =  if(actState.pasoDos) R.drawable.elipse_full else R.drawable.elipse_clean
                    ),
                    contentDescription = "login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
            Column(modifier = Modifier
                .weight(1f)
                .absoluteOffset(-10.dp, 0.dp)) {
                Image(
                    painter = painterResource(
                        id =  if(actState.pasoTres) R.drawable.elipse_full else R.drawable.elipse_clean
                    ),
                    contentDescription = "login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        }
    }


}

