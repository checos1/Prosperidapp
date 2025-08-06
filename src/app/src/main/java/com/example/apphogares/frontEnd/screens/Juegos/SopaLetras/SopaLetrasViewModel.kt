package com.example.apphogares.frontEnd.screens.Juegos.SopaLetras

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SopaLetrasViewModel @Inject constructor(val navegacionApp: NavegacionApp): ViewModel()  {
    val numRows = 10
    val numCols = 10
    //var selectedWord by remember { mutableStateOf("") }
    val selectedWord: MutableState<String> = mutableStateOf("")

    val wordsToHide = listOf("KOTLIN", "ANDROID", "JAVA", "JETPACK", "XML")

    //var selectedWordsList by remember { mutableStateOf(mutableListOf<String>()) }
    var selectedWordsList: MutableList<String> = mutableListOf<String>()

}



