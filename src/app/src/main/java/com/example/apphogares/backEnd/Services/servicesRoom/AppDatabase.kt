package com.example.apphogares.backEnd.Services.servicesRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas


@Database(entities = [HogarListaPreguntas::class], version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

    abstract val hogarPreguntaDao: HogarPreguntaDao

}