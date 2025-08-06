package com.example.apphogares.backEnd.Services.ServicesSystem


import com.example.apphogares.backEnd.core.models.actividadesRuta.TematicaGamificacion
import com.example.apphogares.backEnd.core.models.contenidosRuta.Categoria
import com.example.apphogares.backEnd.core.models.contenidosRuta.Tematica
import com.example.apphogares.AppHogaresAplication


object GamificacionTematica {

    fun ObtenerListaTematicasGamificacion(categorias: List<Categoria>?): MutableList<TematicaGamificacion> {
        val listadoTematicasGamificacion = mutableListOf<TematicaGamificacion>()

        categorias.orEmpty().forEach {
            it.rutas.forEach {
                it.tematicas.forEach {
                    println("Ruta tematicas: ${it}")
                    if(it.id != null && it.codigo != null) {
                        listadoTematicasGamificacion.add(
                            TematicaGamificacion(it.id, it.codigo, 10, 5, 1 )
                        )
                    }
                }
            }
        }

        return listadoTematicasGamificacion
    }

    fun ObtenerTematicaGamificacion(tematica: Tematica): TematicaGamificacion {
        val retorno: TematicaGamificacion? = AppHogaresAplication.listaTematicaGamificacion.first { it.codigo == tematica.codigo }
        return retorno ?: TematicaGamificacion(tematica.id, tematica.codigo, 0, 0, 0)
    }


    fun SubirPosicion(tematica: Tematica) {
        var tematicaGamificacion = ObtenerTematicaGamificacion(tematica)
        tematicaGamificacion.posicionBarra = tematicaGamificacion.posicionBarra + 1
        AppHogaresAplication.listaTematicaGamificacion.remove(tematicaGamificacion)
        AppHogaresAplication.listaTematicaGamificacion.add(tematicaGamificacion)
    }

    fun BajarPosicion(tematica: Tematica) {
        var tematicaGamificacion = ObtenerTematicaGamificacion(tematica)
        tematicaGamificacion.posicionBarra = tematicaGamificacion.posicionBarra - 1
        AppHogaresAplication.listaTematicaGamificacion.remove(tematicaGamificacion)
        AppHogaresAplication.listaTematicaGamificacion.add(tematicaGamificacion)
    }

}