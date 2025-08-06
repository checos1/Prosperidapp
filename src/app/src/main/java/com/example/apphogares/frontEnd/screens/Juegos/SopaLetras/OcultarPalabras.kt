package com.example.apphogares.frontEnd.screens.Juegos.SopaLetras

import androidx.compose.runtime.MutableState
import kotlin.random.Random

//funcion generar letras aleatoria
fun generateRandomLetras(): Char {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return alphabet[Random.nextInt(alphabet.length)]
}


//llenar matriz con palabras random
fun generateRandomPalabrasMatrix(size: Int,matrix: Array<Array<Char>>): Array<Array<Char>> {
    for (i in 0 until size) {
        for (j in 0 until size) {
            if(matrix[i][j] == ' ')
                matrix[i][j] = generateRandomLetras()
        }
    }
    return matrix
}

// Función para seleccionar una dirección aleatoria
fun randomDirection(): Direction {
    val directions = Direction.values()
    return directions[Random.nextInt(directions.size)]
}
// Función para verificar si una palabra puede ser ocultada en una posición sin superponerse
fun VerificarOcultarWord(matrix: Array<Array<Char>>, word: String, startRow: Int, startCol: Int, direction: Direction): Boolean {
    val numRows = matrix.size
    val numCols = matrix[0].size
    val wordLength = word.length

    when (direction) {
        Direction.HORIZONTAL -> {
            if (startCol + wordLength > numCols) return false
            for (i in 0 until wordLength) {
                if (matrix[startRow][startCol + i] != ' ' && matrix[startRow][startCol + i] != word[i]) {
                    return false
                }
            }
        }
        Direction.VERTICAL -> {
            if (startRow + wordLength > numRows) return false
            for (i in 0 until wordLength) {
                if (matrix[startRow + i][startCol] != ' ' && matrix[startRow + i][startCol] != word[i]) {
                    return false
                }
            }
        }
        Direction.DIAGONAL -> {
            if (startRow + wordLength > numRows || startCol + wordLength > numCols) return false
            for (i in 0 until wordLength) {
                if (matrix[startRow + i][startCol + i] != ' ' && matrix[startRow + i][startCol + i] != word[i]) {
                    return false
                }
            }
        }
    }
    return true
}

// Función para ocultar una palabra en la matriz según la dirección seleccionada
fun hideWordInMatrix(matrix: Array<Array<Char>>, word: String, direction: Direction) {
    val numRows = matrix.size
    val numCols = matrix[0].size
    val wordLength = word.length

    var startRow: Int
    var startCol: Int
    var canHide: Boolean

    do {
        startRow = Random.nextInt(numRows)
        startCol = Random.nextInt(numCols)
        canHide = VerificarOcultarWord(matrix, word, startRow, startCol, direction)
    } while (!canHide)

    when (direction) {
        Direction.HORIZONTAL -> {
            for (i in 0 until wordLength) {
                matrix[startRow][startCol + i] = word[i]
            }
        }
        Direction.VERTICAL -> {
            for (i in 0 until wordLength) {
                matrix[startRow + i][startCol] = word[i]
            }
        }
        Direction.DIAGONAL -> {
            for (i in 0 until wordLength) {
                matrix[startRow + i][startCol + i] = word[i]
            }
        }
    }
}
