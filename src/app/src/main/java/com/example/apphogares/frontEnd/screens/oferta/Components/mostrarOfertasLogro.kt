package com.example.apphogares.frontEnd.screens.oferta.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apphogares.backEnd.core.models.hogarMain.Oferta
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.screens.oferta.Opciones
import com.example.apphogares.frontEnd.screens.oferta.infoOfertaViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.White

@Composable
fun mostrarOfertasLogro(listaOfertas: List<Oferta>,
                        onSelectOferta: (idOferta: Int) -> Unit
)   {
    val listaOfertasLogro = listaOfertas
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(listaOfertasLogro) { oferta ->
            InformationDetailOferta(
                title = oferta.nombreOferta,
                backgroundColorMainBox = Color.Transparent ,
                backgroundColorDetailBox = Color.Transparent,
                foregroundColorMainBox = White,
            ) {

                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        text = if(oferta.descripcionOferta.length > 51)  "${oferta.descripcionOferta}".substring(0, 51) +" ..." else "${oferta.descripcionOferta}",
                        color = BackGroundTopLogin,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                    )
                    Text(
                        text = "Logro : ${oferta.nombreLogro}",
                        color = White,
                        modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text ="Oferente : "+ oferta.nombreOferente,
                        color = White,
                        modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Fecha de Inicio: ${oferta.fechaInicio}",
                        color = White,
                        modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Fecha Final: ${oferta.fechaFin}",
                        color = White,
                        modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.titleMedium
                    )
/*                    Text(
                        text = "Integrante: ${oferta.nombreIntegrante}",
                        color = White,
                        modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.titleMedium
                    )*/

                    ButtonAccept(
                        text = "Ver mas",
                        onClick = {
                            onSelectOferta(oferta.idOferta!!)
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                }

            }
            Spacer(modifier = Modifier.padding(5.dp))

        }
    }
}