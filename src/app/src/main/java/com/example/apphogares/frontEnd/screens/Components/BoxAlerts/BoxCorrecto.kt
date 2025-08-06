package com.example.apphogares.frontEnd.screens.Components.BoxAlerts

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.frontEnd.theme.White

@Composable
fun BoxAlert(frase: String, BColor: Color, @DrawableRes idImagen: Int, juego: String) {

    var altura = 130.dp
    if(juego == "EmparejarPalabrasScreen") altura = 60.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .clip(shape = CircleShape).height(altura).padding(10.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, 5.dp)) {
            Image(
                //painter = painterResource(id = R.drawable.check),
                painter = painterResource(id = idImagen),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(35.dp)
                    .padding(start = 4.dp),
                alignment = Alignment.BottomStart
            )
            Text(
                modifier = Modifier
                    .padding(1.dp, 0.dp),
                text = " ${frase}",
                style = TextStyle(
                    color = White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}