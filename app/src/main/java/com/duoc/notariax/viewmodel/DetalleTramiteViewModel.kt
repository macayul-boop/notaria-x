package com.duoc.notariax.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.duoc.notariax.model.Tramite
import com.duoc.notariax.repository.ArchivosRepository
import com.duoc.notariax.repository.TramiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetalleTramiteViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val repositoryTramite = TramiteRepository()
    private val repositoryArchivo = ArchivosRepository()
    val tramiteId: String = checkNotNull(savedStateHandle["tramiteId"])
    val tramite: Tramite? = repositoryTramite.obtenerTramitePorId(tramiteId.toInt())

    private val _archivos = MutableStateFlow<List<Uri>>(emptyList())
    val archivos: StateFlow<List<Uri>> = _archivos.asStateFlow()

    private val _esValido = MutableStateFlow(false)
    val esvalido: StateFlow<Boolean> = _esValido.asStateFlow()

    private val _errorArchivo = MutableStateFlow("")
    val errorArchivo: StateFlow<String> = _errorArchivo.asStateFlow()

    init {
        obtenerArchivos()
    }

    fun cambiarEsValido(){
        _esValido.value = false
    }
    fun obtenerArchivos(){
        _archivos.value = repositoryArchivo.obtenerArchivos()
    }
    fun agregarArchivos(uris: List<Uri>){
        repositoryArchivo.agregarArchivos(uris)
        obtenerArchivos()
    }

    fun eliminarArchivo(uri: Uri){
        repositoryArchivo.eliminarArchivo(uri)
        obtenerArchivos()
    }

    fun validar(){
        if(_archivos.value.size >= 1){
            _errorArchivo.value = ""
            _esValido.value = true;
        }else{
            _esValido.value = false;
            _errorArchivo.value = "Sube al menos 1 archivo"
        }
    }


}