package com.example.apphogares.frontEnd.screens.Permissions

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.R
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import com.example.apphogares.backEnd.Services.ServicesSystem.Downloads
import com.example.apphogares.backEnd.Services.ServicesSystem.GestionImages
import com.example.apphogares.backEnd.Services.ServicesSystem.network
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.AppHogaresAplication
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PreferenceSetting(val key: String, val title: String, val icon: Int)


object ReadPhoneStateSetting : PreferenceSetting(Manifest.permission.READ_PHONE_STATE, "Estado Teléfono", R.drawable.ic_launcher_foreground)
object RECORD_AUDIOSetting : PreferenceSetting(Manifest.permission.RECORD_AUDIO, "Audio", R.drawable.ic_launcher_foreground)
object ACCESS_FINE_LOCATIONSetting : PreferenceSetting(Manifest.permission.ACCESS_FINE_LOCATION, "Localización", R.drawable.ic_launcher_foreground)
object ACCESS_COARSE_LOCATIONSetting : PreferenceSetting(Manifest.permission.ACCESS_COARSE_LOCATION, "Localización", R.drawable.ic_launcher_foreground)
object FOREGROUND_SERVICESetting : PreferenceSetting(Manifest.permission.FOREGROUND_SERVICE, "Localización", R.drawable.ic_launcher_foreground)

object READ_EXTERNAL_STORAGESetting : PreferenceSetting(Manifest.permission.READ_EXTERNAL_STORAGE, "Storage", R.drawable.ic_launcher_foreground)


object CAMERASetting : PreferenceSetting(Manifest.permission.CAMERA, "Cámara", R.drawable.pacho)

@HiltViewModel
class PermissionsViewModel @Inject constructor(private val logError: LogError, private val network: network
): ViewModel()  {

    var messageError: MutableState<String> = mutableStateOf("")
    var msgError: MutableState<String> = mutableStateOf("")

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    init {
        validarContenidoCMS()
    }

    fun prepareDownloadsImage(context: Context) {
/*        if (AppHogaresAplication.contenidoCMS.Categorias.isNullOrEmpty()){
            println("prepareDownloadsImage contenidoCMS: ${Gson().toJson(AppHogaresAplication.contenidoCMS)}")
            return
        }*/

        AppHogaresAplication.listaMedallasRuta =
            GestionImages().ObtenerMedallasRuta(AppHogaresAplication.contenidoCMS.Categorias)
        try {
            viewModelScope.launch {
                Downloads(context).downloadImagesMedalla()
            }
        }catch (e: Exception) {
            viewModelScope.launch {
                if (AppHogaresAplication.estadoDispositivo.isInternetConectivity) {
                    logError.RegistrarError(e.message.orEmpty(), "PermissionsViewModel", "prepareDownloadsImage")
                }
            }
            msgError.value = e.message.orEmpty()
        }
    }

    fun prepareDownloadsAudios(context: Context) {
        println("prepareDownloads contenidoCMS: ${Gson().toJson(AppHogaresAplication.contenidoCMS)}")
/*        if (AppHogaresAplication.contenidoCMS.Categorias.isNullOrEmpty()){
            return
        }*/
        AppHogaresAplication.listadoAudiosTematica =
            Audio().ObtenerAudiosTematica(AppHogaresAplication.contenidoCMS.Categorias)
        println("prepareDownloadsAudios listadoAudiosTematica: ${Gson().toJson(AppHogaresAplication.listadoAudiosTematica.size)}")
        try {
            Downloads(context).downloadAudios()
        }catch (e: Exception) {
            messageError.value = "Error downloadAudios: ${e.message}"
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "PermissionsViewModel", "prepareDownloadsAudios")
            }
            msgError.value = e.message.orEmpty()
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getInformationDevice(context: Context){
        viewModelScope.launch {
            try {
                network.getDispositivo(context)
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                network.getLocation(context, fusedLocationClient)
            }catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "PermissionsViewModel", "getInformationDevice")
                msgError.value = e.message.orEmpty()
            }
        }
    }

    fun validarContenidoCMS(){
        try {
            if (!AppHogaresAplication.contenidoCMS.Categorias.isNullOrEmpty()){
                prepareDownloadsImage(AppHogaresAplication.context)
                prepareDownloadsAudios(AppHogaresAplication.context)
                //AppHogaresAplication.Infohogar.hogar?.contenidosRuta = AppHogaresAplication.contenidoCMS
            }
        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(
                    e.message.orEmpty(),
                    "PermissionsViewModel",
                    "validarContenidoCMS"
                )
            }
            msgError.value = e.message.orEmpty()
        }

    }
}

