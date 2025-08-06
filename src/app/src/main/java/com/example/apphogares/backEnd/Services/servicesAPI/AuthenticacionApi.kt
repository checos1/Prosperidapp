package com.example.apphogares.backEnd.repositories.servicesAPI

import com.example.apphogares.backEnd.core.entities.HogarListaPreguntas
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.hogarMain.Dispositivo
import com.example.apphogares.backEnd.core.models.hogarMain.HogarPreguntas
import com.example.apphogares.backEnd.core.models.hogarMain.LogErrores
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface AuthenticacionApi {
    @GET("autenticacion/PreguntasValidacion/{tipoDocumento}/{numeroDocumento}")
    suspend fun getPreguntasValidacion(@Path("tipoDocumento") tipoDocumento: String, @Path("numeroDocumento") numeroDocumento: String, @Query("code") code:String  = Constants.API_KEY): HogarPreguntas

    @Headers("Content-Type: application/json")
    @POST("autenticacion/VerificarPreguntas")
    suspend fun verificarPreguntas(@Body respuestas: HogarPreguntas, @Query("code") code:String  = Constants.API_KEY): HogarPreguntas

    @Headers("Content-Type: application/json")
    @POST("autenticacion/RegistrarLogErrores")
    suspend fun RegistrarLogErrores(@Body logErrores: LogErrores, @Query("code") code:String  = Constants.API_KEY): Boolean

    @Headers("Content-Type: application/json")
    @POST("InfoHogar/RegistrarInfohogarApp")
    suspend fun RegistrarInfohogarApp(@Body infoHogarApp: HogarListaPreguntas, @Query("code") code:String  = Constants.API_KEY): String

}

