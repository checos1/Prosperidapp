package com.example.apphogares.backEnd.Services.ServicesSystem

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import com.example.apphogares.backEnd.core.models.hogarMain.Dispositivo
import com.example.apphogares.backEnd.core.models.hogarMain.HogarPreguntas
import com.example.apphogares.backEnd.core.models.hogarMain.LogErrores
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.servicesAPI.ContenidoCMSAPI
import com.example.apphogares.backEnd.Services.servicesAPI.DispositivoAPI
import com.example.apphogares.backEnd.Services.utilities
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

import javax.inject.Inject

class network @Inject constructor(private val api: AuthenticacionApi,
                                  private val apiContenidoCMS: ContenidoCMSAPI,
                                  private val apiDispositivo: DispositivoAPI,
                                  private val repository: AppRepository,
                                  private val navegacionApp: NavegacionApp,
                                  private val logError: LogError,
                                  private val utilities: utilities,
                                  private val preferencesManager: PreferencesManager
    ) {

    fun checkInternetConnectivity(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(ComponentActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getTypeInternetConnectivity(context: Context): String? {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.networkSpecifier?.toString()
    }

    fun verificarAPIDispositivo(): Boolean {
        val query = runBlocking {
            repository.getHogarPreguntas().first()
        }
        AppHogaresAplication.Infohogar = Gson().fromJson(query.jsonHogar, HogarPreguntas::class.java)
        val dispositivo = AppHogaresAplication.Infohogar.hogar!!.dispositivo!!
        AppHogaresAplication.estadoDispositivo.isLocalDispositivo = dispositivo == null

        AppHogaresAplication.estadoDispositivo.isValidDispositivo = runBlocking { apiDispositivo.Verificar(dispositivo).estado }

        return AppHogaresAplication.estadoDispositivo.isValidDispositivo
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun validarEstadoDispositivo(context: Context) {
        var paso: String = ""
        try {
            getInfoLocalHogar()
            paso = "getInfoLocalHogar:  ${Gson().toJson(AppHogaresAplication.Infohogar)}"
            var dispositivo = AppHogaresAplication.Infohogar.hogar!!.dispositivo
            if (dispositivo == null)
            {
                getDispositivo(context)
                dispositivo = AppHogaresAplication.Infohogar.hogar!!.dispositivo
            }
            paso = "dispositivo: ${dispositivo!!.idDispositivo}"

            AppHogaresAplication.estadoDispositivo.isLocalDispositivo = dispositivo != null
            AppHogaresAplication.estadoDispositivo.isValidDispositivo = true

            if (AppHogaresAplication.contenidoCMS.Categorias == null)
            {
                ObtenerContenidoCMS()
            }

            val hogarPreguntas= HogarListaPreguntas(
                AppHogaresAplication.Infohogar.idHogar,
                AppHogaresAplication.integranteSeleccionado,
                Gson().toJson(AppHogaresAplication.Infohogar)
            )
            repository.insert(hogarPreguntas)
            paso = "isLocalHogar: ${AppHogaresAplication.estadoDispositivo.isLocalHogar}"
        } catch (e: Exception) {
            logError.RegistrarError(paso + e.message.toString(), "network", "validarEstadoDispositivo")

            AppHogaresAplication.estadoDispositivo.isValidDispositivo = true
        }
    }

    suspend fun getInfoLocalHogar(){
        try {

            AppHogaresAplication.estadoDispositivo.isLocalHogar = AppHogaresAplication.Infohogar.hogar != null
            if (AppHogaresAplication.estadoDispositivo.isLocalHogar)
            {
                if (AppHogaresAplication.Infohogar!!.hogar!!.navegacion != null){
                    AppHogaresAplication.navegacion = AppHogaresAplication.Infohogar!!.hogar!!.navegacion!!
                }else{
                    navegacionApp.ObtenerNavegacionInicialApp()
                }
                AppHogaresAplication.listaPQRs = AppHogaresAplication.Infohogar!!.hogar!!.pqrs
                AppHogaresAplication.listaPublicaciones= AppHogaresAplication.Infohogar!!.hogar!!.publicaciones
            }
        } catch (e: Exception) {
            //AppHogaresAplication.estadoDispositivo.isLocalHogar = false
            logError.RegistrarError(e.message.toString(), "network", "getInfoLocalHogar")
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDispositivo(context: Context) {
        var paso: String = "Inicio"
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            paso = "telephonyManager: ${Gson().toJson(telephonyManager)}"
            val imei = telephonyManager.imei
            val serialNumber = telephonyManager.simSerialNumber
            val cellPhoneNumber = telephonyManager?.line1Number ?: "Phone number not available"
            val formatDate = SimpleDateFormat("yyyy-MM-dd")
            val dispositivo: Dispositivo = Dispositivo(
                0, AppHogaresAplication.integranteSeleccionado, imei, serialNumber, cellPhoneNumber, formatDate.format(Date()), true, AppHogaresAplication.tokenFCM)
            runBlocking {
                apiDispositivo.Registrar(dispositivo)
                AppHogaresAplication.Infohogar.hogar!!.dispositivo = dispositivo
                val hogarPreguntas: HogarListaPreguntas = HogarListaPreguntas(
                    AppHogaresAplication.Infohogar.idHogar,
                    AppHogaresAplication.integranteSeleccionado,
                    Gson().toJson(AppHogaresAplication.Infohogar)
                )
                repository.insert(hogarPreguntas)
            }
        }
        catch (e: Exception) {
            try {
                runBlocking {
                    api.RegistrarLogErrores(LogErrores(0, "", "APP", paso))
                }
            }catch (e: Exception) {
                println("getDispositivo: ${e.message}")
            }
        }

    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false

        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun getLocation(context: Context, fusedLocationClient: FusedLocationProviderClient){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            isGPSEnabled(context)){
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {
                AppHogaresAplication.latitud = it.latitude
                AppHogaresAplication.longitud = it.longitude
            }.addOnFailureListener {
                AppHogaresAplication.latitud = 0.0
                AppHogaresAplication.longitud = 0.0
            }
        }
    }

    fun isGPSEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    suspend fun getDataLocal() {
        var paso: String = "Inicio"
        try {
            AppHogaresAplication.Infohogar = Gson().fromJson(repository.getHogarPreguntas().first().jsonHogar, HogarPreguntas::class.java)
            AppHogaresAplication.estadoDispositivo.isLocalHogar = AppHogaresAplication.Infohogar.idHogar != ""
            if (!AppHogaresAplication.estadoDispositivo.isLocalHogar) {
                return
            }
            AppHogaresAplication.integranteSeleccionado = AppHogaresAplication.Infohogar.idIntegranteHogar
            paso = "Infohogar: ${Gson().toJson(AppHogaresAplication.Infohogar)}"

            val notificacionesJson = preferencesManager.getData("notificaciones", "")

            if(notificacionesJson != ""){
                AppHogaresAplication.Infohogar.hogar?.notificaciones = Gson().fromJson(notificacionesJson, Array<Notificacion>::class.java).toMutableList()
            }
            paso = "notificacionesJson: ${Gson().toJson(AppHogaresAplication.Infohogar.hogar?.notificaciones)}"
            val encuestasJson = preferencesManager.getData("encuestas", "")
            if(encuestasJson != ""){
                AppHogaresAplication.Infohogar.hogar?.encuestas = Gson().fromJson(encuestasJson, Array<Encuesta>::class.java).toMutableList()
            }
            withContext(Dispatchers.IO) {
                GrabarInfoHogar(false)
            }
            paso = "EncuestasJson: ${Gson().toJson(AppHogaresAplication.Infohogar.hogar?.encuestas)}"
            val filteredNotifications = AppHogaresAplication.Infohogar.hogar?.notificaciones
                ?.filter { it.estadoNotificacion != "Eliminada" }
                ?.toMutableList()
                ?: mutableListOf()
            AppHogaresAplication.alertas = filteredNotifications
            AppHogaresAplication.encuestas = AppHogaresAplication.Infohogar.hogar?.encuestas?.toMutableList() ?: mutableListOf()
            paso = "integranteSeleccionado: ${AppHogaresAplication.integranteSeleccionado}"


            utilities.subscribeToTopic("${AppHogaresAplication.Infohogar.hogar!!.contacto?.codigo_municipio?.substring(0, 2)}_00_00")
            utilities.subscribeToTopic("${AppHogaresAplication.Infohogar.hogar!!.contacto?.codigo_municipio}_00_00")


            paso = "Fin"
        }catch (e: Exception) {
            println("Error getDataLocal: $paso ${e.message}")
            //AppHogaresAplication.estadoDispositivo.isLocalHogar = false
        }
    }

    suspend fun GrabarInfoHogar(registrarApi: Boolean = true){
        try {
            val hogarPreguntas = HogarListaPreguntas(
                AppHogaresAplication.Infohogar.idHogar,
                AppHogaresAplication.integranteSeleccionado,
                Gson().toJson(AppHogaresAplication.Infohogar)
            )
            repository.insert(hogarPreguntas)
            if (registrarApi && AppHogaresAplication.estadoDispositivo.isInternetConectivity)
                api.RegistrarInfohogarApp(hogarPreguntas)

        }catch (e: Exception) {
            logError.RegistrarError(e.message.toString(), "network", "GrabarInfoHogar")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun ObtenerContenidoCMS(){
        try {
            println("Network ObtenerContenidoCMS Inicio")
            val strContenidoCMS = preferencesManager.getData("contenidoCMS", "")
            val contenidoCMS = Gson().fromJson(strContenidoCMS, ContenidosRuta::class.java) ?: ContenidosRuta()
            println("Network ObtenerContenidoCMS contenidoCMS Preference: ${Gson().toJson(contenidoCMS)}")
            if(AppHogaresAplication.estadoDispositivo.isInternetConectivity){
                var fechaActualizacion = LocalDateTime.now().toString()
                AppHogaresAplication.contenidoCMS = apiContenidoCMS.Get(fechaActualizacion)
                println("Network ObtenerContenidoCMS contenidoCMS API: ${Gson().toJson(AppHogaresAplication.contenidoCMS)}")
            }else{
                AppHogaresAplication.contenidoCMS = contenidoCMS
            }

            if(AppHogaresAplication.contenidoCMS.Categorias == null || AppHogaresAplication.contenidoCMS.Categorias!!.isEmpty()){
                AppHogaresAplication.contenidoCMS = Gson().fromJson(strContenidoCMS, ContenidosRuta::class.java)
            }else{
                preferencesManager.saveData("contenidoCMS", Gson().toJson(AppHogaresAplication.contenidoCMS))
            }

            AppHogaresAplication.listaMedallasRuta =
                GestionImages().ObtenerMedallasRuta(AppHogaresAplication.contenidoCMS.Categorias)

            try {
                Downloads(AppHogaresAplication.context).downloadImagesMedalla()
            }catch (e: Exception) {
                println("Error downloadImages: ${e.message}")
            }

            AppHogaresAplication.listadoAudiosInicialesTematica = Audio().ObtenerAudiosInicialesematica(
                AppHogaresAplication.contenidoCMS.Categorias)

            try {
                Downloads(AppHogaresAplication.context).downloadAudiosIniciales()
            }catch (e: Exception) {
                println("Error downloadAudios: ${e.message}")
            }

            AppHogaresAplication.listadoTarjetasTematica =
                GestionImages().ObtenerTarjetasTematica(AppHogaresAplication.contenidoCMS.Categorias)
            try {
                Downloads(AppHogaresAplication.context).downloadImages()
            }catch (e: Exception) {
                println("Error downloadImages: ${e.message}")
            }

            AppHogaresAplication.listaTematicaGamificacion =
                GamificacionTematica.ObtenerListaTematicasGamificacion(AppHogaresAplication.contenidoCMS.Categorias)

        }catch (e: Exception) {
            println("Error ObtenerContenidoCMS: ${e.message}")
            logError.RegistrarError(e.message.toString(), "network", "ObtenerContenidoCMS")
        }
    }

}