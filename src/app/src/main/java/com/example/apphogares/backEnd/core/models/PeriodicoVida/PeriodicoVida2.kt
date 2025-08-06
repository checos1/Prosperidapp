/*
package com.example.apphogares.backEnd.core.models.PeriodicoVida

const val URL_BASE = "https://sactestingcjr.blob.core.windows.net/ctntestingcjr/"
const val URL_BASE_PDF = "${URL_BASE}pdfs/"
const val URL_BASE_IMAGE = "${URL_BASE}images/"
//const val URL_BASE_PDF = "https://sapsdafchogaresqa.blob.core.windows.net/cntpsdafchogaresqa/PeriodicoVida/"

data class PeriodicoVida(
    var id: String = "",
    var titulo: String = "",
    var urlPDF: String = "",
    var imagen: String = ""
)

val periodicoVidaList = listOf(
        PeriodicoVida(id = "1", titulo ="Edición No. 1", urlPDF = "https://sapsdafchogaresqa.blob.core.windows.net/cntpsdafchogaresqa/PeriodicoVida/VIDA-tierras-Paz-0124.pdf", imagen = "1.jpg"),
   // PeriodicoVida(id = "1", titulo ="Edición No. 1", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-tierras-Paz-0124.pdf", imagen = "1.jpg"),
    PeriodicoVida(id = "2", titulo ="Edición No. 2", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-Abran-Tren-0224.pdf", imagen = "2.jpg"),
    PeriodicoVida(id = "3", titulo ="Edición No. 3", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-Salud-0324.pdf", imagen = "3.jpg"),
    PeriodicoVida(id = "4", titulo ="Edición No. 4", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-Llueve-0424.pdf", imagen = "4.jpg"),
    PeriodicoVida(id = "5", titulo ="Edición No. 5", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-Marcha-cambio-0524.pdf", imagen = "5.jpg"),
    PeriodicoVida(id = "6", titulo ="Edición No. 6", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-Educacion-0624.pdf", imagen = "6.jpg"),
    PeriodicoVida(id = "7", titulo ="Edición No. 7", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/Vida-Democracia-movimiento-0724.pdf", imagen = "7.jpg"),
    PeriodicoVida(id = "8", titulo ="Edición No. 8", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-bienes-narcos-0824.pdf", imagen = "8.jpg"),
    PeriodicoVida(id = "9", titulo ="Edición No. 9", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-Ofensiva-0924.pdf", imagen = "9.jpg"),
 */
/*      PeriodicoVida(id = "10", titulo ="Edición No. 10", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-No-es-un-regalo1024.pdf", imagen = "10.jpg"),
       PeriodicoVida(id = "11", titulo ="Edición No. 11", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-Mesada-14-1124.pdf", imagen = "11.jpg"),
       PeriodicoVida(id = "12", titulo ="Edición No. 12", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA_1224.pdf", imagen = "12.jpg"),
       PeriodicoVida(id = "13", titulo ="Edición No. 13", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-1324.pdf", imagen = "13.jpg"),
       PeriodicoVida(id = "14", titulo ="Edición No. 14", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-1424.pdf", imagen = "14.jpg"),
       PeriodicoVida(id = "15", titulo ="Edición No. 15", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-1524.pdf", imagen = "15.jpg"),
       PeriodicoVida(id = "16", titulo ="Edición No. 16", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-1624.pdf", imagen = "16.jpg"),
       PeriodicoVida(id = "17", titulo ="Edición No. 17", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-1724.pdf", imagen = "17.jpg"),
       PeriodicoVida(id = "18", titulo ="Edición No. 18", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-1824.pdf", imagen = "18.jpg"),
       PeriodicoVida(id = "19", titulo ="Edición No. 19", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-1924.pdf", imagen = "19.jpg"),
       PeriodicoVida(id = "20", titulo ="Edición No. 20", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-2024.pdf", imagen = "20.jpg"),
       PeriodicoVida(id = "21", titulo ="Edición No. 21", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-2124.pdf.pdf", imagen = "21.jpg"),
       PeriodicoVida(id = "22", titulo ="Edición No. 22", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-2224.pdf", imagen = "22.jpg"),
       PeriodicoVida(id = "23", titulo ="Edición No. 23", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-2324.pdf", imagen = "23.jpg"),
       PeriodicoVida(id = "24", titulo ="Edición No. 24", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA-2424.pdf", imagen = "24.jpg"),
       PeriodicoVida(id = "25", titulo ="Edición No. 25", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA2524.pdf", imagen = "25.jpg"),
       PeriodicoVida(id = "26", titulo ="Edición No. 26", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA2624.pdf", imagen = "26.jpg"),
       PeriodicoVida(id = "27", titulo ="Edición No. 27", urlPDF = "https://www.presidencia.gov.co/prensa/HistorialVida/VIDA2724.pdf", imagen = "27.jpg"),*//*

)*/
