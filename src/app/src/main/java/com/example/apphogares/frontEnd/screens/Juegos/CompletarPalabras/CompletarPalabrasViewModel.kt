package com.example.apphogares.frontEnd.screens.Juegos.CompletarPalabras

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.ServicesSystem.Audio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class itemCMSCompletarPalabras(
    var frase: String,
    var opciones: String
)

@HiltViewModel
class CompletarPalabrasViewModel @Inject constructor(val navegacionApp: NavegacionApp, private val logError: LogError): ViewModel() {

    val contadorSelected: MutableState<Int> = mutableStateOf(0)
    val currentPhraseIndex: MutableState<Int> = mutableStateOf(0)
    val contador: MutableState<Int> = mutableStateOf(1)
    val answer: MutableState<String> = mutableStateOf("")
    val isCorrect: MutableState<Boolean> = mutableStateOf(false)
    val selected: MutableState<Boolean> = mutableStateOf(false)

    val listadoItemCMSCompletarPalabras = mutableListOf<itemCMSCompletarPalabras>()
    val phrases = mutableListOf<String>()
    var answers = mutableListOf<String>()
    val rutaCompletada: MutableState<Boolean> = mutableStateOf(false)

    val activityCompleted: MutableState<Boolean> = mutableStateOf(false)

    val msgError: MutableState<String> = mutableStateOf("")

  val answers_1 = listOf(
      "pluma",
      "Dios",
      "El que busca",
      "mañana"
  )
    val phrases_1 = listOf(
        "La _________ es más poderoso que la espada.",
        "A quien madruga, _________ lo ayuda.",
        "_________ , encuentra.",
        "No dejes para _________ lo que puedas hacer hoy."
    )
    init {
        getFrases()
    }

    private fun getFrases(){

        try {
            var actividadTematica = AppHogaresAplication.listaActividadesTematicaEnCurso.filterIndexed { index, actividade ->
                actividade.tipo == "ComponentHerramientasComplete"
            }.firstOrNull()

            if (actividadTematica != null)
            {
                actividadTematica.complete.split("|").forEach {
                    it.split(" ").forEach{
                        val pala = it.contains (Regex("""\*\*(\w+)"""))
                        if (pala) {
                            //println("actividadTematica 0: " + it)
                            answers.add(it.replace("**",""))
                        }
                    }
                }

                actividadTematica.complete.split("|").forEach {
                    val frase = it.replace(Regex("""\*\*(\w+)"""), "_________").replace("**", "")
                    phrases.add(frase)
                }

                if(answers.size != phrases.size)
                {
                    answers.clear() // Esto vacía la lista
                    phrases.clear() // Esto vacía la lista

                    answers.addAll(answers_1)
                    phrases.addAll(phrases_1)

                }
            }

        }catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(
                    e.message.orEmpty(),
                    "CompletarPalabrasViewModel",
                    "getFrases"
                )
            }
            msgError.value = "Ha ocurrido un error getFrases!!" + e.message
        }

    }

    fun isAnswerCorrect(phraseIndex: Int, respuesta: String): Boolean {

        println("respuesta-->1 ${phraseIndex}")
        println("respuesta-->2 ${respuesta}")
        return respuesta.equals(answers[phraseIndex], ignoreCase = true)
    }

   fun InicializarPaso() {
       contador.value++
       currentPhraseIndex.value++
       answer.value = ""
       isCorrect.value = false
       selected.value = false
      contadorSelected.value = 0
   }

    fun ActualizaActividaTematica(tematicaCode: String, indexActividadEnCurso: Int){

        viewModelScope.launch {
            try {
                navegacionApp.ActualizaActividaTematica(tematicaCode, indexActividadEnCurso)
            }catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "CompletarPalabrasViewModel", "ActualizaActividaTematica")
                msgError.value = "Ha ocurrido un error!!" + e.message
            }

        }

    }

    fun playAudio(i: Int) {
        viewModelScope.launch {
            Audio().startAudioFileFromPositionGamificacion(i)
            delay(2000)
        }
    }
}