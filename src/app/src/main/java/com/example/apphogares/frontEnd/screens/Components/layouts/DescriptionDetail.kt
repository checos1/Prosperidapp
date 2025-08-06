package com.example.apphogares.frontEnd.screens.Components.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionDetail(backgroundColorDetailBox: Color, content: @Composable () -> Unit){

    Box(
        Modifier
            .fillMaxWidth()
            .padding(10.dp))
    {
        Body(backgroundColorDetailBox, content)
    }
}

@Composable
fun Body(backgroundColorDetailBox: Color, content: @Composable () -> Unit) {
    Column() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            color = backgroundColorDetailBox,
            shape = MaterialTheme.shapes.large,
        ) {
            content()
        }

    }
}