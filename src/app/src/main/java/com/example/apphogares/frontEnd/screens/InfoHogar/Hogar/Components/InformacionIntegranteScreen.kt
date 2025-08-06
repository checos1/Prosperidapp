package com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.models.hogarMain.Integrante
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus

@Composable
fun InformacionIntegranteScreen(
    integrante: Integrante
){
    val utilities = utilities()
    val tipoDocumento = utilities.GetTypesDni().find { it.id == integrante.tipoDocumento }!!.description
    val listaProgramas = integrante.nombreProgramas.split(",")

   Column(modifier = Modifier.padding(16.dp)
   ){
       ListaProgramasIntegrante(programas = listaProgramas)

        Text(
            text = "Tipo de documento",
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$tipoDocumento",
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Número de documento",
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = integrante.numeroDocumento,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Sexo",
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = integrante.sexo.replace("2", "Femenino").replace("1", "Masculino"),
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Fecha de nacimiento",
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = integrante.fechaNacimiento,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}