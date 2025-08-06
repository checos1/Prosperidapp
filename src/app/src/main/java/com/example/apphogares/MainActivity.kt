package com.example.apphogares

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.apphogares.backEnd.core.models.BuildConfigApp
import com.example.apphogares.frontEnd.AppNavigation
import com.example.apphogares.frontEnd.MainViewModel
import com.example.apphogares.frontEnd.RoutesNav
import com.example.apphogares.frontEnd.screens.Components.notificationCountIcon
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
/*import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability*/
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint


private const val MY_REQUEST_CODE = 123

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.IMMEDIATE

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getInitialParameters(this)

        //Calcula el tamaño de la pantalla y lo pasa a DP medida utilizada para gestionar los elementos
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val (screenWidthPx, screenHeightPx) = displayMetrics.widthPixels to displayMetrics.heightPixels
        val (screenWidthDp, screenHeightDp) =
            (screenWidthPx / Resources.getSystem()
                .getDisplayMetrics().density).toInt() to (screenHeightPx / Resources.getSystem()
            .getDisplayMetrics().density).toInt()

        setContent {
            GetBuildConfig()

            appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
            checkForAppUpdates()

            notificationCountIcon(this, 1)

            AppNavigation(mainViewModel, screenWidthDp, screenHeightDp).AppNavigationHost()
        }
        handleIntent(intent)
    }

    private fun GetBuildConfig() {
        AppHogaresAplication.buildConfigApp = BuildConfigApp(
            BASE_URL = getString(R.string.base_url),
            API_KEY = getString(R.string.api_key),
            ACCESS_TOKEN = getString(R.string.access_token),
            ACCESS_TOKEN1 = getString(R.string.access_token1),
            URL_STORAGE = getString(R.string.url_storage),
            containerName = getString(R.string.container_name),
            baseURLStorage = getString(R.string.base_url_storage)
        )
    }

    private fun checkForAppUpdates(){
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, updateType, this,
                    MY_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MY_REQUEST_CODE) {
            // Handle the result of the update request here
            if (resultCode != RESULT_OK) {
                println("Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }


    private fun getInitialParameters(mainActivity: MainActivity) {
        AppHogaresAplication.context = this

        //println("maninActivity isInternetConectivity: ${AppHogaresAplication.estadoDispositivo.isInternetConectivity}")
        observeFirebaseMessaging()
        AppHogaresAplication.rutaMusic =this.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!.path

    }

    private fun observeFirebaseMessaging() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    AppHogaresAplication.tokenFCM = task.result
                    FirebaseMessaging.getInstance().subscribeToTopic("all")
                }
            }
    }



    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent) // Ensure getIntent() returns the latest intent
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if(AppHogaresAplication.context == null){
            AppHogaresAplication.context = this
        }
        if (intent.getBooleanExtra(RoutesNav.encuestaScreen.route, false)) {
            AppHogaresAplication.funNav(RoutesNav.encuestaScreen.route)
        }

        if (intent.getBooleanExtra(RoutesNav.alertaScreen.route, false)) {
            AppHogaresAplication.funNav(RoutesNav.alertaScreen.route)
        }
    }

}