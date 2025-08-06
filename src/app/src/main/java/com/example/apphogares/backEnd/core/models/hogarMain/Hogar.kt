package com.example.apphogares.backEnd.core.models.hogarMain

import com.example.apphogares.backEnd.core.models.Comunicaciones.Publicacion
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.backEnd.core.models.actividadesRuta.TematicaGamificacion
import com.example.apphogares.backEnd.core.models.contenidosRuta.ContenidosRuta
import com.example.apphogares.backEnd.core.models.navegacion.Navegacion
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class Hogar(
    @SerializedName("idHogar") @Expose var idHogar: String = "",
    @SerializedName("idIntegranteSeleccionado") @Expose var idIntegranteSeleccionado: String = "",
    @SerializedName("jefeHogar") @Expose var jefeHogar: String = "",
    @SerializedName("idPerfilHogar") @Expose var idPerfilHogar: String = "",
    @SerializedName("topicos") @Expose var topicos: String = "",
    @SerializedName("ofertas") @Expose var ofertas: String = "",

    @SerializedName("fechaCorte") @Expose var fechaCorte: String = "",
    @SerializedName("clasificacion") @Expose var clasificacion: String = "",
    @SerializedName("fuente") @Expose var fuente: String = "",

    @SerializedName("fechaActualizacion") @Expose var fechaActualizacion: String = LocalDate.now().toString(),

    @SerializedName("contacto") @Expose var contacto: Contacto? = Contacto(),
    @SerializedName("integrantes") @Expose var integrantes: List<Integrante>? = null,
    @SerializedName("listaOferta") @Expose var listaOferta: List<Oferta>? = null,
    @SerializedName("dispositivo") @Expose var dispositivo: Dispositivo? = Dispositivo(),
    @SerializedName("navegacion") @Expose var navegacion: Navegacion? = null,
    @SerializedName("notificaciones") @Expose var notificaciones: MutableList<Notificacion> = mutableListOf(),
    @SerializedName("encuestas") @Expose var encuestas: MutableList<Encuesta> = mutableListOf(),
    @SerializedName("pqrs") @Expose var pqrs: MutableList<PQRDocument> = mutableListOf(),
    @SerializedName("publicaciones") @Expose var publicaciones: MutableList<Publicacion> = mutableListOf()
)
