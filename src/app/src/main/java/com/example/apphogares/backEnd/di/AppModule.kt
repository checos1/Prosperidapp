package com.example.apphogares.backEnd.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.apphogares.backEnd.Services.servicesAPI.ContenidoCMSAPI
import com.example.apphogares.backEnd.Services.servicesAPI.DispositivoAPI
import com.example.apphogares.backEnd.Services.servicesAPI.NotificacionAPI
import com.example.apphogares.backEnd.Services.servicesAPI.PQRApi
import com.example.apphogares.backEnd.Services.servicesAPI.PQRDocumentAPI
import com.example.apphogares.backEnd.Services.servicesAPI.PublicacionAPI
import com.example.apphogares.backEnd.Services.servicesRoom.AppDatabase
import com.example.apphogares.backEnd.Services.servicesRoom.AppRepository
import com.example.apphogares.backEnd.core.enums.Constants
import com.example.apphogares.backEnd.repositories.servicesAPI.AuthenticacionApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(90, TimeUnit.SECONDS) // Adjust the timeout duration as needed
            .readTimeout(90, TimeUnit.SECONDS)    // Adjust the read timeout as needed
            .writeTimeout(90, TimeUnit.SECONDS)   // Adjust the write timeout as needed
            .build()
        val response = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return response
    }

    @Singleton
    @Provides
    fun provideApiLogin(retrofit: Retrofit): AuthenticacionApi {
        return retrofit.create(AuthenticacionApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApiPQR(retrofit: Retrofit): PQRApi {
        return retrofit.create(PQRApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApiNotificacion(retrofit: Retrofit): NotificacionAPI {
        return retrofit.create(NotificacionAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideApiContenidoCMS(retrofit: Retrofit): ContenidoCMSAPI {
        return retrofit.create(ContenidoCMSAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideApiDispositivo(retrofit: Retrofit): DispositivoAPI {
        return retrofit.create(DispositivoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "AppHogar_database2"
    )
    .fallbackToDestructiveMigration()
    .build()

    @Provides
    @Singleton
    fun provideRepository(db: AppDatabase): AppRepository {
        return AppRepository(db.hogarPreguntaDao)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideApiPQRDocument(retrofit: Retrofit): PQRDocumentAPI {
        return retrofit.create(PQRDocumentAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideApiPublicacion(retrofit: Retrofit): PublicacionAPI {
        return retrofit.create(PublicacionAPI::class.java)
    }
}



