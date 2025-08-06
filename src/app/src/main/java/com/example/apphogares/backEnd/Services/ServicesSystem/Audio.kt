package com.example.apphogares.backEnd.Services.ServicesSystem


import android.media.AudioAttributes
import android.media.MediaPlayer
import java.io.File
import android.os.Environment
import android.os.Handler
import androidx.compose.runtime.MutableState
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.hogarMain.AudioTematica
import com.example.apphogares.AppHogaresAplication


class Audio() {
    var mediaPlayer: MediaPlayer? = MediaPlayer()
    private val audioPathDir = Environment.DIRECTORY_MUSIC

    fun ObtenerAudiosTematica(categorias: List<Categoria>?): MutableList<AudioTematica> {
        val listadoAudiosTematica = mutableListOf<AudioTematica>()

        categorias.orEmpty().forEach {
            it.rutas.forEach {
                it.tematicas.forEach {
                    val nombreAudio = it.audio.substringAfterLast("/").substringBeforeLast("?")
                    listadoAudiosTematica.add(AudioTematica(it.codigo, it.audio, nombreAudio))
                }
            }
        }

        return listadoAudiosTematica
    }

    fun ObtenerAudiosInicialesematica(categorias: List<Categoria>?): MutableList<AudioTematica> {
        val listadoAudiosTematica = mutableListOf<AudioTematica>()

        categorias.orEmpty().forEach { it ->
            it.rutas.forEach { ruta ->
                ruta.tematicas[0].let {
                    val nombreAudio = it.audio.substringAfterLast("/").substringBeforeLast("?")
                    listadoAudiosTematica.add(AudioTematica(it.codigo, it.audio, nombreAudio))
                }
            }
        }

        return listadoAudiosTematica
    }

    fun startAudioFileFromPosition(audioFile: File, posIni: Int, posFinal: Int, estadoPlay: MutableState<Boolean>) {

        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }
            estadoPlay.value = false

            val start = (posIni * 1000)    // - 300
            val endPosition = posFinal * 1000
            val duration = endPosition - start + 1500
            println("startAudioFileFromPosition file: ${audioFile.name}")
            println("startAudioFileFromPosition: $start - $endPosition - $duration")
            var handler: Handler? = Handler()
            // Set the audio attributes for playback (optional but recommended)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            mediaPlayer?.setAudioAttributes(audioAttributes)

            // Set the data source of the MediaPlayer to the file path
            mediaPlayer?.setDataSource(audioFile.path)

            // Prepare the MediaPlayer asynchronously
            mediaPlayer?.prepare()

            // Set a callback to start playing the audio once it's prepared
            mediaPlayer?.setOnPreparedListener {mp ->
                mp.seekTo(start)
                mp.start()

                handler?.postDelayed({
                    mediaPlayer?.stop()
                    estadoPlay.value = true
                }, duration.toLong() )
            }
            // Set a callback to release the MediaPlayer once the playback is completed
            mediaPlayer?.setOnCompletionListener {
                    mp -> mp.release()
                handler?.removeCallbacksAndMessages(null)

            }
        } catch (e: Exception) {
            stopAudio()
            //e.printStackTrace()
        }
    }


    fun stopAudio() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            //stopAudio()
            //e.printStackTrace()
        }

    }

    fun startAudioFileFromPositionGamificacion(position: Int) {

        try {
            val audioFile = File(
                AppHogaresAplication.rutaMusic,
                "gamificacion.mp3"
            )
            var start = 0
            var duration = 0

            when(position) {
                1 -> {
                    start = 0
                    duration = 2000
                }
                2 -> {
                    start = 2100
                    duration = 3000
                }
                3 -> {
                    start = 5000
                    duration = 2500
                }
                4 -> {
                    start = 7600
                    duration = 2900
                }
                5 -> {
                    start = 10000
                    duration = 2800
                }
                6 -> {
                    start = 13000
                    duration = 1700
                }
                7 -> {
                    start = 14800
                    duration = 2000
                }
                else -> {

                }
            }
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }

            var handler: Handler? = Handler()
            // Set the audio attributes for playback (optional but recommended)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            mediaPlayer?.setAudioAttributes(audioAttributes)

            // Set the data source of the MediaPlayer to the file path
            mediaPlayer?.setDataSource(audioFile.path)

            // Prepare the MediaPlayer asynchronously
            mediaPlayer?.prepare()

            // Set a callback to start playing the audio once it's prepared
            mediaPlayer?.setOnPreparedListener {mp ->
                mp.seekTo(start)
                mp.start()

                handler?.postDelayed({
                    mediaPlayer?.stop()
                }, duration.toLong() )
            }
            // Set a callback to release the MediaPlayer once the playback is completed
            mediaPlayer?.setOnCompletionListener {
                    mp -> mp.release()
                handler?.removeCallbacksAndMessages(null)

            }
        } catch (e: Exception) {
            stopAudio()
            //e.printStackTrace()
        }
    }

    fun startAudioMayo(tipoFelicitacion: String, duracion: Long) {

        try {
            val audioFile = File(
                AppHogaresAplication.rutaMusic,
                "${tipoFelicitacion}.mp3"
            )
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }

            var handler: Handler? = Handler()
            // Set the audio attributes for playback (optional but recommended)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            mediaPlayer?.setAudioAttributes(audioAttributes)

            // Set the data source of the MediaPlayer to the file path
            mediaPlayer?.setDataSource(audioFile.path)

            // Prepare the MediaPlayer asynchronously
            mediaPlayer?.prepare()

            // Set a callback to start playing the audio once it's prepared
            mediaPlayer?.setOnPreparedListener {mp ->
                mp.seekTo(0)
                mp.start()

                handler?.postDelayed({
                    mediaPlayer?.stop()
                }, duracion )
            }
            // Set a callback to release the MediaPlayer once the playback is completed
            mediaPlayer?.setOnCompletionListener {
                    mp -> mp.release()
                handler?.removeCallbacksAndMessages(null)

            }
        } catch (e: Exception) {
            stopAudio()
            //e.printStackTrace()
        }
    }

}
