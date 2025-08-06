package com.example.apphogares.frontEnd.screens.PeriodicoVida.Components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.frontEnd.screens.PeriodicoVida.periodicoVidaViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta


@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebBrowser(modifier: Modifier, viewModel: periodicoVidaViewModel = hiltViewModel()) {
    var webView by remember { mutableStateOf<WebView?>(null) }
    var url = viewModel.periodicoSeleecionado.value?.urlPDF ?: ""
    url = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + url
    //val url = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://query.prod.cms.rt.microsoft.com/cms/api/am/binary/RE4oZ7B"
    println("URL WebBrowser: $url")
    Column(modifier = Modifier.background(BackGroundTopLogin).fillMaxSize()) {
        AndroidView(modifier = Modifier
            .background(color = BackGroundTopLogin)
            .fillMaxSize()
            .padding(8.dp), factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()

                settings.javaScriptEnabled = true
                loadUrl(url)
                webView = this
            }
        }, update = {
            webView?.loadUrl(url)
        })
    }

}

