package com.example.apphogares.frontEnd.screens.Components.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.LightBlue
import com.example.apphogares.frontEnd.theme.RedLight


@Composable
fun AlertDialogSample(show:Boolean, onDismiss: () -> Unit, onConfirm:() -> Unit) {
    //var expanded by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = {onDismiss()},
        containerColor = LightBlue,
        title = { Box(modifier = Modifier.fillMaxWidth()){
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                Modifier
                    .align(Alignment.TopEnd)
                    .background(RedLight)
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .clickable {
                        onDismiss()
                    },
                tint = Color.White
            )
//             }
            Text("Titulo", color = Color(0xFF0F3C96))
        }
        },
        text = {
            Column(){


                Box(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier
                            .align(Alignment.TopEnd)
                            .width(350.dp)
                    ){
                        Text(
                            "Hemos notificado su asistencia, es importante que lleves ese dia el documento de identidad ",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFF0F3C96)
                        )
                    }
                }

            }
        },
        confirmButton = {
            TextButton(onClick = { }) {
                //TextButton(onClick = {onConfirm() }) {
                //Text(text = "Porque es importante la cedula")
            }
        },
        dismissButton = {
            TextButton(onClick = {onDismiss() },colors = ButtonDefaults.buttonColors(RedLight)) {
                Text(text = "Porque es importante la cedula", modifier = Modifier.fillMaxWidth())
            }
        }
    )
}

@Composable
@Preview
fun AlertDialogSamplePreview() {
    AlertDialogSample(show = true, onDismiss = {}, onConfirm = {})
}