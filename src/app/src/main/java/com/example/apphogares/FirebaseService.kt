package com.example.apphogares


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.apphogares.backEnd.Services.ServicesSystem.PreferencesManager
import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import com.example.apphogares.backEnd.core.models.hogarMain.Notificacion
import com.example.apphogares.backEnd.core.models.hogarMain.EstadoEncuesta
import com.example.apphogares.frontEnd.RoutesNav
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson



class FirebaseService () : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        AppHogaresAplication.tokenFCM = token
    }

    @SuppressLint("MissingPermission")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val preferencesManager = PreferencesManager()
        println("onMessageReceived ${remoteMessage}")
        remoteMessage.data.forEach {
            println("onMessageReceived ${it.key} ${it.value}")
            if (it.key == "message"){
                GestionarNotificacion(it, preferencesManager)
            }
            if (it.key == "encuesta"){
                GestionarEncuesta(it, preferencesManager)
            }
            if (it.key == "encuestaCategoria"){
                GestionarEncuestaCategoria(it, preferencesManager)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun GestionarEncuesta(
        remoteMessage: Map.Entry<String, String>,
        preferencesManager: PreferencesManager
    ) {
        val message = remoteMessage.value.toString()
        println("encuesta onMessageReceived $message")
        val encuesta = Gson().fromJson(message, Encuesta::class.java)
        val existeEncuesta =
            AppHogaresAplication.Infohogar.hogar?.encuestas!!.filterIndexed { _, survey ->
                encuesta.id == survey.id
            }.isNotEmpty()
        if (!existeEncuesta) {
            encuesta.estado = EstadoEncuesta.ABIERTA
            AppHogaresAplication.Infohogar.hogar?.encuestas!!.add(encuesta)

            val encuestasJson = Gson().toJson(AppHogaresAplication.Infohogar.hogar?.encuestas)

            preferencesManager.saveData("encuestas", encuestasJson)

            AppHogaresAplication.encuestas =
                AppHogaresAplication.Infohogar.hogar?.encuestas?.toMutableList()
                    ?: mutableListOf()

            // Create an intent with the target destination within your Compose hierarchy
            val intent = Intent(this, MainActivity::class.java).apply {
                // Set flags to bring the app to the foreground if it's running in the background
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                // Add extra data to inform MainActivity which screen to navigate to
                putExtra(RoutesNav.encuestaScreen.route, true)
            }

            // Create a PendingIntent with the intent to open MainActivity
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "com.example.apphogares"
                val descriptionText = "Alertas de la aplicación ProsperApp"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel =
                    NotificationChannel("com.example.apphogares", name, importance).apply {
                        description = descriptionText
                    }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
            val notificationId = encuesta.id.toInt() // Unique ID for your notification
            val notificationBuilder =
                NotificationCompat.Builder(this, "com.example.apphogares").apply {
                    setSmallIcon(R.mipmap.ic_launcher) // Replace with your app's icon
                    setContentTitle("Nuevas encuestas")
                    setContentText("Usted tiene una nueva encuesta.")
                    setContentIntent(pendingIntent)
                    setNumber(AppHogaresAplication.alertas.size) // Set this to the count of notifications you want to display
                    priority = NotificationCompat.PRIORITY_DEFAULT
                }

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, notificationBuilder.build())
            }
            AppHogaresAplication.GlobalState.updateGlobalState(encuesta)
        }
    }

    @SuppressLint("MissingPermission")
    private fun GestionarNotificacion(
        remoteMessage: Map.Entry<String, String>,
        preferencesManager: PreferencesManager
    ) {
        val message = remoteMessage.value.toString()

        val notificacion = Gson().fromJson(message, Notificacion::class.java)
        println("notificacion onMessageReceived $notificacion")
        val existeNotificacion =
            AppHogaresAplication.Infohogar.hogar?.notificaciones!!.filterIndexed { _, notification ->
                notificacion.id == notification.id
            }.isNotEmpty()
        if (!existeNotificacion) {
            AppHogaresAplication.Infohogar.hogar?.notificaciones!!.add(notificacion)
            val notificacionesJson = Gson().toJson(AppHogaresAplication.Infohogar.hogar?.notificaciones)

            preferencesManager.saveData("notificaciones", notificacionesJson)

            AppHogaresAplication.alertas =
                AppHogaresAplication.Infohogar.hogar?.notificaciones?.toMutableList()
                    ?: mutableListOf()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "com.example.apphogares"
                val descriptionText = "Alertas de la aplicación ProsperApp"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel =
                    NotificationChannel("com.example.apphogares", name, importance).apply {
                        description = descriptionText
                    }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
            val notificationId = notificacion.id.toInt() // Unique ID for your notification
            val notificationBuilder =
                NotificationCompat.Builder(this, "com.example.apphogares").apply {
                    setSmallIcon(R.mipmap.ic_launcher) // Replace with your app's icon
                    setContentTitle("Nuevas notificaciones")
                    setContentText("Usted tiene nuevas notificaciones.")
                    setNumber(AppHogaresAplication.alertas.size) // Set this to the count of notifications you want to display
                    priority = NotificationCompat.PRIORITY_DEFAULT
                }

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, notificationBuilder.build())
            }
//
//            // Create an intent with the target destination within your Compose hierarchy
//            val intent = Intent(this, MainActivity::class.java).apply {
//                // Set flags to bring the app to the foreground if it's running in the background
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                // Add extra data to inform MainActivity which screen to navigate to
//                putExtra(RoutesNav.alertaScreen.route, true)
//            }
//
//            // Create a PendingIntent with the intent to open MainActivity
//            val pendingIntent = PendingIntent.getActivity(
//                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun GestionarEncuestaCategoria(
        remoteMessage: Map.Entry<String, String>,
        preferencesManager: PreferencesManager
    ) {
        val message = remoteMessage.value.toString()
        println("encuesta Categoria onMessageReceived $message")
        val encuesta = Gson().fromJson(message, Encuesta::class.java)
        val existeEncuesta =
            AppHogaresAplication.Infohogar.hogar?.encuestas!!.filterIndexed { _, survey ->
                encuesta.id == survey.id
            }.isNotEmpty()
        if (!existeEncuesta) {
            encuesta.estado = EstadoEncuesta.ABIERTA
            AppHogaresAplication.Infohogar.hogar?.encuestas!!.add(encuesta)

            val encuestasJson = Gson().toJson(AppHogaresAplication.Infohogar.hogar?.encuestas)

            preferencesManager.saveData("encuestasCategoria", encuestasJson)

            AppHogaresAplication.encuestas =
                AppHogaresAplication.Infohogar.hogar?.encuestas?.toMutableList()
                    ?: mutableListOf()

            AppHogaresAplication.encuestas.sortedBy { it.orden }

            preferencesManager.saveData("encuestasCategoria", AppHogaresAplication.encuestas.toString())

            AppHogaresAplication.Infohogar.hogar?.encuestas = AppHogaresAplication.encuestas
            println("encuesta Categoria onMessageReceived ${Gson().toJson(AppHogaresAplication.encuestas)}")
        }
    }
}