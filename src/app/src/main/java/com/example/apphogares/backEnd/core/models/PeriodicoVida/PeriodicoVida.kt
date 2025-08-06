package com.example.apphogares.backEnd.core.models.PeriodicoVida

//const val URL_BASE = "https://sactestingcjr.blob.core.windows.net/ctntestingcjr/"
const val URL_BASE = "https://sapsdafchogaresprod.blob.core.windows.net/cntpsdafchogaresprod/PeriodicoVida/"
const val URL_BASE_PDF = "${URL_BASE}pdfs/"
const val URL_BASE_IMAGE = "${URL_BASE}images/"
const val ACCES_TOKEN = "?sp=r&amp;st=2024-04-22T15:46:11Z&amp;se=2029-04-22T23:46:11Z&amp;spr=https&amp;sv=2022-11-02&amp;sr=c&amp;sig=A77Zw%2FjnY9HGJ2wxNBsKFJ1VbM%2B7Yufe71WG83ibAJA%3D"
    //"?sv=2022-11-02&ss=bfqt&srt=sco&sp=rwdlacupiytfx&se=2024-10-21T19:53:16Z&st=2024-10-21T11:53:16Z&spr=https,http&sig=mCv%2B6bKlJtfe7Iel0fN7ll9a%2Fg3SwlXPLJaFQUdEV80%3D"


data class PeriodicoVida(
    var id: String = "",
    var titulo: String = "",
    var urlPDF: String = "",
    var imagen: String = ""
)

val periodicoVidaList = listOf(
    PeriodicoVida(id = "1", titulo = "Edición No. VIDA4725", urlPDF = "${URL_BASE_PDF}VIDA4725.pdf", imagen = "${URL_BASE_IMAGE}VIDA4725.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "2", titulo = "Edición No. VIDA4625", urlPDF = "${URL_BASE_PDF}VIDA4625.pdf", imagen = "${URL_BASE_IMAGE}VIDA4625.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "3", titulo = "Edición No. VIDA4525", urlPDF = "${URL_BASE_PDF}VIDA4525.pdf", imagen = "${URL_BASE_IMAGE}VIDA4525.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "4", titulo = "Edición No. VIDA4425", urlPDF = "${URL_BASE_PDF}VIDA4425.pdf", imagen = "${URL_BASE_IMAGE}VIDA4425.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "5", titulo = "Edición No. VIDA4325", urlPDF = "${URL_BASE_PDF}VIDA4325.pdf", imagen = "${URL_BASE_IMAGE}VIDA4325.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "6", titulo = "Edición No. VIDA4225", urlPDF = "${URL_BASE_PDF}VIDA4225.pdf", imagen = "${URL_BASE_IMAGE}VIDA4225.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "7", titulo = "Edición No. VIDA4125", urlPDF = "${URL_BASE_PDF}VIDA4125.pdf", imagen = "${URL_BASE_IMAGE}VIDA4125.jpeg${ACCES_TOKEN}"),
    PeriodicoVida(id = "8", titulo = "Edición No. VIDA4025", urlPDF = "${URL_BASE_PDF}VIDA4025.pdf", imagen = "${URL_BASE_IMAGE}VIDA4025.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "9", titulo = "Edición No. VIDA3925", urlPDF = "${URL_BASE_PDF}VIDA3925.pdf", imagen = "${URL_BASE_IMAGE}VIDA3925.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "10", titulo = "Edición No. VIDA3825", urlPDF = "${URL_BASE_PDF}VIDA3825.pdf", imagen = "${URL_BASE_IMAGE}VIDA3825.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "11", titulo = "Edición No. VIDA3725", urlPDF = "${URL_BASE_PDF}VIDA3725.pdf", imagen = "${URL_BASE_IMAGE}VIDA3725.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "12", titulo = "Edición No. VIDA3625", urlPDF = "${URL_BASE_PDF}VIDA3625.pdf", imagen = "${URL_BASE_IMAGE}VIDA3625.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "13", titulo = "Edición No. VIDA3525", urlPDF = "${URL_BASE_PDF}VIDA3525.pdf", imagen = "${URL_BASE_IMAGE}VIDA3525.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "14", titulo = "Edición No. VIDA3425", urlPDF = "${URL_BASE_PDF}VIDA3425.pdf", imagen = "${URL_BASE_IMAGE}VIDA3425.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "15", titulo = "Edición No. VIDA3325", urlPDF = "${URL_BASE_PDF}VIDA3325.pdf", imagen = "${URL_BASE_IMAGE}VIDA3325.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "16", titulo = "Edición No. VIDA3225", urlPDF = "${URL_BASE_PDF}VIDA3225.pdf", imagen = "${URL_BASE_IMAGE}VIDA3225.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "17", titulo = "Edición No. VIDA3124", urlPDF = "${URL_BASE_PDF}VIDA3124.pdf", imagen = "${URL_BASE_IMAGE}VIDA3124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "18", titulo = "Edición No. VIDA3024", urlPDF = "${URL_BASE_PDF}VIDA3024.pdf", imagen = "${URL_BASE_IMAGE}VIDA3024.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "19", titulo = "Edición No. VIDA2924", urlPDF = "${URL_BASE_PDF}VIDA2924.pdf", imagen = "${URL_BASE_IMAGE}VIDA2924.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "20", titulo = "Edición No. VIDA2824", urlPDF = "${URL_BASE_PDF}VIDA2824.pdf", imagen = "${URL_BASE_IMAGE}VIDA2824.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "21", titulo = "Edición No. VIDAEXTRA0224", urlPDF = "${URL_BASE_PDF}VIDAEXTRA0224.pdf", imagen = "${URL_BASE_IMAGE}VIDAEXTRA0224.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "22", titulo = "Edición No. VIDA2724", urlPDF = "${URL_BASE_PDF}VIDA2724.pdf", imagen = "${URL_BASE_IMAGE}VIDA2724.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "23", titulo = "Edición No. VIDA2624", urlPDF = "${URL_BASE_PDF}VIDA2624.pdf", imagen = "${URL_BASE_IMAGE}VIDA2624.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "24", titulo = "Edición No. VIDA2524", urlPDF = "${URL_BASE_PDF}VIDA2524.pdf", imagen = "${URL_BASE_IMAGE}VIDA2524.JPG${ACCES_TOKEN}"),
    PeriodicoVida(id = "25", titulo = "Edición No. VIDA-2424-English", urlPDF = "${URL_BASE_PDF}VIDA-2424-English.pdf", imagen = "${URL_BASE_IMAGE}VIDA-2424-English.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "26", titulo = "Edición No. EXTRAVIDA", urlPDF = "${URL_BASE_PDF}EXTRAVIDA.pdf", imagen = "${URL_BASE_IMAGE}VIDA-EXTRA.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "27", titulo = "Edición No. VIDA-2424", urlPDF = "${URL_BASE_PDF}VIDA-2424.pdf", imagen = "${URL_BASE_IMAGE}DAVIDA2424.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "28", titulo = "Edición No. VIDA-2324", urlPDF = "${URL_BASE_PDF}VIDA-2324.pdf", imagen = "${URL_BASE_IMAGE}VIDA2423.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "29", titulo = "Edición No. VIDA-2224", urlPDF = "${URL_BASE_PDF}VIDA-2224.pdf", imagen = "${URL_BASE_IMAGE}Vida-2224.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "30", titulo = "Edición No. VIDA-2124", urlPDF = "${URL_BASE_PDF}VIDA-2124.pdf.pdf", imagen = "${URL_BASE_IMAGE}Vida-2124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "31", titulo = "Edición No. VIDA-2024", urlPDF = "${URL_BASE_PDF}VIDA-2024.pdf", imagen = "${URL_BASE_IMAGE}Vida-2024.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "32", titulo = "Edición No. VIDA-1924", urlPDF = "${URL_BASE_PDF}VIDA-1924.pdf", imagen = "${URL_BASE_IMAGE}Vida-1924.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "33", titulo = "Edición No. VIDA-1824", urlPDF = "${URL_BASE_PDF}VIDA-1824.pdf", imagen = "${URL_BASE_IMAGE}Vida-1824.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "34", titulo = "Edición No. VIDA-1724", urlPDF = "${URL_BASE_PDF}VIDA-1724.pdf", imagen = "${URL_BASE_IMAGE}Vida-1724.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "35", titulo = "Edición No. VIDA-1624", urlPDF = "${URL_BASE_PDF}VIDA-1624.pdf", imagen = "${URL_BASE_IMAGE}Vida-1624.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "36", titulo = "Edición No. VIDA-1524", urlPDF = "${URL_BASE_PDF}VIDA-1524.pdf", imagen = "${URL_BASE_IMAGE}Vida-1524.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "37", titulo = "Edición No. VIDA-1424", urlPDF = "${URL_BASE_PDF}VIDA-1424.pdf", imagen = "${URL_BASE_IMAGE}Vida-1424.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "38", titulo = "Edición No. VIDA-1324", urlPDF = "${URL_BASE_PDF}VIDA-1324.pdf", imagen = "${URL_BASE_IMAGE}Vida-1324.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "39", titulo = "Edición No. VIDA_1224", urlPDF = "${URL_BASE_PDF}VIDA_1224.pdf", imagen = "${URL_BASE_IMAGE}Vida-1224.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "40", titulo = "Edición No. VIDA-Mesada-14-1124", urlPDF = "${URL_BASE_PDF}VIDA-Mesada-14-1124.pdf", imagen = "${URL_BASE_IMAGE}Vida_Mesada_14-1124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "41", titulo = "Edición No. VIDA-No-es-un-regalo1024", urlPDF = "${URL_BASE_PDF}VIDA-No-es-un-regalo1024.pdf", imagen = "${URL_BASE_IMAGE}Vida-no-es-un-regalo-1024.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "42", titulo = "Edición No. VIDA-Ofensiva-0924", urlPDF = "${URL_BASE_PDF}VIDA-Ofensiva-0924.pdf", imagen = "${URL_BASE_IMAGE}VIDA-Ofensiva-0924.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "43", titulo = "Edición No. VIDA-bienes-narcos-0824", urlPDF = "${URL_BASE_PDF}VIDA-bienes-narcos-0824.pdf", imagen = "${URL_BASE_IMAGE}Vida-bienes-0824.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "44", titulo = "Edición No. Vida-Democracia-movimiento-0724", urlPDF = "${URL_BASE_PDF}Vida-Democracia-movimiento-0724.pdf", imagen = "${URL_BASE_IMAGE}Vida-democracia-movimiento-0724.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "45", titulo = "Edición No. VIDA-Educacion-0624", urlPDF = "${URL_BASE_PDF}VIDA-Educacion-0624.pdf", imagen = "${URL_BASE_IMAGE}VIDA-Educacion-0624.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "46", titulo = "Edición No. VIDA-Marcha-cambio-0524", urlPDF = "${URL_BASE_PDF}VIDA-Marcha-cambio-0524.pdf", imagen = "${URL_BASE_IMAGE}VIDA-Marcha-cambio-0524.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "47", titulo = "Edición No. VIDA-Llueve-0424", urlPDF = "${URL_BASE_PDF}VIDA-Llueve-0424.pdf", imagen = "${URL_BASE_IMAGE}VIDA-Llueve-0424.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "48", titulo = "Edición No. VIDA-Salud-0324", urlPDF = "${URL_BASE_PDF}VIDA-Salud-0324.pdf", imagen = "${URL_BASE_IMAGE}VIDA-Salud-0324.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "49", titulo = "Edición No. VIDA-Abran-Tren-0224", urlPDF = "${URL_BASE_PDF}VIDA-Abran-Tren-0224.pdf", imagen = "${URL_BASE_IMAGE}VIDA-Abran-Tren-0224.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "50", titulo = "Edición No. VIDA-tierras-Paz-0124", urlPDF = "${URL_BASE_PDF}VIDA-tierras-Paz-0124.pdf", imagen = "${URL_BASE_IMAGE}VIDA-tierras-Paz-0124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "51", titulo = "Edición No. VIDA-tierras-Paz-0124", urlPDF = "${URL_BASE_PDF}VIDA-tierras-Paz-0124.pdf", imagen = "${URL_BASE_IMAGE}VIDA-tierras-Paz-0124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "52", titulo = "Edición No. VIDA-tierras-Paz-0124", urlPDF = "${URL_BASE_PDF}VIDA-tierras-Paz-0124.pdf", imagen = "${URL_BASE_IMAGE}VIDA-tierras-Paz-0124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "53", titulo = "Edición No. VIDA-tierras-Paz-0124", urlPDF = "${URL_BASE_PDF}VIDA-tierras-Paz-0124.pdf", imagen = "${URL_BASE_IMAGE}VIDA-tierras-Paz-0124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "54", titulo = "Edición No. VIDA-tierras-Paz-0124", urlPDF = "${URL_BASE_PDF}VIDA-tierras-Paz-0124.pdf", imagen = "${URL_BASE_IMAGE}VIDA-tierras-Paz-0124.jpg${ACCES_TOKEN}"),
    PeriodicoVida(id = "55", titulo = "Edición No. VIDA-tierras-Paz-0124", urlPDF = "${URL_BASE_PDF}VIDA-tierras-Paz-0124.pdf", imagen = "${URL_BASE_IMAGE}VIDA-tierras-Paz-0124.jpg${ACCES_TOKEN}")
)

