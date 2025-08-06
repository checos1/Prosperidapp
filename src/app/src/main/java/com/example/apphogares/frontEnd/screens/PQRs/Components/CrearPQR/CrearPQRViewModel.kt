package com.example.apphogares.frontEnd.screens.PQRs.Components.CrearPQR

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphogares.AppHogaresAplication
import com.example.apphogares.backEnd.Services.LogError
import com.example.apphogares.backEnd.Services.servicesAPI.PQRApi
import com.example.apphogares.backEnd.Services.servicesAPI.PQRDocumentAPI
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument
import com.example.apphogares.backEnd.core.models.PQR.PQRs
import com.example.apphogares.frontEnd.RoutesNav
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class CrearPQREstadoUI {
    object MostrarFormulario : CrearPQREstadoUI()
    object MostrandoCamara : CrearPQREstadoUI()
    object MostrandoFilePicker : CrearPQREstadoUI()
}

@HiltViewModel
class CrearPQRViewModel  @Inject constructor(private val apiPQR: PQRDocumentAPI, private val logError: LogError): ViewModel(){

    val estadoPantalla = mutableStateOf<CrearPQREstadoUI>(CrearPQREstadoUI.MostrarFormulario)
    val showCamara: MutableState<Boolean> = mutableStateOf(false)
    val showFilePick: MutableState<Boolean> = mutableStateOf(false)
    val showThumbnailPhoto: MutableState<Boolean> = mutableStateOf(false)
    val showTypePQRs: MutableState<Boolean> = mutableStateOf(false)
    val tipoPQR: MutableState<String> = mutableStateOf("")
    val tipoPeticion = mutableStateOf("")
    val cuerpoPeticion = mutableStateOf("")
    val selectedPQR: MutableState<PQRDocument> = mutableStateOf(PQRDocument())

    var isCreating = mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            try {
                //selectedPQR.value.blqInformacionPeticion.fotoPeticion = selectedPQR.value.blqPersonaNatural.identificacion + ".jpg"
                println("PQRiewModel selectedPQR ->> ${selectedPQR.value}")
                //selectedPQR.value.blqInformacionPeticion.asunto = "Petición"
                //selectedPQR.value.blqInformacionPeticion.idAsunto = 4
                tipoPeticion.value = selectedPQR.value.blqInformacionPeticion.asunto
                val integrante = AppHogaresAplication.Infohogar.hogar!!.integrantes!!.filterIndexed { index, integrante ->
                    integrante.idIntegranteHogar == AppHogaresAplication.integranteSeleccionado
                }[0]
                val hogar = AppHogaresAplication.Infohogar.hogar!!
                selectedPQR.value.idHogar = hogar.idHogar
                selectedPQR.value.blqPersonaNatural.identificacion = integrante.numeroDocumento
                selectedPQR.value.blqPersonaNatural.idTipoIdentificacion = integrante.tipoDocumento
                selectedPQR.value.blqPersonaNatural.tipoIdentificacion = integrante.tipoDocumento
                selectedPQR.value.blqPersonaNatural.primerNombre = integrante.nombre.split(" ")[0]
                selectedPQR.value.blqPersonaNatural.segundoNombre = if (integrante.nombre.split(" ").size > 3) {
                    integrante.nombre.split(" ")[1]
                } else {
                    ""
                }
                selectedPQR.value.blqPersonaNatural.primerApellido = integrante.nombre.split(" ")[integrante.nombre.split(" ").size - 1]
                selectedPQR.value.blqPersonaNatural.segundoApellido = if (integrante.nombre.split(" ").size > 2) {
                    integrante.nombre.split(" ")[integrante.nombre.split(" ").size - 2]
                } else {
                    ""
                }
                selectedPQR.value.blqPersonaNatural.idGenero = 1
                selectedPQR.value.blqPersonaNatural.genero = integrante.sexo
                selectedPQR.value.blqPersonaNatural.fechaNacimiento = integrante.fechaNacimiento
                selectedPQR.value.blqDatosContactoSolicitante.departamento = hogar.contacto!!.departamento
                selectedPQR.value.blqDatosContactoSolicitante.idDepartamento = hogar.contacto!!.codigo_municipio.substring(0, 2).toInt()
                selectedPQR.value.blqDatosContactoSolicitante.ciudad = hogar.contacto!!.municipio
                selectedPQR.value.blqDatosContactoSolicitante.idCiudad = hogar.contacto!!.codigo_municipio.toInt()
                selectedPQR.value.blqDatosContactoSolicitante.direccion = hogar.contacto!!.ubicacionHogar
                selectedPQR.value.blqDatosContactoSolicitante.telefono = if(hogar.contacto!!.numerosContacto.size > 0)  hogar.contacto!!.numerosContacto[0] else ""
                selectedPQR.value.blqDatosContactoSolicitante.correoElectronico = if(isEmailValid(hogar.contacto!!.correoElectronico))  hogar.contacto!!.correoElectronico else ""
                //selectedPQR.value.anexos = null
                //selectedPQR.value = PQRs.items[0]
            } catch (e: Exception) {
                println("CrearPQRViewModel init error ->> ${e.message}")
                logError.RegistrarError(e.message.orEmpty(), "CrearPQRViewModel", "init")
            }
        }
    }

    fun updateTipoPeticion(newTipoPeticion: String) {
        tipoPeticion.value = newTipoPeticion
        selectedPQR.value.blqInformacionPeticion.asunto = newTipoPeticion
        selectedPQR.value.blqInformacionPeticion.idAsunto = PQRs.tipos.filterIndexed( { index, tipo ->
            tipo.tipo == newTipoPeticion
        })[0].idTipo
    }

    fun updateCuerpoPeticion(newCuerpoPeticion: String) {
        println("PQRiewModel updateCuerpoPeticion ->> ${newCuerpoPeticion}")
        cuerpoPeticion.value = newCuerpoPeticion
        selectedPQR.value.blqInformacionPeticion.descripcion = newCuerpoPeticion
    }

    fun AddPQR(onSuccess: () -> Unit) {
        println("PQRiewModel AddPQR ->> ${Gson().toJson(selectedPQR.value)}")
        viewModelScope.launch {
            isCreating.value = true
            try {
                withContext(Dispatchers.IO) {
                    apiPQR.CrearPQR(selectedPQR.value)
                }
                onSuccess()
            } catch (e: Exception) {
                println("PQRiewModel AddPQR Error ->> ${e.message}")
                logError.RegistrarError(e.message.orEmpty(), "PQRViewModel", "AddPQR")
            } finally {
                isCreating.value = false
            }
        }
    }

    fun updateArchivoPeticion(fileName: String) {
        selectedPQR.value = selectedPQR.value.copy().apply {
            blqInformacionPeticion = blqInformacionPeticion.copy(
                archivoPeticion = fileName
            )
        }
    }

    fun mostrarCamara() {
        estadoPantalla.value = CrearPQREstadoUI.MostrandoCamara
    }

    fun mostrarFilePicker() {
        estadoPantalla.value = CrearPQREstadoUI.MostrandoFilePicker
    }

    fun mostrarFormulario() {
        estadoPantalla.value = CrearPQREstadoUI.MostrarFormulario
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}