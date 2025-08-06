package com.example.apphogares.frontEnd.screens.oferta.Components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.GrayTwo


@Composable
fun InformationDetailOferta(
    title: String,
    backgroundColorMainBox: Color,
    backgroundColorDetailBox: Color,
    foregroundColorMainBox: Color,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        color = backgroundColorMainBox,
        shape = MaterialTheme.shapes.large,
    ) {
        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                color = backgroundColorDetailBox,
                shape = MaterialTheme.shapes.large,
                border = BorderStroke(2.dp, color = GrayTwo)
            ) {
                content()
            }
        }
    }
}
