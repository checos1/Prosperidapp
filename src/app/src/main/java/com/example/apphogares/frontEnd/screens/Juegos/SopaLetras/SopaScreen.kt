package com.example.apphogares.frontEnd.screens.Juegos.SopaLetras

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.Gray

// Direcciones posibles para ocultar las palabras
enum class Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
}

enum class BounceState { Pressed, Released }



    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun SopadeLetras(
        catCode: String, rutaCode: String, tematicaCode: String,
        sopaViewModel: SopaLetrasViewModel = hiltViewModel()) {

    var matchedPairs by remember { mutableStateOf(0) }
    var matrix = Array(sopaViewModel.numRows) { Array(sopaViewModel.numCols) { ' ' } }
    var currentState: BounceState by remember { mutableStateOf(BounceState.Released) }
    val mContext = LocalContext.current


    // Generar una sopa de letras vacía - Inicializarse
    for (i in 0 until sopaViewModel.numRows) {
        for (j in 0 until sopaViewModel.numCols) {
            matrix[i][j] = ' '
        }
    }

    // introducir las palabras en la matriz
    for (word in sopaViewModel.wordsToHide) {
        val direction = randomDirection()
        hideWordInMatrix(matrix, word, direction)
    }

    //llenar Matriz con letras aleatorias
    matrix =generateRandomPalabrasMatrix (sopaViewModel.numRows,matrix)


    //var selectedWord by remember { mutableStateOf("") }
    var selectedWordIndexes by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }
    //var selectedWordsList by remember { mutableStateOf(mutableListOf<String>()) }

    // Mostrar la sopa de letras generada
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(BackGroundTopRuta),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1.5f)
                .background(BackGroundTopRuta)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
///*                BarraScrollGamificacion(
//                    modifier = Modifier.weight(1.8f).background(BackGroundTopRuta),
//                    visibleBarra = false,
//                    viewModel = hiltViewModel()
//                )*/
            }
        }

        Divider(color = Gray)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1.0f)
                .background(BackGroundTopRuta)
        ) {
            // First column
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Encuentra las palabras escondidas",
                    color = BackGroundTopLogin,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 19.sp, textAlign = TextAlign.Start
                )

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(11.0f)
                .background(BackGroundTopRuta)
        ) {
            // Second column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(BackGroundTopLogin, shape = RoundedCornerShape(16.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Mostrar la sopa de letras generada
                InteractiveSoupOfLetters(matrix, sopaViewModel)
            }

            // Show the selected word
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,

                ) {

                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    state = rememberLazyGridState(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    matchedPairs++
                    itemsIndexed(sopaViewModel.wordsToHide) { index, palabra ->
                        Text(
                            text = palabra,
                            color = (if (sopaViewModel.selectedWordsList.contains(palabra)) Color.Black else BackGroundTopLogin),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(2.0f)
                .background(BackGroundTopRuta)
        ) {

            //three column
            Column(
                modifier = Modifier.fillMaxSize().fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {}
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SopaScreenPreview() {
    //AppHogaresAplication()
    SopadeLetras("catCode", "rutaCode", "tematicaCode")
}