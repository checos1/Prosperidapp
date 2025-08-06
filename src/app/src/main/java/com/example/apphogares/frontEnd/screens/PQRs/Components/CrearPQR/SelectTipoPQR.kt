package com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.models.PQR.PQRs
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.White

data class TipoPQRDescripcion(val tipo: String, val descripcion: String)

@Composable
fun SelectTipoPQR(crearPQRViewModel: CrearPQRViewModel = hiltViewModel()) {
    val utilities = utilities()
    val kc = LocalSoftwareKeyboardController.current
    kc?.hide()

    var expanded by remember { mutableStateOf(false) }
    val typesPQR = PQRs.tipos.sortedBy { it.orden }.map { it.tipo }

    var showHelpDialog by remember { mutableStateOf(false) }

    val tiposConDescripcion = listOf(
        TipoPQRDescripcion("Petición", "Es la petición o requerimiento de información, documentos o servicio."),
        TipoPQRDescripcion("Queja", "Es la manifestación de protesta, descontento o inconformidad que formula un peticionario en relación con la conducta o proceso realizado de manera presuntamente irregular."),
        TipoPQRDescripcion("Reclamo", "Es la manifestación mediante la cual se pone en conocimiento de las autoridades respectivas la suspensión injustificada o la prestación deficiente de un servicio."),
        TipoPQRDescripcion("Sugerencia", "Es la manifestación de una propuesta, idea o recomendación con el fin de mejorar el servicio o la gestión."),
        TipoPQRDescripcion("Denuncia", "Es la puesta en conocimiento ante una autoridad competente de una conducta posiblemente irregular, para que se adelante la correspondiente investigación disciplinaria, penal y/o fiscal."),
        TipoPQRDescripcion("Felicitación", "Es la manifestación de un ciudadano, grupo de valor o grupo de interés frente a la satisfacción de los servicios o trámites prestado por la Entidad."),
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) {
            expanded = true
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.asunto,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de solicitud", color = Gray) },
/*            placeholder = {
                Text(
                    "Tipo de solicitud",
                    color = Gray
                )
            },*/
            trailingIcon = {
                Row(
                    modifier = Modifier
                        .background(White, shape = RoundedCornerShape(100.dp))
                        .size(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("∨", style = MaterialTheme.typography.titleSmall)
                }
            },
            textStyle = MaterialTheme.typography.titleSmall,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = White
            ),
            interactionSource = interactionSource,
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color = White, shape = RoundedCornerShape(50))
                .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(50))
                .clickable { showHelpDialog = true },
                contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.HelpOutline,
                contentDescription = "Ayuda",
                tint = Gray
            )
        }
    }

    if (expanded) {
        AlertDialog(
            onDismissRequest = { expanded = false },
            containerColor = Color.Transparent,
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Gray, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .padding(15.dp)
                    ) {
                        Text(
                            "Tipo de Solicitud",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f),
                            color = White
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar tipos solicitud",
                            modifier = Modifier
                                .clickable { expanded = false },
                            tint = White
                        )
                    }

                    Column(
                        modifier = Modifier
                            .background(
                                BackGroundTopLoginPlus,
                                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                            )
                            .fillMaxWidth()
                    ) {
                        typesPQR.forEachIndexed { indx, itemPQR ->
                            Text(
                                text = itemPQR,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                color = Gray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        expanded = false
                                        crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.asunto = itemPQR
                                    }
                            )
                            if (indx + 1 < typesPQR.size) Divider(color = Gray)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
/*            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), // puedes usar `.heightIn(min = 400.dp)` si quieres forzar altura*/
            containerColor = Color.Transparent,
            text = {
                Column{
                    // Encabezado
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Gray, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Tipo de Solicitud",
                            color = White,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar tipos solicitud",
                            modifier = Modifier.clickable { showHelpDialog = false },
                            tint = White
                        )
                    }

                    // Contenido con descripciones
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                BackGroundTopLogin,
                                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                            )
                            .padding(vertical = 12.dp) // sin padding horizontal
                    ) {
                        tiposConDescripcion.forEach {
                            Column(modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = it.tipo,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = it.descripcion,
                                    color = Color.DarkGray,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { showHelpDialog = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp), // opcional: 16dp margen interno uniforme
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                        ) {
                            Text("Regresar", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }

                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

}
