package com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.core.models.PQR.PQRs
import com.example.apphogares.backEnd.core.models.PQR.TipoPQR
import com.example.apphogares.frontEnd.screens.PQRs.PQRViewModel
import com.example.apphogares.frontEnd.theme.White

@Composable
fun TipoPQRGrid(tipos: List<TipoPQR>,
                PQRiewModel: PQRViewModel = hiltViewModel(),
                onClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // We assume that there will be two rows as shown in the image
        tipos.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                rowItems.forEach { item ->
                    Button(
                        onClick = { onClick(item.tipo) },
                        modifier = Modifier
                            //.weight(1f)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = item.tipoColor)
                    ) {
                        Text(item.tipo, color = White, fontSize = 12.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Normal, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(3.dp))
                }
                if (rowItems.size < 3) {
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipoPQRGridPreview() {

    PQRs.tipos
    TipoPQRGrid(PQRs.tipos) { tipo ->
        println("Tipo seleccionado: $tipo")
    }
}