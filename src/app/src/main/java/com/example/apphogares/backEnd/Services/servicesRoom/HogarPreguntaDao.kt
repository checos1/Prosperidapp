package com.example.apphogares.backEnd.Services.servicesRoom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import kotlinx.coroutines.flow.Flow

@Dao
interface HogarPreguntaDao {

    @Query("SELECT * from HogarPreguntas")
    fun getHogarPreguntas(): Flow<HogarListaPreguntas>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HogarListaPreguntas)

    @Update
    suspend fun update(item: HogarListaPreguntas)

    @Query("DELETE from HogarPreguntas")
    suspend fun delete()
}