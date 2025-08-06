package com.example.apphogares.backEnd.Services.servicesAPI

import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import com.example.apphogares.backEnd.core.models.navegacion.mensajeNotificacion
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContenidoCMSAPI {
    @GET("GestionContenidoCMS/Get")
    suspend fun Get(@Query("fechaActualizacion") fechaActualizacion:String = "", @Query("code") code:String = Constants.API_KEY): ContenidosRuta

    @GET("GestionNotificacion/GetEncuesta/{numeroDocumento}")
    suspend fun GetEncuestaCategoria(@Path("numeroDocumento") numeroDocumento: String, @Query("code") code:String  = Constants.API_KEY): mensajeNotificacion
}