package com.example.apphogares.frontEnd.screens.PQRs.Components.InformacionPQR

import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.platform.LocalUriHandler
import com.example.apphogares.R

import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI

import com.example.apphogares.frontEnd.theme.BackGroundRuta
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo

import com.example.apphogares.frontEnd.theme.White
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.backEnd.core.models.PQR.PQRs
import com.example.apphogares.backEnd.core.models.PQRs.PQR
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.AnexosPQR
import com.example.apphogares.frontEnd.screens.PQRs.PQRViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.Gray
import java.io.File

@Composable
fun DetailsPQR(item: PQRDocument, PQRiewModel: PQRViewModel = hiltViewModel()) {
    val uriHandler = LocalUriHandler.current
    val showRespuesta = remember { mutableStateOf(false) }
    val utils = utilities()
    val fecha = utils.FormatearFecha(item.response.fechaRadicacion)
    val hora = utils.FormatearHora(item.response.fechaRadicacion)
    var imageToPreview by remember { mutableStateOf<String?>(null) }

    val fotoUrl = remember(item.blqInformacionPeticion.fotoPeticion) {
        val fileName = item.blqInformacionPeticion.fotoPeticion
        if (!fileName.isNullOrBlank()) {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                fileName
            )
            file.toURI().toString()
        } else ""
    }

    val archivoUrl = remember(item.blqInformacionPeticion.urlArhivoPeticion) {
        item.blqInformacionPeticion.urlArhivoPeticion ?: ""
    }

    println("DetailsPQR - item: $item")
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(BackGroundTopLogin)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Tipo:",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 5.dp),
                color = Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(PQRiewModel.GetTipoColor(item.blqInformacionPeticion.asunto)),
                contentAlignment = Alignment.CenterStart


            ) {
                Text(
                    item.blqInformacionPeticion.asunto,
                    fontSize = 14.sp,
                    color = White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Estado:",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 5.dp),
                color = Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(PQRiewModel.GetStatusColor(item.consultarPQRResponse.resultado.estado)),
                contentAlignment = Alignment.CenterStart


            ) {
                Text(
                    if (item.consultarPQRResponse.resultado.estado == null) "" else item.consultarPQRResponse.resultado.estado.uppercase(),
                    fontSize = 14.sp,
                    color = White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Fecha:",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 5.dp),
                color = Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "$fecha - Hora: $hora",
                fontSize = 14.sp,
                color = Gray,
                modifier = Modifier.padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Radicado:",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 5.dp),
                color = Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                if (item.response.radicado == null || item.response.radicado == "") "XX-XXXXXXX" else item.response.radicado,
                fontSize = 14.sp,
                color = Gray,
                modifier = Modifier.padding(5.dp)
            )
        }
        if (item.blqInformacionPeticion.archivoPeticion.isNotBlank() ||
            item.blqInformacionPeticion.fotoPeticion.isNotBlank()
        ) {

            Text(
                "Documentos adjuntos:",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                color = Gray,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Miniatura de foto si existe
                item.blqInformacionPeticion.fotoPeticion.takeIf { it.isNotBlank() }?.let { foto ->
                    //val fotoUrl = ObtenerMediaUrl(foto, item.idPeticionAPP)
                    ImageThumbnail(
                        url = fotoUrl,
                        onClick = { imageToPreview = fotoUrl },
                        isImage = true,
                        modifier = Modifier.size(80.dp)
                    )
                }

                // Miniatura de archivo si existe
                item.blqInformacionPeticion.archivoPeticion.takeIf { it.isNotBlank() }
                    ?.let { archivo ->
                        //val archivoUrl = ObtenerMediaUrl(archivo, item.idPeticionAPP)
                        ImageThumbnail(
                            url = archivoUrl,
                            onClick = {
                                if (archivo.endsWith(".pdf", true)) {
                                    //uriHandler.openUri(archivoUrl)
                                } else {
                                    imageToPreview = archivoUrl
                                }
                            },
                            isImage = archivo.endsWith(".jpg", true) ||
                                    archivo.endsWith(".png", true) ||
                                    archivo.endsWith(".jpeg", true),
                            modifier = Modifier.size(80.dp)
                        )
                    }
            }

            /*        Row {
            //Text("Solicito se me informe sobre el estado de afiliación a el programa unidos, mi documentotode identidad es 87.890.476 de Chiquinquira, no puedo solicitar el subsidio de vivienda y me indican que tengo que llevar el soporte de afiliación a el programa.\n\nAgradezco su pronta respuesta.", color = White, fontSize = 16.sp)
            Text(item.asunto, color = Gray, fontSize = 16.sp)
        }*/
            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                modifier = Modifier.padding(0.dp).background(BackGroundTopLogin),
                color = Color.Gray,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Gray)
                    .padding(16.dp)
            ) {
                if (item.consultarPQRResponse.resultado.archivos.size == 0) {
                    Text(
                        text = "Tu solicitud está siendo gestionada. Se dará una respuesta lo más pronto posible.",
                        color = White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Justify
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "¡Tu solicitud ha sido atendida! Ya puedes consultar y descargar el archivo con la respuesta. Da clic en el botón Descargar.",
                            color = White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Button(
                            onClick = {
                                // Abrir el enlace
                                uriHandler.openUri(item.consultarPQRResponse.resultado.archivos[0])
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BackGroundTopLoginPlus,
                                contentColor = Gray
                            ),
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                        ) {
                            Text("Descargar")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Send Button
                    Button(
                        onClick = {
                            AppHogaresAplication.funNav(RoutesNav.PQRScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = White
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(BackGroundTopLoginPlus, shape = RoundedCornerShape(50.dp)),
                    ) {
                        Text(
                            text = "Regresar",
                            style = MaterialTheme.typography.titleSmall,
                            color = BackGroundRuta
                        )
                    }
                }
            }

            if (imageToPreview != null) {
                Dialog(onDismissRequest = { imageToPreview = null }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Image(
                                painter = rememberAsyncImagePainter(imageToPreview),
                                contentDescription = "Vista previa ampliada",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { imageToPreview = null },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Cerrar")
                            }
                        }
                    }
                }
            }
            /*        Spacer(modifier = Modifier.weight(1f))

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Camera Icon and text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier.padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ){
                // Files Icon and text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {  Handle file picker click  }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.file_arrow_up_alt_svgrepo_com),
                        contentDescription = "Archivos",
                        modifier = Modifier.size(48.dp),
                        tint = Gray
                    )
                    Text("Archivos", color = White)
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Send Button
                Button(
                    onClick = {
                        showRespuesta.value = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = White),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(BackGroundTopLoginPlus, shape = RoundedCornerShape(50.dp)),
                ) {
                    Text(text = "VER RESPUESTA", style = MaterialTheme.typography.titleSmall, color = BackGroundRuta)
                }
            }
        }
    }*/



            if (showRespuesta.value) {
                Dialog(onDismissRequest = { showRespuesta.value = false }) {
                    CustomDialogUI(
                        openDialogCustom = showRespuesta,
                        title = "Respuesta no Disponible",
                        onClick = { showRespuesta.value = false },
                        modifier = Modifier.clip(RoundedCornerShape(0, 0, 10, 10))
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Tu solicitud no ha recibido respuesta. Estamos trabajando diligentemente para atender tu petición lo más pronto posible. Te invitamos a que sigas consultando.",
                                color = Color.Black,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Versión mejorada del componente ImageThumbnail
@Composable
fun ImageThumbnail(
    url: String,
    onClick: () -> Unit,
    isImage: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(Color.LightGray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        if (isImage) {
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = "Miniatura de documento",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pdf_file),
                    contentDescription = "PDF",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "PDF",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// Función para obtener la URL del archivo (debe estar en el mismo archivo o en un lugar accesible)
fun ObtenerMediaUrl(nombreArchivo: String, idPeticionAPP: String): String {
    return if (nombreArchivo.isNotBlank())
        AppHogaresAplication.buildConfigApp.baseURLStorage +
                AppHogaresAplication.buildConfigApp.containerName +
                "/$idPeticionAPP/$nombreArchivo" + AppHogaresAplication.buildConfigApp.ACCESS_TOKEN
    else ""
}


/*
@Preview
@Composable
fun DetailsPQRPreview() {
    DetailsPQR(PQRs.items[0])
}*/
