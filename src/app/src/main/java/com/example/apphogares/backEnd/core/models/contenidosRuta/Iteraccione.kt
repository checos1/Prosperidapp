package com.example.apphogares.backEnd.core.models.contenidosRuta

data class Iteraccione(
    val audio_fin: String,
    val audio_inicio: String,
    val dialogo: String,
    val personaje: String,
    val pregunta: String,
    val respuestas: List<Respuesta>,
    val tipo: String
)