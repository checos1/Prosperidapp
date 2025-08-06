package com.example.apphogares.backEnd.core.models.PQRs

import androidx.compose.ui.graphics.Color.Companion.Green
import com.example.apphogares.frontEnd.theme.BackGroundTopLoginPlus
import com.example.apphogares.frontEnd.theme.BackgroundBottomInTo
import com.example.apphogares.frontEnd.theme.Blue
import com.example.apphogares.frontEnd.theme.BlueSky
import com.example.apphogares.frontEnd.theme.Red
import com.example.apphogares.frontEnd.theme.RedLight

object TiposPeticionesPQR {
    val tipos = listOf(
        TipoPeticionPQR("PETICIÓN", Blue),
        TipoPeticionPQR("QUEJA", BackgroundBottomInTo),
        TipoPeticionPQR("DENUNCIA", BlueSky),
        TipoPeticionPQR("RECLAMO", Green),
        TipoPeticionPQR("FELICITACIÓN", BackGroundTopLoginPlus),
        TipoPeticionPQR("SUGERENCIA", RedLight),
        TipoPeticionPQR("INFORMACIÓN", Red),
        TipoPeticionPQR("INFORMACIÓN PÚBLICA", BackgroundBottomInTo)
    )
}
