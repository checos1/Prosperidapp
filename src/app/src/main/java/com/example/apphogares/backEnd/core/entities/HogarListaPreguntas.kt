package com.example.apphogares.backEnd.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HogarPreguntas")
data class HogarListaPreguntas(
    @PrimaryKey val idHogar: String,
    var idIntegrante: String,
    var jsonHogar: String
)