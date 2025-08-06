package com.example.apphogares.frontEnd.screens.Informate.Componentes

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun InteraccionesPublicacion(
    likesIniciales: Int,
    compartidosIniciales: Int,
    mensajeCompartir: String = "Consulta esta publicación de Prosperidad Social.",
    onLike: (() -> Unit)? = null,
    onCompartir: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var likes by remember { mutableStateOf(likesIniciales) }
    var compartidos by remember { mutableStateOf(compartidosIniciales) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$compartidos compartido", color = Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "$likes likes", color = Gray, fontSize = 12.sp)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        likes++
                        onLike?.invoke()
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Me gusta", tint = Gray, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Me gusta", fontSize = 12.sp, color = Gray)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        compartidos++
                        onCompartir?.invoke()

                        // Compartir nativo
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, mensajeCompartir)
                        }
                        val chooser = Intent.createChooser(intent, "Compartir publicación")
                        context.startActivity(chooser)

                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Compartir", tint = Gray, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Compartir", fontSize = 12.sp, color = Gray)
            }
        }
    }
}
