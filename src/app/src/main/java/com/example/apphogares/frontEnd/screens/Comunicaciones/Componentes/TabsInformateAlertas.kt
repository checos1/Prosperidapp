package com.example.apphogares.frontEnd.screens.Comunicaciones.Componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apphogares.R
import com.example.apphogares.frontEnd.screens.Comunicaciones.comunicacionesViewModel
import com.example.apphogares.frontEnd.screens.Informate.InformateScreen
import com.example.apphogares.frontEnd.screens.alertas.AlertaScreen
import com.example.apphogares.frontEnd.theme.BackGroundTopLogin
import com.example.apphogares.frontEnd.theme.VerdeCorrecto

@Composable
fun TabsInformateAlertas(comunicacionesViewModel: comunicacionesViewModel = hiltViewModel()) {

    val tabs = listOf("Infórmate", "Alertas")

    Column(
        modifier = Modifier
            .background(BackGroundTopLogin)
    ) {
        TabRow(
            modifier = Modifier.background(BackGroundTopLogin),
            selectedTabIndex = comunicacionesViewModel.selectedTab.value,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[comunicacionesViewModel.selectedTab.value]),
                    color = VerdeCorrecto
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val iconId = when (title) {
                    "Infórmate" -> R.drawable.icon_informate
                    "Alertas" -> R.drawable.icon_alertas
                    else -> R.drawable.ic_launcher_foreground // fallback
                }

                Tab(
                    modifier = Modifier.background(BackGroundTopLogin),
                    selected = comunicacionesViewModel.selectedTab.value == index,
                    onClick = { comunicacionesViewModel.selectedTab.value = index },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = iconId),
                                contentDescription = title,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(20.dp)
                            )
                            Text(text = title)
                        }
                    }
                )
            }
        }

        when (comunicacionesViewModel.selectedTab.value) {
            0 -> InformateScreen()
            1 -> AlertaScreen()
        }
    }
}

