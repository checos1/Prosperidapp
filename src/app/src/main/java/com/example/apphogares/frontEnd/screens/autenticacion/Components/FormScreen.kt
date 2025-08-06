package com.example.apphogares.frontEnd.screens.autenticacion.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apphogares.frontEnd.theme.*
import kotlinx.serialization.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.frontEnd.MainViewModel

@Composable
fun Form() {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(30.dp,10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SelectDNI()
        Spacer(Modifier.height(20.dp))
        inputDNI()
        Spacer(Modifier.height(40.dp))
        AceptoTerminosRow()
        Spacer(Modifier.height(20.dp))
        buttomLogin()
    }

}


@Composable
fun AceptoTerminosRow() {
    val viewModel = hiltViewModel<MainViewModel>().loginViewModel
    val uriHandler = LocalUriHandler.current
    val linkText = "Acepto los términos, condiciones y la política de tratamiento de datos personales"
    // Construimos un AnnotatedString con estilo underline y una anotación de URL
    val annotatedLink = buildAnnotatedString {
        append(linkText)
        addStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline, fontSize = 12.sp ),
            start = 0,
            end = linkText.length
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://sapsdafchogaresprod.blob.core.windows.net/cntpsdafchogaresprod/TratamientoDatosPersonales/PoliticaTratamientoDatosPersonales.pdf", // <- tu URL aquí
            start = 0,
            end = linkText.length
        )
    }

    Row(
        modifier = with(Modifier) {
            fillMaxWidth()
        },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = viewModel.aceptado.value,
            onCheckedChange = { viewModel.aceptado.value = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Black,
                checkmarkColor = Color.White      // color de la palomita
            )
        )
        Spacer(modifier = Modifier.width(6.dp))
        // ClickableText nos permite reaccionar a clicks en ranges anotados
        ClickableText(
            text = annotatedLink,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            ),
            onClick = { offset ->
                // Si el click cae dentro de nuestra anotación "URL", la abrimos
                annotatedLink
                    .getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()
                    ?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )
    }
}


@Composable
fun buttomLogin() {
    val viewModel = hiltViewModel<MainViewModel>().loginViewModel
    var expanded by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .width(200.dp)
            .background( BackGroundTopLoginPlus, shape = RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = BackGroundTopLoginPlus, contentColor = BackGroundTopLoginPlus),
        onClick = {
            if (viewModel.typeDocument.value != "" && viewModel.document.value != "" && viewModel.aceptado.value){
                val utilities = utilities()
                viewModel.typeDocument.value = utilities.setTipoDocumento(viewModel.typeDocument.value)
                viewModel.connectDataLogin()
            }
            else expanded = true

        }
    ){
        Text("INGRESAR",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium
        )
    }

    //Valida que los campos sean diligenciados y informa con popUp al usuario
    if(expanded) {
        var title = "Ups, Error"
        var errorInfo = "Ingresa el Número de Documento"
        if(viewModel.typeDocument.value == "") errorInfo = "Selecciona el tipo de Documento"

        if(viewModel.aceptado.value == false) errorInfo = "Selecciona el  tratamiento de datos personales"

        AlertDialog(
            onDismissRequest = {},
            containerColor = Color.Transparent,
            modifier =  Modifier.height(220.dp),
            text = {
                Column(
                ){
                            Row(modifier = Modifier.weight(4f).background(Gray, shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)).padding(15.dp)){
                                Column(modifier = Modifier.weight(9f).fillMaxHeight()){
                                    Text(title,
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = White
                                    )
                                }
                                Column(modifier = Modifier.weight(1f).fillMaxHeight()){
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Icon Cerrar",
                                        Modifier.align(Alignment.End).clickable {
                                            expanded = false
                                        },
                                        tint = White,
                                    )
                                }
                            }

                            Row(modifier = Modifier.weight(6f)
                                .background(BackGroundTopLoginPlus, shape = RoundedCornerShape(0.dp, 0.dp,20.dp, 20.dp))
                                .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                                ){
                                Column(){
                                    Text(
                                        errorInfo,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Gray
                                    )
                                }
                            }
                        }
            },
            confirmButton = {
            },
            dismissButton = {
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun inputDNI() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel = hiltViewModel<MainViewModel>().loginViewModel

    // Track focus state
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    TextField(
        value = viewModel.document.value,
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            if (it.length <= 12) {
                val tmp = if (isNumber(it) || it.length == 0) it else viewModel.document.value
                viewModel.document.value = tmp
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        textStyle = MaterialTheme.typography.titleSmall,
        label = { Text("Número de documento", color = Gray) },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Gray,
            focusedIndicatorColor = BackgroundBottomInTo,  // This changes the underline when focused
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            containerColor = BackGroundTopLoginPlus,
            focusedLabelColor = Gray,
            unfocusedLabelColor = Gray,
            // Add these to ensure text color remains consistent
            //focusedTextColor = MaterialTheme.colorScheme.onSurface,
            //unfocusedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth(),
/*            .border(
                width = 1.dp,
                color = if (isFocused) BackgroundBottomInTo else BackGroundTopLoginPlus,
                shape = RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp)
            ),*/
        shape = RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp),
        interactionSource = interactionSource,
        enabled = true
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SelectDNI() {
    val viewModel = hiltViewModel<MainViewModel>().loginViewModel
    val utilities = utilities()
    val kc = LocalSoftwareKeyboardController.current
    kc?.hide()

    var expanded by remember { mutableStateOf(false) }
    val typesDni = utilities.GetTypesDni().map { it.description }

    // Track focus and press states
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    val isFocused: Boolean by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) {
            expanded = true
        }
    }

    TextField(
        value = viewModel.typeDocument.value,
        placeholder = { Text(viewModel.typeDocument.value, color = Gray) },
        readOnly = true,
        onValueChange = { viewModel.typeDocument.value = it },
        textStyle = MaterialTheme.typography.titleSmall,
        trailingIcon = {
            Row(
                modifier = Modifier
                    .background(BackgroundBottomInTo, shape = RoundedCornerShape(100.dp))
                    .width(30.dp).height(30.dp)
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("∨", style = MaterialTheme.typography.titleSmall)
            }
        },
        label = { Text("Tipo de documento", color = Gray) },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            containerColor = BackGroundTopLoginPlus,
        ),
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isFocused) BackgroundBottomInTo else BackGroundTopLoginPlus, // Use BackgroundBottomInTo when focused
                shape = RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp)
            ),
        shape = RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp),
        enabled = true
    )

    // Rest of the AlertDialog code remains unchanged
    if (expanded) {
        AlertDialog(
            onDismissRequest = {},
            containerColor = Color.Transparent,
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .background(Gray, shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                            .padding(15.dp)
                    ) {
                        Column(modifier = Modifier.weight(9f).fillMaxHeight()) {
                            Text(
                                "Tipo de Documento",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier.fillMaxWidth(),
                                color = White
                            )
                        }
                        Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar tipos identidad",
                                Modifier.align(Alignment.End).clickable {
                                    expanded = false
                                },
                                tint = White,
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .weight(9f)
                            .background(BackGroundTopLoginPlus, shape = RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            typesDni.forEachIndexed { indx, itemDni ->
                                Row(
                                    modifier = Modifier.weight(1f).fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = itemDni,
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        color = Gray,
                                        lineHeight = 25.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .clickable {
                                                expanded = false
                                                viewModel.typeDocument.value = itemDni
                                            },
                                    )
                                }
                                if (indx + 1 < typesDni.size) Divider(color = Gray)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}

fun isNumber(it:String): Boolean {
    return when(it.toLongOrNull())
    {
        null -> false
        else -> true
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContentV2(){}