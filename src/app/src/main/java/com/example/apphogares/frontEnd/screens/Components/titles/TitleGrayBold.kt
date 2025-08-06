package com.example.apphogares.frontEnd.screens.Components.titles

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TitleGrayBold(
    text: String,
    modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Gray
    )
}