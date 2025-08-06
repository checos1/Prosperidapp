package com.example.apphogares.backEnd.Services

import com.example.apphogares.backEnd.core.models.hogarMain.LogErrores
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.example.apphogares.AppHogaresAplication
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject

class LogError @Inject constructor(private val api: AuthenticacionApi) {

    suspend fun RegistrarError(error: String, clase: String, metodo: String) {
        try {
            if(AppHogaresAplication.estadoDispositivo.isInternetConectivity)
            {
                val logErrores: LogErrores = LogErrores(
                    0,
                    AppHogaresAplication.Infohogar.idHogar,
                    "APP",
                    error,
                    Date(),
                    clase,
                    metodo
                )
                api.RegistrarLogErrores(logErrores)
            }
        } catch (e: Exception) {
            println("$clase - $metodo}: ${e.message}")
        }
    }

}