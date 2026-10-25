package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.model.Tramite
import com.duoc.notariax.repository.ProgresoTramiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TramitesViewModel(): ViewModel() {

    private val repositoryTramites = ProgresoTramiteRepository()

    private val _listaTramites = MutableStateFlow<List<ProgresoConTramite>>(repositoryTramites.obtenerVariosProgesoConTramite())
    val listaTramites: StateFlow<List<ProgresoConTramite>> = _listaTramites.asStateFlow()



}