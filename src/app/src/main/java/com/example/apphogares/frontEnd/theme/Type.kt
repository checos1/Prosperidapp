package com.example.dpsapp.ui.theme

import android.icu.text.ListFormatter.Width
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.apphogares.R
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.BackGroundTopRuta
import com.example.apphogares.frontEnd.theme.BlueTwo
import com.example.apphogares.frontEnd.theme.Gray
import com.example.apphogares.frontEnd.theme.White
import com.example.apphogares.frontEnd.theme.Yellow

val RobotoDefault = FontFamily(Font(R.font.roboto))
val NunitoDefault = FontFamily(Font(R.font.nunitosans))

// Set of Material typography styles to start with
val Typography = Typography(

    //Usamos esta fuente y configuración para titulos principales
    titleLarge = TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = White
    ),

    titleMedium = TextStyle(
        fontFamily = NunitoDefault,
        fontSize = 19.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.sp,
        color = Gray
    ),

    //Usamos esta fuente y configuración para subtitulos
    titleSmall = TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Gray
    ),

    headlineMedium = TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp,
        color = Gray
    ),

    headlineLarge = TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp,
        color = BackGroundTopRuta
    ),

    bodyMedium = TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.sp,
        color = BackGroundTopLogin
    ),


    bodyLarge = TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp,
        color = White
    ),

    bodySmall = TextStyle(
        fontFamily = NunitoDefault,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
        color = White,
        textAlign = TextAlign.Justify
    ),

    headlineSmall = TextStyle(
        fontFamily = NunitoDefault,
        fontSize = 11.sp,
        lineHeight = 8.sp,
        letterSpacing = 0.sp,
        color = White
    ),


    displayMedium = TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 19.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp,
        color = Gray
    ),

    displaySmall= TextStyle(
        fontFamily = NunitoDefault,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 19.sp,
        lineHeight = 20.sp,
        letterSpacing = 2.sp,
        color = White
    ),



)