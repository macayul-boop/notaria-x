package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.model.Tramite
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.repository.ProgresoTramiteRepository
import com.duoc.notariax.repository.TramitesUsuariosRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TramitesViewModel(
    sesionManager: SessionManager
): ViewModel() {

    private val repositoryTramitesUsuarios = TramitesUsuariosRepository()
    val idUsuario: String? = sesionManager.obtenerIdSesion();

    private val _listaTramites = MutableStateFlow<List<TramitesUsuariosDto>>(emptyList())
    val listaTramites: StateFlow<List<TramitesUsuariosDto>> = _listaTramites.asStateFlow()

    init {
        cargarTramites()
    }

    fun cargarTramites(){
        viewModelScope.launch {
           if(idUsuario != null){
               try{
                   _listaTramites.value = repositoryTramitesUsuarios.obtenerTramiteUsuarioPorIdUsuario(idUsuario.toInt())
               }catch (e: Exception){
                   print(e)
               }
           }
        }
    }

}