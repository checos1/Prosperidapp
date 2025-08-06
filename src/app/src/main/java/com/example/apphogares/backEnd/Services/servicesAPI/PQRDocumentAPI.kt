package com.example.apphogares.backEnd.Services.servicesAPI

import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface PQRDocumentAPI {
    @Headers("Content-Type: application/json")
    @POST("GestionPQRDocument/CrearPQR")
    suspend fun CrearPQR(@Body pqr: PQRDocument, @Query("code") code:String  = Constants.API_KEY): PQRDocument

    @GET("GestionPQRDocument/ObtenerPQRs/{idHogar}")
    suspend fun ObtenerPQRs(@Path("idHogar") idHogar: String, @Query("code") code:String  = Constants.API_KEY): MutableList<PQRDocument>
}