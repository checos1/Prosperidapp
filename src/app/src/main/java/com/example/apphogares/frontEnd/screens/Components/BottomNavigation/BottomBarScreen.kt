package com.example.apphogares.frontEnd.screens.Components.BottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object MiHogar : BottomBarScreen(
        route = "MiHogar",
        title = "Mi Hogar",
        icon = Icons.Default.Home
    )

    object Alertas : BottomBarScreen(
        route = "Alertas",
        title = "Alertas",
        icon = Icons.Default.Warning
    )

    object Ruta : BottomBarScreen(
        route = "Ruta",
        title = "Ruta",
        icon = Icons.Default.Place
    )

    object Ofertas : BottomBarScreen(
        route = "Ofertas",
        title = "Ofertas",
        icon = Icons.Default.Settings
    )

    object Certificaciones : BottomBarScreen(
        route = "ProsperApp",
        title = "Prosperidad",
        icon = Icons.Default.Settings
    )
}