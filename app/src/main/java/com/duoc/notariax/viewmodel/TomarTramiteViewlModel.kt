package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.TomarTramiteRequest
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.repository.TramitesUsuariosRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TomarTramiteViewlModel(
    sessionManager: SessionManager
): ViewModel() {

    private val repository = TramitesUsuariosRepository()
    private val idNotario = sessionManager.obtenerIdSesion();

    private val _listaTramites = MutableStateFlow<List<TramitesUsuariosDto?>>(emptyList())
    val listaTramites = _listaTramites.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos(){
        try {
            viewModelScope.launch {
                _listaTramites.value = repository.obtenerTramitesSinNotario();
            }
        }catch (e: Exception){
            print(e)
        }
    }

    fun seleccionarTramite(idTramite: Int){
        if(idNotario != null){
            try {
                viewModelScope.launch {
                    repository.tomarTramite(idTramite, TomarTramiteRequest(idNotario.toInt()))
                    cargarDatos()
                }
            }catch (e: Exception){
                print(e)
            }
        }
    }

}