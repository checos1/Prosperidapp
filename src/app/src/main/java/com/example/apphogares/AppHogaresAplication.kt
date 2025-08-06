package com.example.apphogares

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.backEnd.core.models.BuildConfigApp
import com.example.apphogares.backEnd.core.models.Comunicaciones.Publicacion
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.backEnd.core.models.PQRs.PQR
import com.example.apphogares.backEnd.core.models.actividadesRuta.TematicaGamificacion
import com.example.apphogares.backEnd.core.models.contenidosRuta.Actividade
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import com.example.apphogares.backEnd.core.models.contenidosRuta.Medalla
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoCategoria
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRuta
import com.example.apphogares.backEnd.core.models.gamificacion.EstadosTematicas
import com.example.apphogares.backEnd.core.models.hogarMain.AudioTematica
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.Hogar
import com.example.apphogares.backEnd.core.models.hogarMain.HogarPreguntas
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.example.apphogares.backEnd.core.models.hogarMain.TarjetasTematica
import com.example.apphogares.backEnd.core.models.navegacion.Integrante
import com.example.apphogares.backEnd.core.models.navegacion.Navegacion
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EstadoDispositivo(
    var isInternetConectivity: Boolean = false,
    var typeInternetConectivity: String = "",
    var isLocalDispositivo: Boolean = false,
    var isValidDispositivo: Boolean = false,
    var isLocalHogar: Boolean = false
)

@HiltAndroidApp
class AppHogaresAplication() : Application() {

    companion object {
        // Global variable declaration
        var Infohogar: HogarPreguntas = HogarPreguntas("", "", false, emptyList(), Hogar("",  "", "", "", "", "", "", "", "", "",null, emptyList(), emptyList()))
        var contenidoCMS: ContenidosRuta = ContenidosRuta()
        var estadoDispositivo: EstadoDispositivo = EstadoDispositivo()
        var listadoAudiosTematica = mutableListOf<AudioTematica>()
        var listadoAudiosInicialesTematica = mutableListOf<AudioTematica>()
        var listadoTarjetasTematica = mutableListOf<TarjetasTematica>()
        var listaEstadosTematicas = mutableListOf<EstadosTematicas>()

        var listaEstadosRutas = mutableListOf<EstadoRuta>()
        var listaMedallasRuta = mutableListOf<Medalla>()
        var listaEstadosCategorias = mutableListOf<EstadoCategoria>()
        var listaActividadesTematicaEnCurso : List<Actividade> =  emptyList<Actividade>()
        var indexActividadEnCurso : Int = 0
        var audio = Audio()
        var listaTematicaGamificacion = mutableListOf<TematicaGamificacion>()
        var integranteSeleccionado: String = ""
        var navegacion: Navegacion = Navegacion(mutableListOf<Integrante>(), 0, 10, 5 )
        var latitud: Double = 0.0
        var longitud: Double = 0.0
        var rutaMusic = ""
        var tokenFCM: String = ""
        lateinit var context: Context
        lateinit var funNav: (String) -> Unit
        var alertas = mutableListOf<Notificacion>()
        var encuestas = mutableListOf<Encuesta>()
        var screenActual = ""
        var listaPQRs = mutableListOf<PQRDocument>()
        var selectedPQR = PQRDocument()

        var listaPublicaciones = mutableListOf<Publicacion>()

        var buildConfigApp = BuildConfigApp()

        var currentCategoria: Categoria = Categoria()
    }

    object GlobalState {
        private val _globalState = MutableStateFlow<Encuesta>(Encuesta())
        val globalState: StateFlow<Encuesta> = _globalState.asStateFlow()

        fun updateGlobalState(newEncuesta: Encuesta) {
            _globalState.value = newEncuesta
        }
    }


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Audio().stopAudio()
    }
}