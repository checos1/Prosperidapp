package com.example.apphogares.backEnd.Services.servicesAPI
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.core.models.hogarMain.LogErrores
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PQRApi {
    @Headers("Content-Type: application/json")
    @POST("GestionPQR/AgregarPQR")
    suspend fun AgregarPQR(@Body pqr: PQRDocument, @Query("code") code:String  = Constants.API_KEY): Boolean

    @GET("GestionPQR/ConsultarPQRCMSXIdhogar/{idHogar}")
    suspend fun ObtenerPQRs(@Path("idHogar") idHogar: String,  @Query("code") code:String  = Constants.API_KEY): MutableList<PQRDocument>
}
