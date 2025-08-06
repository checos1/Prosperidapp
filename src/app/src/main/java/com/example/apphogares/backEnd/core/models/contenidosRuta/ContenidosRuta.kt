package com.example.apphogares.backEnd.core.models.contenidosRuta

import com.example.apphogares.backEnd.core.models.Programa
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.example.apphogares.backEnd.core.models.Comunicaciones.Comunicacion
import java.util.Date

data class ContenidosRuta(
    @SerializedName("fechaActualizacion") @Expose var FechaActualizacion: String? = "",
    @SerializedName("categorias") @Expose var Categorias: List<Categoria>? = null,
    @SerializedName("programas") @Expose var Programas: List<Programa>? = null,
    @SerializedName("comunicaciones") @Expose var Comunicaciones: List<Comunicacion>? = null
)