package com.example.apphogares.frontEnd.screens.ruta.Categorias.Components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.screens.ruta.Categorias.ItemCategoria
import com.example.apphogares.frontEnd.screens.ruta.Categorias.categoriaViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin


@Composable
fun ListaCategorias(
    itemCategorias: List<ItemCategoria>,
    categoriaViewModel: categoriaViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E4)) // fondo crema
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 0.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(itemCategorias.size) { index ->
                val itemCategoria = itemCategorias[index]
                CategoriaItem(
                    itemCategoria = itemCategoria,
                    onClick = { categoriaViewModel.onCategoriaSelected(itemCategoria) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp) // solo los items llevan padding
                )
            }
        }
    }
}
