package com.example.apphogares.frontEnd.screens.PeriodicoVida.Components

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.frontEnd.screens.Components.Video.VideoPlayer

@Composable
fun PDFViewerScreen(pdfPath: String) {
    val url = "https://sapsdafchogaresqa.blob.core.windows.net/cntpsdafchogaresqa/PeriodicoVida/VIDA-tierras-Paz-0124.pdf" + Constants.ACCESS_TOKEN
    Box(
        modifier = Modifier
            .background(color = androidx.compose.ui.graphics.Color.Gray)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
       AndroidView(
            modifier = Modifier.background(Color.Blue).fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    webViewClient = WebViewClient()
                    settings.allowFileAccess = true
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                }
            }
        )
    }

}