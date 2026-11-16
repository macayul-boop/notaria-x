package com.duoc.notariax.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.CambiarEstadoRequest
import com.duoc.notariax.model.EstadoTramitesUsuarios
import com.duoc.notariax.model.HistorialTramiteRequest
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.repository.HistorialTramiteRepository
import com.duoc.notariax.repository.TramitesUsuariosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProgresoTramiteNotarioViewlModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val repositoryTramiteUsuario = TramitesUsuariosRepository()
    private val repositoryHistorial = HistorialTramiteRepository()
    private val tramiteUsuarioId: String = checkNotNull(savedStateHandle["progresoTramiteId"])

    private val _tramiteUsuario = MutableStateFlow<TramitesUsuariosDto?>(null)
    val tramiteUsuario = _tramiteUsuario.asStateFlow()

    // VARIABLES DEL FORMULARIO
    private var _titulo = MutableStateFlow("")
    private var _descripcion = MutableStateFlow("")

    private var _errorTitulo = MutableStateFlow("")
    private var _errorDescripcion = MutableStateFlow("")

    var errorTitulo = _errorTitulo.asStateFlow()
    var errorDescripcion = _errorDescripcion.asStateFlow()

    var titulo = _titulo.asStateFlow()
    var descripcion = _descripcion.asStateFlow()

    // VARIABLES DEL INPUT DESPLEGABLE
    val opcionesDropdown = listOf("informativo", "advertencia", "finalizado", "rechazado")
    private val _opcionSeleccionada = MutableStateFlow(opcionesDropdown[0])

    val opcionSeleccionada = _opcionSeleccionada.asStateFlow()

    fun seleccionarOpcion(nuevaOpcion: String) {
        _opcionSeleccionada.value = nuevaOpcion
    }


    init {
        cargarDatosIniciales()
    }

    fun cargarDatosIniciales(){
        if(tramiteUsuarioId != null){
            try {
                viewModelScope.launch {
                    _tramiteUsuario.value = repositoryTramiteUsuario.obtenerTramiteUsuarioPorId(tramiteUsuarioId.toInt())
                }
            }catch (e: Exception){
                print(e)
            }
        }
    }

    fun limpiarTituloError(){
        _errorTitulo.value = ""
    }

    fun limpiarDescripcionError(){
        _errorDescripcion.value = ""
    }


    fun modificarTitulo(inputTitulo: String){
        _titulo.value = inputTitulo
    }

    fun modificarDescripcion(inputDescripcion: String){
        _descripcion.value = inputDescripcion
    }

    fun validarTituloVacio(): Boolean{
        if(repositoryTramiteUsuario.validarTituloVacio(_titulo.value)){
            _errorTitulo.value = "El titulo es obligatorio"
            return false
        }else{
            _errorTitulo.value = ""
            return true

        }
    }

    fun validarDescripcionVacio(): Boolean{
        if(repositoryTramiteUsuario.validarDescripcionVacio(_descripcion.value)){
            _errorDescripcion.value = "La descripcion es obligatorio"
            return false
        }else{
            _errorDescripcion.value = ""
            return true
        }
    }

    fun cambiarEstado(){
        if(_tramiteUsuario.value?.estado?.equals(EstadoTramitesUsuarios.abierto) ?: false){
            try {
                viewModelScope.launch {
                    repositoryTramiteUsuario.cambiarEstado(
                        tramiteUsuarioId.toInt(),
                        CambiarEstadoRequest(EstadoTramitesUsuarios.finalizado)
                    )
                    cargarDatosIniciales()
                }
            }catch (e: Exception){
                print(e)
            }
        }
    }

    fun crearEvento(){

        val tituloValido = validarTituloVacio()
        val descripcionValida = validarDescripcionVacio()

        val esValido = tituloValido && descripcionValida

        if(esValido){
            try {
                viewModelScope.launch {
                    repositoryHistorial.crearHistorialTramite(
                        HistorialTramiteRequest(
                            tramite_usuario_id = tramiteUsuarioId.toInt(),
                            titulo = _titulo.value,
                            descripcion = _descripcion.value,
                            tipo = _opcionSeleccionada.value
                        )
                    )
                    cargarDatosIniciales()
                    _titulo.value = ""
                    _descripcion.value = ""
                    _opcionSeleccionada.value = opcionesDropdown[0]
                }
            }catch (e: Exception){
                print(e)
            }
        }

    }

}