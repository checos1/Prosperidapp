package com.example.apphogares.frontEnd.screens.Components.titles

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.dpsapp.ui.theme.RobotoDefault

@Composable
fun BarTopScreen(@DrawableRes id: Int, text: String){
    Row(modifier = Modifier.padding(16.dp)){
        Icon(painter = painterResource(id),
            contentDescription = null,
            tint = BackgroundBottomInTo,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        Text(text = text,
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontFamily = RobotoDefault,
            modifier = Modifier.padding(start = 16.dp).align(alignment = Alignment.CenterVertically)
        )
    }
}