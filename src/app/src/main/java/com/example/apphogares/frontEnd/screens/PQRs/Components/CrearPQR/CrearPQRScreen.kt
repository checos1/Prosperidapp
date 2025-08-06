package com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.AppHogaresAplication.Companion.context
import com.example.apphogares.backEnd.Services.ServicesSystem.FileManagement
import com.example.apphogares.backEnd.core.models.PQR.PQRs
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.AnexosPQR
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.DescriptionPQR
import com.example.apphogares.frontEnd.screens.PQRs.Components.General.encabezadoPQR
import com.example.apphogares.frontEnd.screens.PQRs.PQRViewModel
import com.example.apphogares.frontEnd.theme.BackGroundRuta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun  CrearPQRScreen(crearPQRViewModel: CrearPQRViewModel = hiltViewModel()) {

    val fileManagement = FileManagement()
    val pickFile = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            val nameFile = fileManagement.getFileName(uri, context)
            fileManagement.uploadFile(uri, crearPQRViewModel.selectedPQR.value.idPeticionAPP, nameFile!!, context)
            // Create a new copy of the PQR with updated file information
            crearPQRViewModel.updateArchivoPeticion(nameFile)

        }
    }

    when (crearPQRViewModel.estadoPantalla.value) {
        is CrearPQREstadoUI.MostrandoCamara -> {
            CamaraScreen(onDimiss = {
                crearPQRViewModel.mostrarFormulario()
                crearPQRViewModel.showThumbnailPhoto.value = true
            })
        }

        is CrearPQREstadoUI.MostrandoFilePicker -> {
            pickFile.launch(arrayOf("*/*"))
            crearPQRViewModel.mostrarFormulario()
        }

        is CrearPQREstadoUI.MostrarFormulario -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = BackGroundTopLogin)
                        .verticalScroll(rememberScrollState()),

                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    encabezadoPQR("PQRSDF", "Crear Petición")
                    Divider(modifier = Modifier.padding(0.dp).background(BackGroundTopLogin), color = Color.Gray, thickness = 1.dp)
                    /*            SelectProgramaPQR(
                                    programas = ProgramasPQR.programas,
                                    onClick = { programa ->
                                        PQRiewModel.updateProgramaPeticion(programa)
                                    }
                                )*/
                    SelectTipoPQR()
/*                    TipoPQRDropdown(
                        PQRs.tipos,
                        crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.asunto,
                        onTipoSelected = { tipo ->
                            crearPQRViewModel.updateTipoPeticion(tipo)
                        }
                    )*/
/*                    TipoPQRGrid(
                        PQRs.tipos,
                        onClick = { tipoPeticion ->
                            crearPQRViewModel.updateTipoPeticion(tipoPeticion)
                        }
                    )*/
                    DescriptionPQR(
                        onDescriptionChange = { descripcion ->
                            crearPQRViewModel.updateCuerpoPeticion(descripcion)
                        }
                    )

                    AttachDocumentsPQR(
                        onAttachPhoto = { photo ->
                            crearPQRViewModel.mostrarCamara()
                            //crearPQRViewModel.showCamara.value = true
                        },
                        onAttachFile = { file ->
                            crearPQRViewModel.mostrarFilePicker()
                            //crearPQRViewModel.showFilePick.value = true
                        },
                        onPQRCreated = {
                            AppHogaresAplication.funNav(RoutesNav.PQRScreen.route)
                        }
                    )
/*                    Column(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        key(crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.archivoPeticion) {
                            AnexosPQR(crearPQRViewModel.selectedPQR.value)
                        }
                    }*/

                }
                if (crearPQRViewModel.isCreating.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Gray)
                    }
                }
            }
        }
    }
}



@Preview
@Composable
fun CrearPQRScreenPreview() {
    CrearPQRScreen()
}