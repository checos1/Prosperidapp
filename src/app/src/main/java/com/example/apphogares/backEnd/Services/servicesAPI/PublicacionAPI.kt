package com.example.apphogares.backEnd.Services.servicesAPI

import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.Comunicaciones.InteraccionRequest
import com.example.apphogares.backEnd.core.models.Comunicaciones.InteraccionesPublicacion
import com.example.apphogares.backEnd.core.models.Comunicaciones.Publicacion
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface PublicacionAPI {

    @GET("GestionComunicacion/GetPublicacion")
    suspend fun ObtenerPublicaciones(@Query("code") code:String  = Constants.API_KEY): MutableList<Publicacion>

    @Headers("Content-Type: application/json")
    @POST("GestionComunicacion/InteraccionPublicacion")
    suspend fun InteraccionPublicacion(@Body interaccionRequest: InteraccionRequest, @Query("code") code:String  = Constants.API_KEY): InteraccionesPublicacion
}