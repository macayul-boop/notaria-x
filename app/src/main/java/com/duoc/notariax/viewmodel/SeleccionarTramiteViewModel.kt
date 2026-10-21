package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import com.duoc.notariax.repository.TramiteRepository
import kotlinx.coroutines.flow.MutableStateFlow

class SeleccionarTramiteViewModel() : ViewModel() {

    private val repository = TramiteRepository()

    var listaTramites = repository.obtenerTodosTramites();
    val tramiteSeleccionado = MutableStateFlow(null)

}