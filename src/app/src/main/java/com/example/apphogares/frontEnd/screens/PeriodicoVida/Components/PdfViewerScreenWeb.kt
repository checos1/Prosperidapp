package com.example.apphogares.frontEnd.screens.PeriodicoVida.Components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.withContext

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.withContext


@Composable
fun PdfViewerScreenWeb(context: Context, pdfUrl: String) {
    var pdfPages by remember { mutableStateOf<List<Bitmap>>(emptyList()) }

    println("PDF URL: $pdfUrl")
    LaunchedEffect(Unit) {
        val pdfFile = downloadPdf(context, pdfUrl)
        pdfPages = renderPdfToBitmaps(context, pdfFile)
    }

    val scrollState = rememberScrollState()

    // Display PDF pages as images
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .background(color = Color.Green)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        pdfPages.forEach { bitmap ->
            ZoomableImage(bitmap = bitmap)
        }
    }
}

@Composable
fun ZoomableImage(bitmap: Bitmap) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    offsetX += pan.x
                    offsetY += pan.y
                }
            }
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                ),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop

        )
    }
}


private suspend fun downloadPdf(context: Context, pdfUrl: String): File =
    withContext(Dispatchers.IO) {
        val url = URL(pdfUrl)
        val connection = url.openConnection()
        val inputStream = connection.getInputStream()
        val file = File(context.cacheDir, "downloaded.pdf")
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        file
    }

private suspend fun renderPdfToBitmaps(context: Context, pdfFile: File): List<Bitmap> =
    withContext(Dispatchers.IO) {
        val bitmaps = mutableListOf<Bitmap>()
        val parcelFileDescriptor =
            ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(parcelFileDescriptor)

        for (i in 0 until pdfRenderer.pageCount) {
            val page = pdfRenderer.openPage(i)
            val bitmap = Bitmap.createBitmap(
                page.width, page.height, Bitmap.Config.ARGB_8888
            )
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmaps.add(bitmap)
            page.close()
        }

        pdfRenderer.close()
        parcelFileDescriptor.close()

        bitmaps
    }