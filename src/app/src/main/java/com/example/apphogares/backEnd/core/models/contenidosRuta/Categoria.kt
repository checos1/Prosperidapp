package com.example.apphogares.backEnd.core.models.contenidosRuta

data class Categoria(
    val codigo: String = "",
    val ordenar: Int = 0,
    val descripcion: String = "",
    val id: String = "",
    val nombre: String = "",
    val rutas: List<Ruta> = emptyList(),
    var estadoCategoria: String = "",
    val imagenActiva: String = "https://sapsdafchogaresprod.blob.core.windows.net/cntpsdafchogaresprod/assets/Renta_Joven_b97db2d2d1.jpg",
    val imagenInactiva: String = "https://sapsdafchogaresprod.blob.core.windows.net/cntpsdafchogaresprod/assets/medium_DANILO_10_cb3ddeadf3.png"
)