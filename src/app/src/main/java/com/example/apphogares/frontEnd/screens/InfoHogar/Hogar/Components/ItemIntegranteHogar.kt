package com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apphogares.R
import com.example.apphogares.frontEnd.screens.Components.layouts.nonScaledSp
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.dpsapp.ui.theme.RobotoDefault

@Composable
fun ItemIntegranteHogar(
    text: String,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.padding(top = 16.dp))
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)
        .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically){
        Icon(painter = painterResource(id= R.drawable.person),
            contentDescription = null,
            //tint = BlueTwo,
            modifier = Modifier
                .size(60.dp)
                .align(alignment = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Button(onClick = { onClick() } ,
            colors = ButtonDefaults.buttonColors(contentColor = BackGroundTopLoginPlus, containerColor = BackGroundTopLoginPlus),
            modifier = Modifier
                .background(BackGroundTopLoginPlus, shape = RoundedCornerShape(50.dp))
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text(text = text,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .background(BackGroundTopLoginPlus),
                textAlign = TextAlign.Left,
                fontSize = 14.nonScaledSp,
                fontFamily = RobotoDefault,
                fontWeight = FontWeight.SemiBold

            )
        }
    }
}