package com.example.apphogares.backEnd.Services.ServicesSystem

import com.example.apphogares.backEnd.core.models.hogarMain.Encuesta
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotificationHandler {
    private val _notificationFlow = MutableSharedFlow<Encuesta>()
    val notificationFlow = _notificationFlow.asSharedFlow()

    suspend fun postNotification(encuesta: Encuesta) {
        _notificationFlow.emit(encuesta)
    }
}

data class NotificationMessage(val title: String, val message: String)
