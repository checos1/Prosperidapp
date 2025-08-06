package com.example.apphogares.frontEnd.screens.PeriodicoVida.Components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apphogares.backEnd.core.models.PeriodicoVida.PeriodicoVida
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta

@Composable
fun PeriodicosGrid(periodicos: List<PeriodicoVida>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Creates a grid with 2 columns
        modifier = Modifier.background(color = BackGroundTopLogin).padding(4.dp)
    ) {
        items(periodicos) { periodico ->
            PeriodicoVidaItem(periodico)
        }
    }
}



