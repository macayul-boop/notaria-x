package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.model.ProgresoTramite
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.repository.ProgresoTramiteRepository
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InicioViewModel(
    private val repository: UsuarioRepository,
    private val sessionManager: SessionManager
): ViewModel() {

    private val repositoryProgresoTramite = ProgresoTramiteRepository()

    private val idUsuario: String? = sessionManager.obtenerIdSesion();
    private val _nombreUsuario = MutableStateFlow<String?>("Desconocido/a")
    val nombreUsuario: StateFlow<String?> = _nombreUsuario.asStateFlow()

    private val _listaTramitesProgreso = MutableStateFlow<List<ProgresoConTramite>>(emptyList())
    val listaTramitesProgreso: StateFlow<List<ProgresoConTramite>> = _listaTramitesProgreso.asStateFlow()


    init {
        obtenerUsuario()
        obtenerTramites()
    }

    private fun obtenerUsuario(){
        viewModelScope.launch {
            if(idUsuario != null) {
                val resultado = repository.getUsuario(idUsuario.toInt())
                _nombreUsuario.value = resultado?.nombreCompleto ?: "Desconocido/a"
            }
        }
    }

    private fun obtenerTramites(){
        _listaTramitesProgreso.value = repositoryProgresoTramite.obtenerVariosProgesoConTramite(2)
    }


}