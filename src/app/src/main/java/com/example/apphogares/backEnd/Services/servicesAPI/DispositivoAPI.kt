package com.example.apphogares.backEnd.Services.servicesAPI

import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.hogarMain.Dispositivo
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface DispositivoAPI {
    @Headers("Content-Type: application/json")
    @POST("GestionDispositivo/Actualizar")
    suspend fun Registrar(@Body dispositivo: Dispositivo, @Query("code") code:String  = Constants.API_KEY): Dispositivo

    @Headers("Content-Type: application/json")
    @POST("GestionDispositivo/Verificar")
    suspend fun Verificar(@Body dispositivo: Dispositivo, @Query("code") code:String  = Constants.API_KEY): Dispositivo

}