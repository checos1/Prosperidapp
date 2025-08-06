package com.example.apphogares.frontEnd.screens.Version


import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.R
import com.example.apphogares.frontEnd.MainViewModel
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.buttons.ButtonAccept
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration


@Composable
fun VersionScreen(versionViewModel: versionViewModel = hiltViewModel()){
    // Obtén el contexto
    val version = showDialog()
    val copyright: Char = '\u00A9'

    val mensaje ="Nuestra aplicación está dedicada a brindar apoyo a los hogares en su proceso de formación y superación de la pobreza. " + "Esta App utiliza como fuente, los datos de Sisben IV.\n"
    val globalState by AppHogaresAplication.GlobalState.globalState.collectAsState()

    if (globalState.id != "") {
        AppHogaresAplication.screenActual = RoutesNav.wellcomeScreen.route
        AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
    }

    Column(modifier = Modifier
        .background(color = BackGroundTopLogin)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Image(
            painter = painterResource(id = R.drawable.prosperidad_logo),
            contentDescription = "", modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))
        Text(text = version, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = mensaje,
            fontWeight = FontWeight.Light,
            color = Color.Black,
            modifier = Modifier.padding(15.dp), style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify
        )


        Text(text = "$copyright 2023 Prosperidad Social", fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier //.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .background(color = BackGroundTopLogin)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            AceptoTerminos()
            /*  if (versionViewModel.encuestasCategoriasAbiertas.value.isNotEmpty()) {
                ButtonAccept(
                    text = "Responder Encuesta",
                    onClick = {
                        AppHogaresAplication.funNav(RoutesNav.encuestaCategoriaScreen.route)
                    }
                )
            } else {
                ButtonAccept(
                    text = "Solicitar Encuesta",
                    onClick = {
                        versionViewModel.GetEncuestaCategoria()
                    }
                )
            }*/
        }
    }
}

@Composable
fun AceptoTerminos() {
    val uriHandler = LocalUriHandler.current
    val linkText = "Consulta los términos, condiciones y la política de tratamiento de datos personales."
    // Construimos un AnnotatedString con estilo underline y una anotación de URL
    val annotatedLink = buildAnnotatedString {
        append(linkText)
        addStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline),
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

        //Spacer(modifier = Modifier.width(8.dp))
        // ClickableText nos permite reaccionar a clicks en ranges anotados
        ClickableText(
            text = annotatedLink,
            style = MaterialTheme.typography.titleMedium,
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
fun showDialog(): String {
    val context = LocalContext.current
    val version = getVersionName(context)

    if(!version!!.isNotEmpty()) {
        //Text("Versión  1.0")
        return "Versión  1.0"
    }
    else{
        val separador = " "
        val version_1 = version.split(separador)

        return  "Versión   ${version_1[0]}"
    }
}
// Función para obtener la versión de la aplicación
fun getVersionName(context: Context): String? {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "N/A"
    }
}