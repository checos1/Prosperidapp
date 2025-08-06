package com.example.apphogares.backEnd.Services.ServicesSystem

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.contenidosRuta.Medalla
import com.example.apphogares.backEnd.core.models.hogarMain.TarjetasTematica
import com.google.gson.Gson
import java.io.File
import android.provider.MediaStore
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoRutaEnum

class GestionImages {

    fun ObtenerTarjetasTematica(categorias: List<Categoria>?): MutableList<TarjetasTematica> {
        val listadoTarjetasTematica = mutableListOf<TarjetasTematica>()

        categorias.orEmpty().forEach {
            it.rutas.forEach {
                it.tematicas.forEach {
                    val codigoTematica = it.codigo
                    it.actividades.forEach {
                        if (it.tarjetas.isNotEmpty())
                            listadoTarjetasTematica.add(TarjetasTematica(codigoTematica, it.tarjetas))
                    }
                }
            }
        }

        return listadoTarjetasTematica
    }

    fun ObtenerMedallasRuta(categorias: List<Categoria>?): MutableList<Medalla> {
        val listadoMedallas = mutableListOf<Medalla>()

        categorias.orEmpty().forEach {
            it.rutas.forEach {
                var medalla = it.medalla
                medalla.ruta = it.codigo
                listadoMedallas.add(medalla)
            }
        }
        return listadoMedallas
    }

    fun obtenerRutaFisicaImagen(context: Context, codigoTematica: String, index: Int): ImageBitmap? {
        val filePath = Downloads(context).ObtenerRutaTarjetaTematica(codigoTematica, index)
        val file = File(filePath)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(filePath)
            return bitmap.asImageBitmap()
        }
        return null
    }

    fun obtenerRutaFisicaImagenRuta(context: Context, ruta: String, estadoRuta: EstadoRutaEnum): ImageBitmap? {
        var imageBitmap: ImageBitmap? = null
        try {
            val filePath = Downloads(context).ObtenerImagenRuta(ruta, estadoRuta)
            val nombreImagen = filePath.substringAfterLast("/")
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
                val imagenFile = File(it, nombreImagen)
                if (imagenFile.exists()) {
                    println("GestionImages obtenerRutaFisicaImagenRuta exist: ${Gson().toJson(imagenFile)}")
                    val bitmap = BitmapFactory.decodeFile(imagenFile.path)
                    println("GestionImages obtenerRutaFisicaImagenRuta bitmap: ${Gson().toJson(bitmap)}")
                    return bitmap.asImageBitmap()
                }
            }
        }catch (e: Exception) {
            println("GestionImages obtenerRutaFisicaImagenRuta Error: ${e.message}")
        }

        return imageBitmap
    }

    fun loadImagesFromMediaStore(context: Context): List<Uri> {
        val imageList = mutableListOf<Uri>()

        // Define the projection (columns) to retrieve
        val projection = arrayOf(
            MediaStore.Images.Media._ID,  // ID of the media item
            MediaStore.Images.Media.DISPLAY_NAME // Name of the media item
        )

        // Content URI for external media
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        context.contentResolver.query(
            contentUri,
            projection,
            null, // No selection clause
            null, // No selection arguments
            null  // Default sort order
        )?.use { cursor ->
            // Indices of the columns we're interested in
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                // Get values of columns for a given image.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                imageList.add(contentUri)
                // You can use 'contentUri' to display or manage the image.
            }
        }

        return imageList
    }

    fun saveImageToMediaStore(context: Context, bitmap: Bitmap, displayName: String) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        uri?.let {
            context.contentResolver.openOutputStream(it).use { outputStream ->
                // Compress and write the bitmap to the output stream obtained above
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream!!)
            }
        }
    }
}