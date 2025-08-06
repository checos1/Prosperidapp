package com.example.apphogares.frontEnd.screens.ruta.Ruta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.AppHogaresAplication.Companion.funNav
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.ruta.ObjImg
import com.example.apphogares.frontEnd.screens.ruta.Components.BarraScrollGamificacion
import com.example.apphogares.frontEnd.screens.ruta.Components.BarraTabsCategoria
import com.example.apphogares.frontEnd.screens.ruta.calculaImg
import com.example.apphogares.frontEnd.screens.ruta.rutaViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.VerdeCorrecto

@Composable
fun BuildRuta(
    objDataRuta: ContenidosRuta,
    screenWidthDp: Int,
    funNav: (String) -> Unit,
    viewModel: rutaViewModel = hiltViewModel()
) {

    val categorias: List<Categoria> = objDataRuta.Categorias.orEmpty()

    //Solicitamos el objeto de imagenes
    var objImg:MutableList<ObjImg> = calculaImg(categorias)

    AppHogaresAplication.listaActividadesTematicaEnCurso = emptyList()
    AppHogaresAplication.indexActividadEnCurso = 0

    Column(Modifier.fillMaxSize()
                   .background(VerdeCorrecto)
                   .padding(top = 30.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = Gray)
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxSize()
                .weight(1.5f)
                .background(BackGroundTopLogin)
        ){
/*            BarraScrollGamificacion(
                modifier = Modifier
                    .weight(1.8f)
                    .background(BackGroundTopLogin),
                false
            )*/
            BarraTabsCategoria(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.8f)
                    .background(BackGroundTopLogin),
                onRutasClick = {
                    funNav(RoutesNav.categoriaScreen.route)
                               },
                onInfoClick = {
                    funNav(RoutesNav.InformacionRutaScreen.route)
                }
            )

        }

        //Divider(color = Gray)

        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(14.5f)
            .background(BackGroundTopLogin)
        ){
            LazyColumn(){
                itemsIndexed(categorias){index, categoria ->
                    Column(){
                        Row(){
                            ruta(categoria, screenWidthDp, index, objImg, funNav, viewModel, categorias)
                        }
                    }
                }
            }
        }
    }


}
