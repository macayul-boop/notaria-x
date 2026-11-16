package com.duoc.notariax.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.model.TramiteDto
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.repository.ProgresoTramiteRepository
import com.duoc.notariax.repository.TramitesUsuariosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProgresoTramiteViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val repository = TramitesUsuariosRepository()
    val progresoId: String = checkNotNull(savedStateHandle["progresoTramiteId"])

    private val _tramiteUsuario = MutableStateFlow<TramitesUsuariosDto?>(null)
    val tramiteUsuario = _tramiteUsuario.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos(){
        viewModelScope.launch {
            try {
                _tramiteUsuario.value = repository.obtenerTramiteUsuarioPorId(progresoId.toInt())
            }catch (e: Exception){
                print(e)
            }
        }
    }

}