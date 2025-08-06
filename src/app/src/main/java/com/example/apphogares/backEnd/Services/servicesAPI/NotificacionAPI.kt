package com.example.apphogares.backEnd.Services.servicesAPI


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion

interface NotificacionAPI {

    @GET("GestionNotificacion/GetNotificationsHogar/{idHogar}")
    suspend fun GetNotificationsHogar(@Path("idHogar") idHogar: String,  @Query("code") code:String  = Constants.API_KEY): MutableList<Notificacion>
}