package com.example.apphogares.frontEnd.screens.Components.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray


//Layout
@Composable
fun AlertSimple(
    modifier: Modifier = Modifier,
    title: String,
    textoButonAceptar: String,
    onClickCerrar: ()  -> Unit,
    onClickButonAceptar: ()  -> Unit,
    contentDialogo: @Composable () -> Unit
) {
    var permisos by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(10, 10, 10, 10),
    ) {
        Column(
            modifier
                .background(BackGroundTopLogin))
        {
            Column(
                modifier = Modifier
                    .padding(0.dp)
                    .background(Gray)

            ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        Modifier
                            .align(Alignment.End)
                            .background(Gray)
                            .padding(8.dp)
                            .clip(shape = RoundedCornerShape(size = 12.dp))
                            .clickable { onClickCerrar },
                        tint = BackGroundTopLogin,
                    )
                    Text(
                        text = title,
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 15.dp)
                            .fillMaxWidth()
                            .background(Gray),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = BackGroundTopLogin,
                        textAlign = TextAlign.Center
                    )

            }
                Spacer(modifier = Modifier.height(10.dp))


            contentDialogo()

            ButtonAccept(
                text = textoButonAceptar,
                onClick = onClickButonAceptar
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewDialogSimple() {
    AlertSimple(
        title = "Está seguro de cerrar la sesión?",
        textoButonAceptar = "Aceptar",
        onClickCerrar = {},
        onClickButonAceptar = {},
        contentDialogo = {

        }
    )
}
