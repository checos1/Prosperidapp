package com.example.apphogares.frontEnd.screens.PeriodicoVida.Components

import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieCompositionFactory.fromAsset


@Composable
fun PdfViewerComponent(url: String) {

/*    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PDFView(context, null).apply {
                    // Load your PDF from assets or file here
                    fromAsset(url).load()
                }
            }
        )
    }*/

}