package com.example.apphogares.frontEnd.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import com.example.dpsapp.ui.theme.Typography

private val DarkColorScheme = darkColorScheme(
    primary = BackGroundTopLogin,
    onPrimary = BackGroundTopLogin,
    secondary = BackGroundRuta,
    onSecondary = BackGroundRuta
)

val LightColorScheme = lightColorScheme(
    primary = BackGroundTopLogin,
    onPrimary = BackGroundTopLogin,
    secondary = BackGroundRuta,
    onSecondary = BackGroundRuta
)

@Composable
fun AppHogaresTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /*val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }*/

    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = colorScheme.secondary.toArgb()
            window.statusBarColor = colorScheme.secondary.toArgb()
            //ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}