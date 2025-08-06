package com.example.apphogares.frontEnd.screens.Juegos.SopaLetras

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.Gray

@Composable
fun InteractiveSoupOfLetters(matrix: Array<Array<Char>>, sopaViewModel: SopaLetrasViewModel = hiltViewModel()) {


    //var selectedWord by remember { mutableStateOf("") }
    var selectedWordIndexes by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }
    var matchedPairs by remember { mutableStateOf(0) }
    //var selectedWordsList by remember { mutableStateOf(mutableListOf<String>()) }

    var firtsColor by rememberSaveable { mutableStateOf(false) }

        // Show the letter matrix
        Column (modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally){
            for (i in matrix.indices) {
                Row {
                    for (j in matrix[i].indices) {
                        val letter = matrix[i][j]
                        var isSelected = selectedWordIndexes.contains(i to j)

                        val textColor by animateColorAsState(targetValue = if (isSelected) BackGroundTopLogin else Gray, animationSpec = tween(durationMillis = 1000) )

                        val backgroundColor by animateColorAsState(targetValue = if (isSelected) Gray else BackGroundTopLogin, animationSpec = tween(durationMillis = 1000) )

                        // Use ClickableText to allow selecting the letters
                        ClickableText(
                            text = AnnotatedString(letter.toString()),
                            onClick = {

                                if (isSelected) {
                                    // Remove letter from selection
                                   sopaViewModel.selectedWord.value = sopaViewModel.selectedWord.value.removeSuffix(letter.toString())
                                    selectedWordIndexes = selectedWordIndexes.filterNot { it.first == i && it.second == j }
                                } else {

                                    // Add letter to selection
                                    sopaViewModel.selectedWord.value += letter
                                    selectedWordIndexes = selectedWordIndexes + (i to j)
                                }

                                // Check if selected letters form a valid word
                                if (sopaViewModel.selectedWord.value.length >= 3)
                                {
                                    if (sopaViewModel.wordsToHide.contains(sopaViewModel.selectedWord.value))
                                    {
                                        // Word is valid, add it to the selectedWordsList
                                        if (!sopaViewModel.selectedWordsList.contains(sopaViewModel.selectedWord.value)) {
                                            sopaViewModel.selectedWordsList.add(sopaViewModel.selectedWord.value)
                                            //println("sopaViewModel 1--> ${sopaViewModel.selectedWordsList.size}")
                                            matchedPairs++
                                            //sopaViewModel.selectedWord.value = ""
                                        }
                                        //isSelected = true
                                    }
                                }
                            },
                            style = TextStyle(color = textColor),
                            modifier = Modifier
                                //.background(realColor)
                                .background(backgroundColor)
                                .size(35.dp)
                                .padding(10.dp)
                        )
                    }
                }
            }
        }


    // Show the selected word
    if (!sopaViewModel.selectedWord.value.isNullOrEmpty())
    {
        Column(
            modifier = Modifier
                .background(Color.Gray.copy(alpha = 0.5f))
        ) {
            Text(
                text = "Palabra seleccionada: ${sopaViewModel.selectedWord.value}",
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
            if (sopaViewModel.selectedWord.value in sopaViewModel.wordsToHide)
            {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    val mjs = "¡Palabra encontrada: ${sopaViewModel.selectedWord.value}!"
                    Text(
                        text = if (sopaViewModel.selectedWord.value in sopaViewModel.wordsToHide) "Encontrada" else "",
                        modifier = Modifier.padding(start = 16.dp),
                        style = TextStyle(fontSize = 20.sp, color = Color.Green,fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
