package com.example.apphogares.frontEnd.screens.InfoHogar.Hogar.Components

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import com.example.apphogares.backEnd.core.models.hogarMain.Integrante
import com.example.apphogares.frontEnd.screens.Components.layouts.CustomDialogUI

@SuppressLint("UnrememberedMutableState")
@Composable
fun ShowMemberInformation(showMemberInformation: MutableState<Boolean>, integrante: Integrante) {
    Dialog(onDismissRequest = { showMemberInformation.value = false} ) {
        CustomDialogUI(openDialogCustom =  showMemberInformation,
            title = "Información de\n${integrante.nombre} ${integrante.apellidos}",
            onClick = { showMemberInformation.value = false},
            modifier = Modifier.clip(RoundedCornerShape(0, 0, 10, 10))
        ){
            InformacionIntegranteScreen(integrante)
        }
    }
}