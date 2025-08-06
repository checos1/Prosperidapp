package com.example.apphogares.frontEnd.screens.PQRs.Components.General

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR.CrearPQRViewModel
import com.example.apphogares.frontEnd.theme.Gray
import com.google.gson.Gson

@Composable
fun  AnexosPQR(item: PQRDocument) {

    println("AnexosPQR item: ${Gson().toJson(item)}")
    if (item.blqInformacionPeticion.fotoPeticion.isEmpty() && item.blqInformacionPeticion.archivoPeticion.isEmpty()) {
        return
    }
    if (item.blqInformacionPeticion.fotoPeticion == "" && item.blqInformacionPeticion.archivoPeticion == "") {
        return
    }
    println("AnexosPQR : ${item.blqInformacionPeticion.fotoPeticion} - ${item.blqInformacionPeticion.archivoPeticion}")
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Anexos:", fontSize = 14.sp, modifier = Modifier.padding(top=5.dp), color = Gray, fontWeight = FontWeight.Bold)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        if(item.blqInformacionPeticion.fotoPeticion != null && item.blqInformacionPeticion.fotoPeticion.isNotEmpty()) {
            Text(item.blqInformacionPeticion.fotoPeticion, fontSize = 14.sp, color = Gray, modifier = Modifier.padding(5.dp))
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        if(item.blqInformacionPeticion.archivoPeticion != null && item.blqInformacionPeticion.archivoPeticion.isNotEmpty()) {
            Text(item.blqInformacionPeticion.archivoPeticion, fontSize = 14.sp, color = Gray, modifier = Modifier.padding(5.dp))
        }
    }
}