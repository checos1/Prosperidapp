package com.example.apphogares.backEnd.Services.ServicesSystem

import android.content.Context
import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import com.example.apphogares.backEnd.core.models.hogarMain.AudioTematica
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.contenidosRuta.Medalla
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRutaEnum
import com.example.apphogares.frontEnd.screens.PeriodicoVida.periodicoVidaViewModel
import java.io.File
import java.io.InputStream
import java.io.FileOutputStream
import java.net.URL


class Downloads(context: Context) {
    val _context = context
    val audioPathDir = Environment.DIRECTORY_MUSIC
    val photoPathDir = Environment.DIRECTORY_PICTURES
    val pdfPathDir = Environment.DIRECTORY_DOCUMENTS


    // Function to download audio from a given URL
    fun downloadAudio(audioUrl: String, titleAudio: String): Long  {
        try {

            val downloadManager = _context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val request = DownloadManager.Request(Uri.parse(audioUrl))
                .setMimeType("audio/MP3")
                .setTitle("Audio Download") // Set the title for the download notification
                .setDescription("Downloading audio file") // Set the description for the download notification
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                .setDestinationInExternalFilesDir(_context, audioPathDir, titleAudio);
            // Enqueue the download and return the download ID
            return downloadManager.enqueue(request)
        } catch (e: Exception) {
            println("Error al descargar el archivo: ${e.message}")
            return 0
        }

    }

    fun downloadAudios() {
        if(!AppHogaresAplication.estadoDispositivo.isInternetConectivity) return

        if (AppHogaresAplication.Infohogar.idHogar != null && AppHogaresAplication.Infohogar.idHogar != "") {
            println("Descargando audios: ${AppHogaresAplication.listadoAudiosTematica}")
            removeAudios()
            var listaAudios = mutableListOf<AudioTematica>()
            AppHogaresAplication.listadoAudiosTematica.forEach { audioTematica ->
                _context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.let {
                    val audioFile = File(it, audioTematica.nombreAudio)
                    if (!audioFile.exists()) {
                        val urlAudio = audioTematica.urlAudio + Constants.ACCESS_TOKEN
                        println("Descargando audio: $urlAudio")
                        downloadAudio(urlAudio, audioTematica.nombreAudio)
                    }
                }
            }
        }
        _context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.let {
            val audioFile = File(it, "gamificacion.mp3")
            if (!audioFile.exists()) {
                val urlAudio = "${Constants.URL_STORAGE}gamificacion_202f85bd3d.mp3" + Constants.ACCESS_TOKEN
                println("Descargando audio: $urlAudio")
                downloadAudio(urlAudio, "gamificacion.mp3")
            }
        }
        _context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.let {
            val audioFile = File(it, "felicitaciones.mp3")
            if (!audioFile.exists()) {
                val urlAudio = "${Constants.URL_STORAGE}Felicitaciones.mp3" + Constants.ACCESS_TOKEN
                println("Descargando audio Felicitaciones: $urlAudio")
                downloadAudio(urlAudio, "felicitaciones.mp3")
            }
        }
        _context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.let {
            val audioFile = File(it, "felicitacionescontinuemos.mp3")
            if (!audioFile.exists()) {
                val urlAudio = "${Constants.URL_STORAGE}FelicitacionesContinuemos.mp3" + Constants.ACCESS_TOKEN
                println("Descargando audio FelicitacionesContinuemos: $urlAudio")
                downloadAudio(urlAudio, "felicitacionescontinuemos.mp3")
            }
        }
    }

    fun downloadAudiosIniciales(){
        if(!AppHogaresAplication.estadoDispositivo.isInternetConectivity) return
        _context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.let {
            AppHogaresAplication.listadoAudiosInicialesTematica.forEach { audioTematica ->
                val audioFile = File(it, audioTematica.nombreAudio)
                if (!audioFile.exists()) {
                    val urlAudio = audioTematica.urlAudio + Constants.ACCESS_TOKEN
                    println("Descargando audio: $urlAudio")
                    downloadAudio(urlAudio, audioTematica.nombreAudio)
                }
            }

        }

    }

     fun removeAudios() {
        if (AppHogaresAplication.Infohogar.idHogar != null && AppHogaresAplication.Infohogar.idHogar != "") {
            _context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.let {
                it.listFiles().forEach { file ->
                    file.extension == "mp3" && !AppHogaresAplication.listadoAudiosTematica.any { audioTematica ->
                        audioTematica.nombreAudio.substringAfterLast("/") == file.name
                    } && file.delete()
                }
            }
        }
    }

    fun downloadImage(photoUrl: String):Long {
        try {
            val titleImage = photoUrl.substringAfterLast("/").substringBeforeLast("?")
            val downloadManager = _context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(photoUrl))
                .setMimeType("image/png")
                .setTitle("Image Download") // Set the title for the download notification
                .setDescription("Downloading image file") // Set the description for the download notification
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                .setDestinationInExternalFilesDir(_context, photoPathDir, titleImage);
            // Enqueue the download and return the download ID
            return downloadManager.enqueue(request)
        } catch (e: Exception) {
            println("Error al descargar el archivo: ${e.message}")
            throw e
        }
    }

    fun downloadImages() {
        if(!AppHogaresAplication.estadoDispositivo.isInternetConectivity) return
        try {
            if (AppHogaresAplication.Infohogar.idHogar != null && AppHogaresAplication.Infohogar.idHogar != "") {
                //removeImages()
                _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
                    AppHogaresAplication.listadoTarjetasTematica.forEach() { tarjetasTematica ->
                        tarjetasTematica.tarjetas.forEach {tarjeta ->
                            val titleImage = tarjeta.url.substringAfterLast("/")
                            val photoFile = File(it, titleImage)
                            if (!photoFile.exists()) {
                                val urlPhoto = tarjeta.url //+ AppHogaresAplication.accessToken
                                downloadImage(urlPhoto)
                            }
                        }
                    }
                }

/*                AppHogaresAplication.listaMedallasRuta.forEach { medalla ->
                    val titleImage = medalla.url.substringAfterLast("/")
                    _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
                        val photoFile = File(it, titleImage)
                        if (!photoFile.exists()) {
                            val urlPhoto = medalla.url //+ AppHogaresAplication.accessToken
                            downloadImage(urlPhoto)
                        }
                    }
                }*/
            }
        } catch (e: Exception) {
            throw e
        }

    }

    fun downloadImagesMedalla() {
        if(!AppHogaresAplication.estadoDispositivo.isInternetConectivity) return
        try {
            println("Descargando imagenes de medallas: ${AppHogaresAplication.listaMedallasRuta}")
            _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
                AppHogaresAplication.listaMedallasRuta.forEach { medalla ->
                    if(medalla.urlInactiva != null) {
                        GetMedalla(medalla.urlInactiva, it)
                    }
                }
                AppHogaresAplication.listaMedallasRuta.forEach { medalla ->
                    if(medalla.urlActiva != null) {
                        GetMedalla(medalla.urlActiva, it)
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }

    }

    private fun GetMedalla(
        medallaUrl: String,
        it: File
    ) {
        val titleImage = medallaUrl.substringAfterLast("/")
        println("Descargando imagenes de medallas titleImage: $titleImage")
        val photoFile = File(it, titleImage)
        println("Descargando imagenes de medallas photoFile: $photoFile")
        if (!photoFile.exists()) {
            val urlPhoto = medallaUrl + Constants.ACCESS_TOKEN
            downloadImage(urlPhoto)
        }
    }

    private fun removeImages() {
        _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
            it.listFiles().forEach { file ->
                (file.extension == "png" || file.extension == "jpg") && !AppHogaresAplication.listadoTarjetasTematica.any { tarjetasTematica ->
                    tarjetasTematica.tarjetas.any { tarjeta ->
                        tarjeta.url.substringAfterLast("/") == file.name
                    }
                } && file.delete()
/*                (file.extension == "png" || file.extension == "jpg") && !AppHogaresAplication.listaMedallasRuta.any { imagenRuta ->
                    imagenRuta.url.substringAfterLast("/") == file.name
                } && file.delete()*/
            }
        }
    }

    fun ObtenerRutaTarjetaTematica(tematica: String, index: Int) : String {
        var ruta = ""
        val tarjetas = AppHogaresAplication.listadoTarjetasTematica
        val tarjeta = tarjetas.find { it.idTematica == tematica }?.tarjetas?.get(index)
        _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
            ruta = it.path + "/" + tarjeta?.url?.substringAfterLast("/")?.substringBeforeLast("?")
        }
        return ruta
    }

    fun ObtenerImagenRuta(ruta: String, estadoRuta: EstadoRutaEnum) : String {
        var rutaUrl = ""
        val imagenesRuta = AppHogaresAplication.listaMedallasRuta
        val medalla = imagenesRuta.find { it.ruta == ruta }
        _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
            val urlMedalla = if (estadoRuta == EstadoRutaEnum.Realizado) medalla?.urlActiva else medalla?.urlInactiva
            rutaUrl = it.path + "/" + urlMedalla?.substringAfterLast("/")?.substringBeforeLast("?")
        }
        return rutaUrl
    }

    fun downloadPDF(pdfUrl: String):Long {
        _context.getExternalFilesDir(pdfPathDir)?.let {
            val pdfFile = File(it, "periodicoVida.pdf")
            if (pdfFile.exists()) {
                pdfFile.delete()
            }
        }

        try {
            val titleImage = "periodicoVida.pdf"
            val downloadManager = _context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(pdfUrl))
                .setMimeType("application/pdf")
                .setTitle("PDF Download") // Set the title for the download notification
                .setDescription("Downloading pdf file") // Set the description for the download notification
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                .setDestinationInExternalFilesDir(_context, pdfPathDir, titleImage);
            // Enqueue the download and return the download ID
            return downloadManager.enqueue(request)
        } catch (e: Exception) {
            println("Error al descargar el archivo: ${e.message}")
            throw e
        }
    }

    fun downloadPdf(url: String, fileName: String): File {
        val pdfFile = File(_context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
        if (!pdfFile.exists()) {
            val input: InputStream = URL(url).openStream()
            val output = FileOutputStream(pdfFile)
            input.use { inputStream ->
                output.use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
        return pdfFile
    }

}