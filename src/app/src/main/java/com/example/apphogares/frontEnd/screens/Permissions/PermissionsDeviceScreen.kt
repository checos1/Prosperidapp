package com.example.apphogares.frontEnd.screens.Permissions

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsDeviceScreen(funNav: (String) -> Unit, permissionsViewModel: PermissionsViewModel = hiltViewModel()) {

    val preferenceSettings = listOf(
        ReadPhoneStateSetting,
        RECORD_AUDIOSetting,
        ACCESS_FINE_LOCATIONSetting,
        ACCESS_COARSE_LOCATIONSetting,
        FOREGROUND_SERVICESetting,
        CAMERASetting,
        READ_EXTERNAL_STORAGESetting
    )


    @OptIn(ExperimentalPermissionsApi::class)
    val permissionsState = rememberMultiplePermissionsState(
        permissions = preferenceSettings.map { it.key }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundTopLogin)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(BackGroundTopLogin)
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            MessagePermisssions()
            Spacer(modifier = Modifier.size(20.dp))

            Spacer(modifier = Modifier.size(30.dp))

            Button(
                modifier = Modifier
                    .width(200.dp)
                    .background(
                        color = BackGroundTopLoginPlus,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    permissionsState.launchMultiplePermissionRequest()
                }
            ) {
                Text(
                    text = "ACEPTAR", textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium, color = Color.Black
                )
            }

           if (permissionsState.permissionRequested) {
               if (!permissionsState.allPermissionsGranted){
                   val shouldShowRationale = permissionsState.permissions.any { it.shouldShowRationale }

                   if (shouldShowRationale) {
                       // Show a rationale to the user explaining why the permission is needed

                   } else {
                       //permissionsViewModel.validarContenidoCMS()
                       funNav(RoutesNav.rutaScreen.route)
                   }
               }else{
                   permissionsViewModel.getInformationDevice(LocalContext.current)
                   //permissionsViewModel.validarContenidoCMS()
                   funNav(RoutesNav.rutaScreen.route)
               }
            }
        }
    }

    if (permissionsViewModel.messageError.value != "") {
        MessageSnackBar(text = permissionsViewModel.messageError.value)
        permissionsViewModel.messageError.value == ""
    }
}


