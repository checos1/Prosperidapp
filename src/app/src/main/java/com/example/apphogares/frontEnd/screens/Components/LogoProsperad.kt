package com.example.apphogares.frontEnd.screens.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apphogares.R

@Composable
fun LogoProsperad (
    modifier: Modifier){
    Row(modifier = modifier) {114870
        Image(
            painter = painterResource(id = R.drawable.prosperapp_logo),
            contentDescription = "login",
            modifier = modifier
        )
    }
}