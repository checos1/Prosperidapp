package com.example.apphogares.frontEnd.screens.Components.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InformationDetailBox(
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

                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = foregroundColorMainBox,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            Spacer(modifier = Modifier.padding(5.dp))
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
}