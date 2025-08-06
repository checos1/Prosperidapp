package com.example.apphogares.frontEnd.screens.Components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithIconText(
    colorBackground: Color,
    colorText: Color,
    @DrawableRes icon: Int,
    textButton: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Button(
        onClick = onClick ,
        modifier = modifier
            // .padding(10.dp)
            .height(40.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(containerColor = colorBackground),
        contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Box(
            modifier = Modifier.background(colorBackground),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row() {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(48.dp)
                        .padding(start = 4.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(1.dp, 0.dp),
                    text = "| ",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorText,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                modifier = Modifier
                    .padding(1.dp, 0.dp)
                    .fillMaxWidth(),
                text = textButton,
                style = MaterialTheme.typography.bodySmall,
                color = colorText,
                textAlign = TextAlign.Center
            )
        }
    }
}