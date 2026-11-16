package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.repository.TramitesUsuariosRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TramitesNotarioViewModel(
    sessionManager: SessionManager
): ViewModel() {

    private val repository: TramitesUsuariosRepository = TramitesUsuariosRepository()
    private val idNotario = sessionManager.obtenerIdSesion();

    private val _listaTramites = MutableStateFlow<List<TramitesUsuariosDto>>(emptyList())
    val listaTramite = _listaTramites.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos(){
        if(idNotario != null){
            try {
                viewModelScope.launch {
                    _listaTramites.value = repository.obtenerTramiteUsuarioPorIdUsuario(idNotario.toInt())
                }
            }catch (e: Exception){
                print(e)
            }
        }
    }


}