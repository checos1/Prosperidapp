package com.example.apphogares.frontEnd.screens.ruta.Categorias

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication.Companion.funNav
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.ruta.Categorias.Components.ListaCategorias
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.VerdeCorrecto

@Composable
fun CategoriaScreen(onBack: () -> Unit,
    categoriaViewModel: categoriaViewModel = hiltViewModel()
) {
    val lista = categoriaViewModel.listaItemCategoria.value
    val error = categoriaViewModel.msgError.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeCorrecto)
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        // Header con fondo amarillo y botón atrás
        Divider(color = Color.Gray, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VerdeCorrecto)
                .padding(top = 20.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.Red,
                    modifier = Modifier.size(36.dp)
                )
            }
            //Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Como funciona la ruta",
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxSize()
                .background(BackGroundTopLogin)
                .padding(16.dp)
        ){
            when {
                lista.isNotEmpty() -> {
                    ListaCategorias(itemCategorias = lista, categoriaViewModel = categoriaViewModel)
                }
                error.isNotEmpty() -> {
                    // Muestra el mensaje de error en el centro de la pantalla
                    Text(
                        text = error,
                        modifier = Modifier
                            .align(Alignment.Center as Alignment.Vertical)
                            .padding(32.dp)
                    )
                }
                else -> {
                    // Puedes mostrar un loader si así lo deseas
                    Text(
                        text = "Cargando categorías...",
                        modifier = Modifier.align(Alignment.Center as Alignment.Vertical)
                    )
                }
            }
        }

    }
}
