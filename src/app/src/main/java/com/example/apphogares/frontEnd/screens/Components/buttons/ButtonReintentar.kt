package com.example.apphogares.frontEnd.screens.Components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import com.example.apphogares.R
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.Red
import com.example.apphogares.frontEnd.theme.VerdeCorrecto
import com.example.apphogares.frontEnd.theme.White

@Composable
fun ButtonReintentar(
    reintentar: String,
    contador: String,
    onClick: () -> Unit,
    estado: MutableState<Boolean> = mutableStateOf(true)
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                if (estado.value) BackgroundBottomInTo else BackgroundBottomInTo,
                shape = RoundedCornerShape(50.dp)
            ),
        colors = ButtonDefaults.buttonColors(containerColor = BackGroundTopLoginPlus, contentColor = BackGroundTopLoginPlus),
        onClick = onClick
    ){
        Text(reintentar,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold,
            color = if(estado.value) Gray else Gray
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .clip(shape = CircleShape).height(55.dp).padding(10.dp),
        contentAlignment = Alignment.CenterStart,
    ) {


        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, 5.dp)) {
            Image(
                painter = painterResource(id = R.drawable.reintentar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(35.dp)
                    .padding(start = 4.dp),
                alignment = Alignment.BottomStart
            )
            Text(
                modifier = Modifier
                    .padding(1.dp, 0.dp).height(45.dp),
                text = "  ${contador}",
                style = TextStyle(
                    color = BackGroundTopLogin,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                ),
            )
        }

    }
}