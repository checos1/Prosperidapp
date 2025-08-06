package com.example.apphogares.backEnd.core.models.gamificacion

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Gamificacion (
    var listaEstadoRuta: MutableList<EstadoRuta> = mutableListOf()
)