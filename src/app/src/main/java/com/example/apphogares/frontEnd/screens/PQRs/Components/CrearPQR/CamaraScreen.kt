package com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Size
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.FileManagement
import com.example.apphogares.frontEnd.screens.PQRs.PQRViewModel
import com.example.apphogares.frontEnd.theme.Gray
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

var IdPeticionAPP = ""
@Composable
fun CamaraScreen(
    crearPQRViewModel: CrearPQRViewModel = hiltViewModel(),
    onDimiss: () -> Unit
) {
    IdPeticionAPP = crearPQRViewModel.selectedPQR.value.idPeticionAPP
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder()
            .setTargetResolution(Size(1280, 720)) // Reduce resolution at capture time
            .build()
    }
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Button(onClick = {
            crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.fotoPeticion = "${IdPeticionAPP}_${System.currentTimeMillis()}.jpeg"
            captureImage(imageCapture,
                crearPQRViewModel.selectedPQR.value.blqInformacionPeticion.fotoPeticion,
                context,
                onDimiss
            )
        }) {
            Text(text = "Capturar Imagen", color = Gray)
        }
    }
}



private fun captureImage(
    imageCapture: ImageCapture,
    nameImage: String,
    context: Context,
    onDimiss: () -> Unit
) {
    val photoPathDir = Environment.DIRECTORY_PICTURES
    val photoName = nameImage
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, photoName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            //put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            put(MediaStore.Images.Media.RELATIVE_PATH, photoPathDir )
        }
    }
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val msg = "Foto capturada con exito "
                Toast.makeText(AppHogaresAplication.context, msg, Toast.LENGTH_SHORT).show()

                // Compress the image before uploading
                val compressedUri = compressImage(context, outputFileResults.savedUri!!, 50) // 70% quality

                val fileManagement = FileManagement()
                fileManagement.uploadFile(compressedUri, IdPeticionAPP, nameImage, context)
                onDimiss()
                //funNav(RoutesNav.CrearPQRScreen.route)
            }

            override fun onError(exception: ImageCaptureException) {
                println("Failed $exception")
            }
        })
}


private fun compressImage(context: Context, uri: Uri, quality: Int): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()

    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

    val compressedPath = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.size()),
        "compressed_${System.currentTimeMillis()}",
        "compressed image"
    )

    return Uri.parse(compressedPath)
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }