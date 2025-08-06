package com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.AppHogaresAplication.Companion.context
import com.example.apphogares.R
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.frontEnd.screens.Components.layouts.nonScaledSp
import com.example.apphogares.frontEnd.theme.*
import android.os.Environment
import androidx.compose.ui.platform.LocalUriHandler
import java.io.File
import android.widget.Toast

@Composable
fun AttachDocumentsPQR(
    crearPQRViewModel: CrearPQRViewModel = hiltViewModel(),
    onAttachPhoto: (String) -> Unit = {},
    onAttachFile: (MutableState<PQRDocument>) -> Unit = {},
    onPQRCreated: () -> Unit = {}
) {
    var imageToPreview by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val fotoUrl = remember(crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.fotoPeticion) {
        val fileName = crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.fotoPeticion
        if (!fileName.isNullOrBlank()) {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                fileName
            )
            file.toURI().toString()
        } else ""
    }


    val archivoUrl =  ObtenerMediaUrl(
        crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.archivoPeticion,
        crearPQRViewModel.selectedPQR.value.idPeticionAPP
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Desea anexar documento",
            color = Gray,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Íconos y miniatura de foto
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = {
                        onAttachPhoto(crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.fotoPeticion)
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.camera_viewfinder_svgrepo_com),
                        contentDescription = "Cámara",
                        modifier = Modifier.size(40.dp),
                        tint = Gray
                    )
                }

/*                val fotoUrl = remember(crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.fotoPeticion) {
                    ObtenerMediaUrl(
                        crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.fotoPeticion,
                        crearPQRViewModel.selectedPQR.value.idPeticionAPP
                    )
                }*/

                if (!fotoUrl.isNullOrBlank()) {
                    ImageThumbnail(
                        url = fotoUrl,
                        onClick = { imageToPreview = fotoUrl },
                        isImage = true
                    )
                }
            }

            // Íconos y miniatura de archivo
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = {
                        onAttachFile(crearPQRViewModel.selectedPQR)
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.file_arrow_up_alt_svgrepo_com),
                        contentDescription = "Archivo",
                        modifier = Modifier.size(40.dp),
                        tint = Gray
                    )
                }

/*                val archivoUrl = remember(crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.archivoPeticion) {
                    ObtenerMediaUrl(
                        crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.archivoPeticion,
                        crearPQRViewModel.selectedPQR.value.idPeticionAPP
                    )
                }*/
                //val archivoUrl = crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.archivoPeticion
                if (!archivoUrl.isNullOrBlank()) {
                    println("Archivo URL >: $archivoUrl")
                    ImageThumbnail(
                        url = archivoUrl,
                        onClick = {
                            if (archivoUrl.endsWith(".pdf", true)) {
/*                                if (archivoUrl.startsWith("http", true)) {
                                    try {
                                        uriHandler.openUri(archivoUrl)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "No se pudo abrir el archivo PDF.", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(context, "URL inválida para archivo PDF.", Toast.LENGTH_SHORT).show()
                                }*/
                            } else {
                                imageToPreview = archivoUrl
                            }
                        },
                        isImage = archivoUrl.endsWith(".jpg", true) ||
                                archivoUrl.endsWith(".png", true) ||
                                archivoUrl.endsWith(".jpeg", true)
                    )
                }

            }

            // Botón ENVIAR PQR
            Button(
                onClick = { crearPQRViewModel.AddPQR(onPQRCreated) },
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BackGroundTopLoginPlus)
            ) {
                Text(
                    text = "ENVIAR PQR",
                    style = MaterialTheme.typography.titleSmall,
                    color = Gray

                )
            }
        }

        // Visor ampliado
        if (imageToPreview != null) {
            Dialog(onDismissRequest = { imageToPreview = null }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(imageToPreview),
                        contentDescription = "Vista previa",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }
    }



    // Visor ampliado
    if (imageToPreview != null) {
        Dialog(onDismissRequest = { imageToPreview = null }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageToPreview),
                    contentDescription = "Vista previa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}


@Composable
private fun ImageThumbnail(
    url: String,
    onClick: () -> Unit,
    isImage: Boolean
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(Color.LightGray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        if (isImage) {
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = "Miniatura",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_pdf_file),
                contentDescription = "PDF",
                tint = Color.Red,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

fun ObtenerMediaUrl(nombreArchivo: String, idPeticionAPP: String): String {
    return if (nombreArchivo.isNotBlank())
        AppHogaresAplication.buildConfigApp.baseURLStorage +
                AppHogaresAplication.buildConfigApp.containerName +
                "/$idPeticionAPP/$nombreArchivo" //+ AppHogaresAplication.buildConfigApp.ACCESS_TOKEN
    else ""
}
