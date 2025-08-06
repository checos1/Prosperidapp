package com.example.apphogares.frontEnd.screens.Components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray


@Composable
fun PopupInput(
    modifier: Modifier = Modifier,
    title: String,
    onInputEntered: (String) -> Unit,
    onDismiss: () -> Unit
){
    var name by remember { mutableStateOf("") }
    Card(
        shape = RoundedCornerShape(10, 10, 10, 10),
    ) {
        Column(
            modifier
                .background(BackGroundTopLogin)
        ){
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
                        .padding(16.dp)
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .clickable { onDismiss() },
                    tint = BackGroundTopLogin
                )
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                        .background(Gray),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = BackGroundTopLogin

                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            TextField(
                value = name,
                onValueChange = { newName -> name = newName },
                modifier = Modifier.padding(16.dp).background(BackGroundTopLogin),
                label = { Text(text = title, color = Color.Black) }
            )

            ButtonAccept(
                text = "Aceptar",
                onClick = {
                    onInputEntered(name)
                    onDismiss()
                    name = ""
                }
            )
        }
    }
}
