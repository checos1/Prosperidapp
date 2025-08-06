package com.example.apphogares.frontEnd.screens.Comunicaciones.Componentes

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.R
import com.example.apphogares.frontEnd.screens.Comunicaciones.comunicacionesViewModel
import com.example.apphogares.frontEnd.theme.Gray
import androidx.core.net.toUri

@Composable
fun BloqueContactoYRedes(comunicacionesViewModel: comunicacionesViewModel = hiltViewModel()) {
    val context = LocalContext.current

        val comunicacion = comunicacionesViewModel.comunicacion.value
        if (comunicacion == null) {
            Toast.makeText(context, "Información de contacto no disponible", Toast.LENGTH_SHORT).show()
            return
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, end = 10.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Teléfonos
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.telefono),
                    contentDescription = "Teléfono",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp)
                )
                Column {
                    Text(text = comunicacion.telefono1 ?: "", fontSize = 14.sp, color = Gray)
                    Text(text = comunicacion.telefono ?: "", fontSize = 14.sp, color = Gray)
                }
            }

            // Redes sociales
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    val rawNumber = comunicacion.cuenta_whatsApp.orEmpty().replace(" ", "")
                    context.startActivity(
                        // on below line we are opening the intent.
                        Intent(
                            // on below line we are calling
                            // uri to parse the data
                            Intent.ACTION_VIEW,
                            java.lang.String.format(
                                "https://api.whatsapp.com/send?phone=%s&text=%s",
                                rawNumber,
                                ""
                            ).toUri()
                        )
                    )
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.whatsapp),
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(30.dp)
                    )
                }
                // WhatsApp
/*                IconButton(onClick = {
                    val rawNumber = comunicacion.cuenta_whatsApp.orEmpty().replace(" ", "")
                    if (rawNumber.isBlank()) {
                        Toast.makeText(context, "Número de WhatsApp no disponible", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }

                    val pm = context.packageManager
                    val url = "https://wa.me/57$rawNumber"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                    try {
                        pm.getPackageInfo("com.whatsapp", 0)
                        context.startActivity(intent)
                    } catch (e1: PackageManager.NameNotFoundException) {
                        try {
                            pm.getPackageInfo("com.whatsapp.w4b", 0) // WhatsApp Business
                            context.startActivity(intent)
                        } catch (e2: PackageManager.NameNotFoundException) {
                            Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.whatsapp),
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(30.dp)
                    )
                }*/


                // X (Twitter)
                IconButton(onClick = {
                    val url = comunicacion.cuenta_x.orEmpty()
                    if (url.isBlank()) {
                        Toast.makeText(context, "Cuenta de X (Twitter) no disponible", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }

                    try {
                        context.packageManager.getPackageInfo("com.twitter.android", 0)
                    } catch (_: PackageManager.NameNotFoundException) {
                        Toast.makeText(context, "La app de X (Twitter) no está instalada", Toast.LENGTH_SHORT).show()
                    }

                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }) {
                    Image(painter = painterResource(id = R.drawable.twitter), contentDescription = "X (Twitter)", modifier = Modifier.size(30.dp))
                }

                // Instagram
                IconButton(onClick = {
                    val url = comunicacion.cuenta_instagram.orEmpty()
                    if (url.isBlank()) {
                        Toast.makeText(context, "Cuenta de Instagram no disponible", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }

                    try {
                        context.packageManager.getPackageInfo("com.instagram.android", 0)
                    } catch (_: PackageManager.NameNotFoundException) {
                        Toast.makeText(context, "La app de Instagram no está instalada", Toast.LENGTH_SHORT).show()
                    }

                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }) {
                    Image(painter = painterResource(id = R.drawable.instagram), contentDescription = "Instagram", modifier = Modifier.size(30.dp))
                }

                // Facebook
                IconButton(onClick = {
                    val url = comunicacion.cuenta_facebook.orEmpty()
                    if (url.isBlank()) {
                        Toast.makeText(context, "Cuenta de Facebook no disponible", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }

                    try {
                        context.packageManager.getPackageInfo("com.facebook.katana", 0)
                    } catch (_: PackageManager.NameNotFoundException) {
                        Toast.makeText(context, "La app de Facebook no está instalada", Toast.LENGTH_SHORT).show()
                    }

                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }) {
                    Image(painter = painterResource(id = R.drawable.facebook), contentDescription = "Facebook", modifier = Modifier.size(30.dp))
                }

                // TikTok
                IconButton(onClick = {
                    val url = comunicacion.cuenta_tiktok.orEmpty()
                    if (url.isBlank()) {
                        Toast.makeText(context, "Cuenta de TikTok no disponible", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }

                    try {
                        context.packageManager.getPackageInfo("com.zhiliaoapp.musically", 0)
                    } catch (_: PackageManager.NameNotFoundException) {
                        Toast.makeText(context, "La app de TikTok no está instalada", Toast.LENGTH_SHORT).show()
                    }

                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }) {
                    Image(painter = painterResource(id = R.drawable.tiktok), contentDescription = "TikTok", modifier = Modifier.size(30.dp))
                }
            }
        }
}
