package com.example.apphogares.frontEnd.screens.PQRs.Components.General

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR.CrearPQRViewModel
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.White

@Composable
fun DescriptionPQR(
    crearPQRViewModel: CrearPQRViewModel = hiltViewModel(),
    onDescriptionChange: (String) -> Unit = {}
) {
    var textCampo by remember { mutableStateOf(TextFieldValue(crearPQRViewModel.cuerpoPeticion.value)) }
    var telefono by remember { mutableStateOf(TextFieldValue(crearPQRViewModel.selectedPQR.value.blqDatosContactoSolicitante.telefono)) }
    var correo by remember { mutableStateOf(TextFieldValue(crearPQRViewModel.selectedPQR.value.blqDatosContactoSolicitante.correoElectronico)) }

    var isEmailValid by remember { mutableStateOf(true) }
    var telefonoError by remember { mutableStateOf(false) }

    val maxChars = 2000
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Cuéntanos sobre la solicitud",
            color = Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(White)
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
        ) {
            BasicTextField(
                value = textCampo,
                onValueChange = {
                    if (it.text.length <= maxChars) {
                        textCampo = it
                        onDescriptionChange(it.text)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                cursorBrush = SolidColor(Color.Gray),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions.Default
            )

            if (scrollState.maxValue > 0 && scrollState.value < scrollState.maxValue) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(12.dp)
                        .align(Alignment.CenterEnd)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, Color(0x22000000))
                            )
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${textCampo.text.length} / $maxChars",
                fontSize = 12.sp,
                color = Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Datos de contacto",
            color = Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Teléfono
            BasicTextField(
                value = telefono,
                onValueChange = {
                    val input = it.text
                    val filtered = input.filter { char -> char.isDigit() }.take(10)
                    telefono = TextFieldValue(
                        text = filtered,
                        selection = TextRange(filtered.length)
                    )
                    crearPQRViewModel.selectedPQR.value.blqDatosContactoSolicitante.telefono = filtered
                    telefonoError = input.any { !it.isDigit() } || input.length > 10
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(White)
                    .border(
                        1.dp,
                        if (telefonoError) Color.Red else Color.LightGray,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                cursorBrush = SolidColor(Color.Gray),
                decorationBox = { innerTextField ->
                    Box {
                        if (telefono.text.isEmpty()) {
                            Text("Celular", color = Color.Gray, fontSize = 14.sp)
                        }
                        innerTextField()
                    }
                }
            )

            // Correo electrónico
            BasicTextField(
                value = correo,
                onValueChange = {
                    val cleanText = it.text.trim()
                    correo = TextFieldValue(
                        text = cleanText,
                        selection = TextRange(cleanText.length)
                    )
                    crearPQRViewModel.selectedPQR.value.blqDatosContactoSolicitante.correoElectronico = cleanText
                    isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(cleanText).matches()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(White)
                    .border(
                        1.dp,
                        if (isEmailValid) Color.LightGray else Color.Red,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                cursorBrush = SolidColor(Color.Gray),
                decorationBox = { innerTextField ->
                    Box {
                        if (correo.text.isEmpty()) {
                            Text("Correo electrónico", color = Color.Gray, fontSize = 14.sp)
                        }
                        innerTextField()
                    }
                }
            )
        }

        if (telefonoError) {
            Text(
                text = "Ingrese solo números. Máximo 10 dígitos.",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        if (!isEmailValid) {
            Text(
                text = "El correo electrónico no es válido",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}
