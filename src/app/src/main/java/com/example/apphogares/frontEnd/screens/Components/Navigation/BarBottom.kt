package com.example.apphogares.frontEnd.screens.Components.Navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.example.apphogares.frontEnd.RoutesNav

@Composable
fun BarBottom(funNav: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center
    ){
        Row(
            //verticalAlignment = Alignment.CenterVertically
        ){

            //InfoHogar
            Column(
                Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .clickable {
                        funNav(RoutesNav.infoHogarScreen.route)
                    }
            ){
                Box(Modifier
                    .align(Alignment.CenterHorizontally)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.icon_mihogar),
                        contentDescription = "Img Menu",
                        contentScale = ContentScale.Fit
                    )
                }
                Box(Modifier.align(Alignment.CenterHorizontally)){Text("Mi Hogar", style = MaterialTheme.typography.headlineSmall, color = Color.Black)}
            }

            //Notificaciones
            Column(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(2.dp)
                    .clickable {
                        funNav(RoutesNav.rutaScreen.route)
                    }
            ){
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
/*                    BadgedBox(
                        badge = {
                            if (AppHogaresAplication.alertas.size > 0){
                                Badge(
                                    modifier = Modifier.offset(x = 5.dp, y = 10.dp),
                                    containerColor = Color.Red,
                                ) {
                                    Text(
                                        text = AppHogaresAplication.alertas.size.toString(),
                                        modifier = Modifier.semantics {
                                            contentDescription = "${AppHogaresAplication.alertas.size} nuevas notificationes"
                                        }
                                    )
                                }
                            }
                        }
                    ) {

                    }*/
                    Image(
                        painter = painterResource(id = R.drawable.ic_ruta),
                        contentDescription = "Img Menu",
                        contentScale = ContentScale.Fit
                    )
                }

                Box(Modifier.align(Alignment.CenterHorizontally)){Text("Ruta", style = MaterialTheme.typography.headlineSmall, color = Color.Black)}
            }

            //rutas
            Column(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(2.dp)
                    .clickable {
                        funNav(RoutesNav.ComunicacionesScreen.route)
                    }
            ){
                Box(Modifier.align(Alignment.CenterHorizontally)){
                    Image(
                        painter = painterResource(id = R.drawable.icon_ruta),
                        contentDescription = "Img Menu",
                        contentScale = ContentScale.Fit
                    )
                }
                Box(Modifier.align(Alignment.CenterHorizontally)){Text("Ruta", style = MaterialTheme.typography.headlineSmall, color = Color.Black)}
            }

            //Ofertas
            Column(
                Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .clickable {
                        funNav(RoutesNav.ofertaHogarScreen.route)
                    }
            ){
                Box(Modifier.align(Alignment.CenterHorizontally)){
                    Image(
                        painter = painterResource(id = R.drawable.icon_oferta),
                        contentDescription = "Img Menu",
                        contentScale = ContentScale.Fit
                    )
                }
                Box(Modifier.align(Alignment.CenterHorizontally)){Text("Ofertas", style = MaterialTheme.typography.headlineSmall, color = Color.Black)}
            }

            //Certificados
            Column(
                Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .clickable {
                        funNav(RoutesNav.gestionaScreen.route)
                    }
            ){
                val encuestasCategoriaAbiertas = AppHogaresAplication.encuestas.filter { it.estado == EstadoEncuesta.ABIERTA && it.categoria != "" }
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                    BadgedBox(
                        badge = {
                            if (encuestasCategoriaAbiertas.isNotEmpty()){
                                Badge(
                                    modifier = Modifier.offset(x = 5.dp, y = 10.dp),
                                    containerColor = Color.Red,
                                ) {
                                    Text(
                                        text = encuestasCategoriaAbiertas.size.toString(),
                                        modifier = Modifier.semantics {
                                            contentDescription = "${encuestasCategoriaAbiertas.size} nuevas encuestas"
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_gestiona),
                            contentDescription = "Img Menu",
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Box(Modifier.align(Alignment.CenterHorizontally)){Text("Gestiona", style = MaterialTheme.typography.headlineSmall, color = Color.Black)}
            }
        }
    }
}