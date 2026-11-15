package com.duoc.notariax.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.EstadoTramitesUsuarios
import com.duoc.notariax.model.Tramite
import com.duoc.notariax.model.TramiteDto
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.repository.ArchivosRepository
import com.duoc.notariax.repository.TramiteRepository
import com.duoc.notariax.repository.TramitesUsuariosRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleTramiteViewModel(
    savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager
): ViewModel() {

    private val repositoryTramite = TramiteRepository()
    private val repositoryArchivo = ArchivosRepository()
    private val repositoryTramiteUsuario = TramitesUsuariosRepository()
    val tramiteId: String = checkNotNull(savedStateHandle["tramiteId"])
    val idUsuario: String? = sessionManager.obtenerIdSesion();
    private val _tramite = MutableStateFlow<TramiteDto?>(null)
    val tramite: StateFlow<TramiteDto?> = _tramite.asStateFlow()
    private val _archivos = MutableStateFlow<List<Uri>>(emptyList())
    val archivos: StateFlow<List<Uri>> = _archivos.asStateFlow()

    private val _esValido = MutableStateFlow(false)
    val esvalido: StateFlow<Boolean> = _esValido.asStateFlow()

    private val _errorArchivo = MutableStateFlow("")
    val errorArchivo: StateFlow<String> = _errorArchivo.asStateFlow()

    init {
        obtenerArchivos()
        cargarTramite()
    }
    fun cargarTramite(){
        viewModelScope.launch {
            try {
                _tramite.value = repositoryTramite.obtenerTramite(tramiteId.toInt())
            }catch (e: Exception){
                print(e)
            }
        }
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
        if(idUsuario != null){
            if(_archivos.value.size >= 1){
                _errorArchivo.value = ""
                _esValido.value = true;

                viewModelScope.launch {
                    try {
                        repositoryTramiteUsuario.crearTramiteUsuario(
                            TramitesUsuariosDto(
                                cliente_id = idUsuario.toInt(),
                                tramite_id = tramiteId.toInt(),
                                estado = EstadoTramitesUsuarios.abierto
                            )
                        )
                    }catch (e: Exception){
                        print(e)
                    }
                }

            }else{
                _esValido.value = false;
                _errorArchivo.value = "Sube al menos 1 archivo"
            }
        }
    }


}