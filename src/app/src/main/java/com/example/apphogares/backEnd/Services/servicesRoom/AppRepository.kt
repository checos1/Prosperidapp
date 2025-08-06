package com.example.apphogares.backEnd.Services.servicesRoom


import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import com.example.apphogares.AppHogaresAplication
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(private val dao: HogarPreguntaDao) {

    fun getHogarPreguntas(): Flow<HogarListaPreguntas> {
        return dao.getHogarPreguntas()
    }

    suspend fun insert(hogarPreguntaDTO: HogarListaPreguntas) {
        dao.delete()
        hogarPreguntaDTO.idIntegrante = AppHogaresAplication.integranteSeleccionado
        dao.insert(hogarPreguntaDTO)
    }

}