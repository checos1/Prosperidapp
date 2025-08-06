package com.example.apphogares.frontEnd.screens.ruta.Categorias

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.ServicesSystem.NavegacionApp
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.contenidosRuta.Tematica
import com.example.apphogares.backEnd.core.models.gamificacion.EstadoTematicaEnum
import com.example.apphogares.frontEnd.RoutesNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ItemCategoria(
    val nombre: String,
    val totalTematicas: Int,
    val tematicasCompletadas: Int,
    val categoriaCompletada: Boolean = totalTematicas == tematicasCompletadas,
    var imagen: String
)

@HiltViewModel
class categoriaViewModel @Inject constructor(private val navegacionApp: NavegacionApp,
                                             private val logError: LogError): ViewModel() {

    var listaCategorias : MutableState<List<Categoria>> = mutableStateOf(emptyList())
    val msgError: MutableState<String> = mutableStateOf("")
    var listaItemCategoria: MutableState<List<ItemCategoria>> = mutableStateOf(emptyList())

    init {
        AppHogaresAplication.screenActual = RoutesNav.alertaScreen.route
        viewModelScope.launch {
            try {
                listaCategorias.value = AppHogaresAplication.contenidoCMS.Categorias!!
                if (listaCategorias.value.isEmpty()) {
                    msgError.value = "No hay categorias disponibles"
                }
                listaItemCategoria.value = listaCategorias.value.map { categoria ->
                    ItemCategoria(
                        nombre = categoria.nombre,
                        totalTematicas = ObtenerTematicas(categoria).size,
                        tematicasCompletadas = ObtenerTematicasCompletadas(categoria),
                        imagen = ObtenerImagenCategoria(categoria)
                    )
                }
                var indexCategoriaActiva = 0
                listaItemCategoria.value.forEach { itemCategoria ->
                    if(itemCategoria.tematicasCompletadas > 0) {
                        indexCategoriaActiva++
                        //AppHogaresAplication.currentCategoria = listaCategorias.value.find { it.nombre == itemCategoria.nombre } ?: AppHogaresAplication.currentCategoria
                    }
                }
                AppHogaresAplication.currentCategoria = listaCategorias.value[indexCategoriaActiva]
                if (indexCategoriaActiva == 0) {
                    listaItemCategoria.value[0].imagen = listaCategorias.value[0].imagenActiva
                }
            } catch (e: Exception) {
                logError.RegistrarError(e.message.orEmpty(), "categoriaViewModel", "init")
                msgError.value = "Ha ocurrido un error categoriaViewModel init!!" + e.message
            }
        }
        agregarVisitaHija("Categoria", "RutaCategoria")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarVisitaHija(vistaPadre:String, vistaHija:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                navegacionApp.AgregarVisitaHija(vistaPadre, vistaHija)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                logError.RegistrarError(e.message.orEmpty(), "categoriaViewModel", "agregarVisitaHija")
            }
            msgError.value = "Ha ocurrido un error agregarVisitaHija!!" + e.message
        }
    }

    fun ObtenerTematicas(categoria: Categoria): List<Tematica> {
        val tematicas = mutableListOf<Tematica>()
        for (ruta in categoria.rutas) {
            tematicas.addAll(ruta.tematicas)
        }
        return tematicas
    }

    fun ObtenerTematicasCompletadas(categoria: Categoria): Int {
        var contador = 0
        for (ruta in categoria.rutas) {
            for (tematica in ruta.tematicas) {
                AppHogaresAplication.listaEstadosTematicas.forEach { estado ->
                    if (estado.codigo == tematica.codigo && estado.estadoTematica == EstadoTematicaEnum.Realizado) {
                        contador++
                    }
                }
            }
        }
        return contador
    }

    fun ObtenerImagenCategoria(categoria: Categoria): String {
        val totalTematicas = ObtenerTematicas(categoria).size
        val totalTematicasCompletadas = ObtenerTematicasCompletadas(categoria)
        if (totalTematicasCompletadas > 0) {
            return categoria.imagenActiva
        } else {
            return  categoria.imagenInactiva
        }
    }

    fun onCategoriaSelected(
        itemCategoria: ItemCategoria
    ) {
        val listaCategoria = listaCategorias.value.find { it.nombre == itemCategoria.nombre }
        if (listaCategorias != null) {
            AppHogaresAplication.currentCategoria = listaCategoria!!
        } else {
            AppHogaresAplication.currentCategoria = listaCategorias.value[0]
        }
    }
}
