package com.example.apphogares.frontEnd.screens.Components.alerts

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UpdateDialog(
    onDismiss: () -> Unit,
    onUpdateClick: () -> Unit
) {
    var dialogOpen by remember { mutableStateOf(true) }

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = {
                dialogOpen = false
                onDismiss()
            },
            title = {
                Text(text = "Actualización Disponible")
            },
            text = {
                Text(text = "Una nueva versión de la app está disponible. Actualice para obtener las últimas funciones y mejoras.")
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            dialogOpen = false
                            onDismiss()
                        }
                    ) {
                        Text(text = "Después")
                    }

                    TextButton(
                        onClick = {
                            dialogOpen = false
                            onUpdateClick()
                        }
                    ) {
                        Text(text = "Actualizar Ahora")
                    }
                }
            }
        )
    }
}
