package com.example.apphogares.frontEnd

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.ViewModel
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.models.PQRs.PQR
import com.example.apphogares.frontEnd.screens.Components.Navigation.BarBottom
import com.example.apphogares.frontEnd.screens.Encuesta.EncuestaCategoriaScreen
import com.example.apphogares.frontEnd.screens.Encuesta.EncuestaScreen
import com.example.apphogares.frontEnd.screens.Gestiona.GestionaScreen
import com.example.apphogares.frontEnd.screens.Index.IndexScreen
import com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.HogarInfoScreen
import com.example.apphogares.frontEnd.screens.Juegos.CompletarPalabras.CompletarPalabrasScreen
import com.example.apphogares.frontEnd.screens.Juegos.EmparejarPalabras.EmparejarPalabrasScreen
import com.example.apphogares.frontEnd.screens.Juegos.MemoriaPalabras.MemoriaScreen
import com.example.apphogares.frontEnd.screens.Juegos.SopaLetras.SopadeLetras
import com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR.CamaraScreen
import com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR.CrearPQRScreen
import com.example.apphogares.frontEnd.screens.PQRs.Components.InformacionPQR.InformacionPQRScreen
import com.example.apphogares.frontEnd.screens.PQRs.PQRScreen
import com.example.apphogares.frontEnd.screens.PeriodicoVida.PeriodicoVidaScreen
import com.example.apphogares.frontEnd.screens.Permissions.PermissionsDeviceScreen
import com.example.apphogares.frontEnd.screens.Version.VersionScreen
import com.example.apphogares.frontEnd.screens.alertas.AlertaScreen
import com.example.apphogares.frontEnd.screens.autenticacion.LoginScreen
import com.example.apphogares.frontEnd.screens.autenticacion.Components.QuestionScreen
import com.example.apphogares.frontEnd.screens.oferta.OfertaHogarScreen
import com.example.apphogares.frontEnd.screens.ruta.Tematica.HistorietaScreen
import com.example.apphogares.frontEnd.screens.ruta.RutaScreen
import com.example.apphogares.frontEnd.theme.BackGroundBottomRuta
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.LightColorScheme
import com.example.apphogares.frontEnd.screens.autenticacion.LoginViewModel
import com.example.dpsapp.ui.theme.Typography
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import com.example.apphogares.frontEnd.screens.Comunicaciones.ComunicacionesScreen
import com.example.apphogares.frontEnd.screens.ruta.Categorias.CategoriaScreen
import com.example.apphogares.frontEnd.screens.ruta.InformacionRuta.InformacionRutaScreen

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    val loginViewModel: LoginViewModel, val repository: AppRepository, val network: network,
    val api: AuthenticacionApi
) : ViewModel() {

    lateinit var funNav: (String) -> Unit
    val screenWidthDp:MutableState<Int> = mutableStateOf(0)
    val screenHeightDp:MutableState<Int> = mutableStateOf(0)


    var entrada = ""
    @Composable
    fun loginScreen() {
        Column(
            Modifier
                .fillMaxSize()
                .background(BackGroundTopLogin)){
            LoginScreen(funNav)
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun indexScreen() {
        Column(
            Modifier
                .fillMaxSize()
                .background(BackGroundTopLogin)){
            IndexScreen(funNav)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun rutaScreen() {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, {RutaScreen(screenWidthDp.value, screenHeightDp.value, funNav)}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun historietaScreen(code: String, context: Context){
        if (AppHogaresAplication.audio.mediaPlayer == null){
            AppHogaresAplication.audio.mediaPlayer = MediaPlayer()
        }
        wrapper(funNav, { HistorietaScreen(screenWidthDp.value, screenHeightDp.value, funNav, code, context) }, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun questionScreen() {
        QuestionScreen(funNav)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun permissionsDeviceScreen() {
        Column(
            Modifier
                .fillMaxSize()
                .background(BackGroundTopLogin)){
            PermissionsDeviceScreen(funNav)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun infoHogarScreen() {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, { HogarInfoScreen() }, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun wellcomeScreen() {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, {VersionScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ofertaHogarScreen() {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, {OfertaHogarScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun emparejarPalabraScreen(
        catCode: String,
        rutaCode: String,
        tematicaCode: String
    ) {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, {EmparejarPalabrasScreen(funNav, catCode, rutaCode, tematicaCode )}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun completarPalabraScreen(
        catCode: String,
        rutaCode: String,
        tematicaCode: String
    ) {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, { CompletarPalabrasScreen(funNav, catCode, rutaCode, tematicaCode, screenWidthDp.value, screenHeightDp.value ) }, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun memoriaPalabraScreen(
        catCode: String,
        rutaCode: String,
        tematicaCode: String
    ) {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, { MemoriaScreen(funNav, catCode, rutaCode, tematicaCode ) }, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun sopaPalabraScreen(
        catCode: String,
        rutaCode: String,
        tematicaCode: String
    ) {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, { SopadeLetras(catCode, rutaCode, tematicaCode ) }, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun alertaScreen() {
        AppHogaresAplication.audio.stopAudio()
        wrapper(funNav, {AlertaScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun encuestaScreen() {
        wrapper(funNav, {EncuestaScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun gestionaScreen() {
        wrapper(funNav, {GestionaScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun encuestaCategoriaScreen() {
        wrapper(funNav, {EncuestaCategoriaScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun PQRsScreen() {
        wrapper(funNav, {PQRScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun crearPQRScreen() {
        wrapper(funNav, {CrearPQRScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun informacionPQRScreen(
    ) {
        wrapper(funNav, {InformacionPQRScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun periodicoVidaScreen(
    ) {
        wrapper(funNav, {PeriodicoVidaScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun comunicacionesScreen(
    ) {
        wrapper(funNav, {ComunicacionesScreen()}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun categoriaScreen(
    ) {
        wrapper(funNav, {CategoriaScreen(
            {
                funNav(RoutesNav.rutaScreen.route)
            }
        )}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }

    @Composable
    fun informacionRutaScreen(
    ) {
        wrapper(funNav, {InformacionRutaScreen(
            {
                funNav(RoutesNav.rutaScreen.route)
            }
        )}, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }


/*    @Composable
    fun camaraScreen() {
        wrapper(funNav, {CamaraScreen(onDimiss = {
            showCamara = false
        })
        }, true, true, colorScheme.onSecondary.toArgb(), colorScheme.secondary.toArgb())
    }*/


    @Composable
    fun wrapper(
        funNav: (String) -> Unit,
        Ruta: @Composable () -> Unit,
        top: Boolean,
        bottom: Boolean,
        topBarr: Int,
        bottomBarr: Int
    ) {

        val colorScheme = LightColorScheme
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.navigationBarColor = topBarr
                window.statusBarColor = bottomBarr
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(16f)
                        .background(BackGroundTopLogin)
                ){
                    Ruta()
                }

                Divider(color = Gray)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(2f)
                        .background(BackGroundBottomRuta)
                ){
                    BarBottom(funNav)
                }
            }
        }
    }
}