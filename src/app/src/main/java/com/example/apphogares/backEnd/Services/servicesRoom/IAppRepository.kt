package com.example.apphogares.backEnd.Services.servicesRoom

import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import kotlinx.coroutines.flow.Flow

interface IAppRepository  {

    fun getHogarPreguntas(id: Int): Flow<HogarListaPreguntas>

    suspend fun insert(hogarPreguntaDTO: HogarListaPreguntas)

    suspend fun delete(hogarPreguntaDTO: HogarListaPreguntas)
}