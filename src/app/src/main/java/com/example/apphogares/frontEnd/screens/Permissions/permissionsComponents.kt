package com.example.apphogares.frontEnd.screens.Permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apphogares.R
import com.example.apphogares.frontEnd.theme.BackGroundRuta


@Composable
fun MessagePermisssions() {
    Text(text = "PROSPERIDAD NECESITA",  fontStyle = FontStyle( R.font.roboto), modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = BackGroundRuta, textAlign = TextAlign.Center)
    Text(text = "PERMISOS", fontStyle = FontStyle( R.font.roboto), modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleMedium,fontWeight = FontWeight.Bold, color = BackGroundRuta, textAlign = TextAlign.Center)
    PermisosDividir()
    Text(text = "Para mejorar la experiencia y facilitar la navegacion es necesario aceptar los permisos y activar las notificaciones", fontStyle = FontStyle( R.font.roboto), modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),style = MaterialTheme.typography.titleMedium, color = BackGroundRuta, textAlign = TextAlign.Justify)
}

@Composable
fun PermisosDividir(){
    Spacer(modifier = Modifier.size(20.dp))
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(thickness = 1.dp,color = Color.Black,)
    }
    Spacer(modifier = Modifier.size(20.dp))

}

@Composable
fun MessageSnackBar(text: String){
    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(R.drawable.ic_launcher_foreground) , contentDescription = null)
            Text(text = text,maxLines = 1, style = MaterialTheme.typography.titleMedium)
        }
    }

}