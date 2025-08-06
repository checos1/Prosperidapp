package com.example.apphogares.backEnd.core.models.Comunicaciones

data class Publicacion(
    val id: String,
    val titulo: String,
    val compartidos: Int = 0,
    val likes: Int = 0,
    val carrusel: List<String>,
    val banner: String,
    val ordenar: Int,
    val videoYoutube: String,
    val descripcion: String,
    val fechaVencimiento: String,
    val tipo: String,
    val imagenAuxiliar: String =""
)


