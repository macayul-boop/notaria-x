package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.model.ProgresoTramite
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.repository.ProgresoTramiteRepository
import com.duoc.notariax.repository.TramitesUsuariosRepository
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InicioViewModel(
    private val sessionManager: SessionManager
): ViewModel() {

    private val repositoryUsuario = UsuarioRepository()
    private val repositoryTramite = TramitesUsuariosRepository()

    private val idUsuario: String? get() = sessionManager.obtenerIdSesion();
    private val _nombreUsuario = MutableStateFlow<String?>("Desconocido/a")
    val nombreUsuario: StateFlow<String?> = _nombreUsuario.asStateFlow()

    private val _listaTramitesProgreso = MutableStateFlow<List<TramitesUsuariosDto>>(emptyList())
    val listaTramitesProgreso: StateFlow<List<TramitesUsuariosDto>> = _listaTramitesProgreso.asStateFlow()


    init {
        obtenerUsuario()
        obtenerTramites()
    }

    fun obtenerUsuario(){
        viewModelScope.launch {
            if(idUsuario != null) {
                val idActual = idUsuario
                if (idActual != null) {
                    try {
                        val resultado = repositoryUsuario.obtenerUsuario(idActual.toInt())
                        _nombreUsuario.value = resultado?.nombre ?: "Desconocido/a"
                    }catch (e: Exception){
                        print(e)
                    }
                }
            }
        }
    }

    private fun obtenerTramites(){
        viewModelScope.launch {
            if(idUsuario != null){
                val idActual = idUsuario
                if (idActual != null) {
                    try {
                        val resultado = repositoryTramite.obtenerTramiteUsuarioPorIdUsuario(idActual.toInt())
                        _listaTramitesProgreso.value = resultado.takeLast(2)
                    }catch (e: Exception){
                        print(e)
                    }
                }
            }
        }
    }


}