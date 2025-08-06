package com.example.apphogares.frontEnd.screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun InputPrompt(
    onInputEntered: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Enter Your Name") },
        shape = RoundedCornerShape(10, 10, 10, 10),
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Cancel")
                }
                TextButton(
                    onClick = {
                        onInputEntered(name)
                        onDismiss()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "OK")
                }
            }
        },
        text = {
            TextField(
                value = name,
                onValueChange = { newName -> name = newName },
                modifier = Modifier.padding(16.dp).background(BackGroundTopLogin),
                label = { Text(text = "Name") }
            )
        }
    )
}
