package com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.backEnd.core.models.PQR.TipoPQR
import androidx.compose.material.icons.filled.HelpOutline

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPQRDropdown(
    tipos: List<TipoPQR>,
    selectedTipo: String,
    onTipoSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = remember(selectedTipo) {
        tipos.find { it.tipo == selectedTipo }?.tipo ?: "Tipo de solicitud"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(1f)
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                //label = { Text("Tipo de solicitud") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo.tipo, fontWeight = FontWeight.Normal, fontSize = 14.sp) },
                        onClick = {
                            onTipoSelected(tipo.tipo)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Filled.HelpOutline,
            contentDescription = "Ayuda",
            modifier = Modifier
                .clickable {
                    // Aquí puedes abrir un diálogo de ayuda o una explicación
                }
        )
    }
}
