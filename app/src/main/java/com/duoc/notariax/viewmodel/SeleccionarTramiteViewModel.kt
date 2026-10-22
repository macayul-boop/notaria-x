package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import com.duoc.notariax.model.Tramite
import com.duoc.notariax.repository.TramiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SeleccionarTramiteViewModel() : ViewModel() {

    private val repository = TramiteRepository()
    private val _listaTramites = MutableStateFlow<List<Tramite>>(repository.obtenerTodosTramites())
    val listaTramites: StateFlow<List<Tramite>> = _listaTramites.asStateFlow()

    private val _tramiteSeleccionado = MutableStateFlow<Int?>(null)
    val tramiteSeleccionado: StateFlow<Int?> = _tramiteSeleccionado.asStateFlow()

    private val _esValido = MutableStateFlow("")
    val esValido: StateFlow<String> = _esValido.asStateFlow()

    fun seleccionarTramite(idTramite: Int): Unit{
        if (_tramiteSeleccionado.value == idTramite) {
            _tramiteSeleccionado.value = null
        }else{
            _tramiteSeleccionado.value = idTramite
        }
    }

    fun validar(): Unit{
        if(_tramiteSeleccionado.value != null){
            _esValido.value = "";
        }else{
            _esValido.value = "Selecciona un tramite";
        }
    }

}