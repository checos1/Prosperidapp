package com.example.apphogares.backEnd.core.models.hogarMain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//Define el modelo para almacenar respuesta
//Definen el objeto Hogar y Preguntas, y construye metodo hogarJson para reemplazar respuesta de API
data class HogarPreguntas(
    @SerializedName("idHogar") @Expose var idHogar: String = "",
    @SerializedName("idIntegranteHogar") @Expose var idIntegranteHogar: String = "",
    @SerializedName("flag") @Expose var flag: Boolean? = false,
    @SerializedName("listaPreguntas") @Expose var listaPreguntas: List<Preguntas>? = null,
    @SerializedName("hogar") @Expose var hogar: Hogar? = Hogar(),
)

data class Preguntas(
    @SerializedName("pregunta") @Expose var pregunta: String? = null,
    @SerializedName("codigoPregunta") @Expose var codigoPregunta: Int? = null,
    @SerializedName("respuestas") @Expose var respuestas: List<String>? = null,
    @SerializedName("peso") @Expose var peso: String? = null,
    @SerializedName("seleccion") @Expose var seleccion: String? = null,
)
