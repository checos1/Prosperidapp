package com.example.apphogares.frontEnd.screens.Components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithText(
    colorBackground: Color,
    colorText: Color,
    textButton: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Button(
        onClick = onClick ,
        modifier = modifier
            .padding(10.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(containerColor = colorBackground),
        contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp, 0.dp)
                .fillMaxWidth(),
            text = textButton,
            style = MaterialTheme.typography.bodySmall,
            color = colorText,
            textAlign = TextAlign.Center
        )
    }
}