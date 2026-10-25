package com.duoc.notariax.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.repository.ProgresoTramiteRepository

class ProgresoTramiteViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val repository = ProgresoTramiteRepository()

    val progresoId: String = checkNotNull(savedStateHandle["progresoTramiteId"])
    val pogresoConTramite: ProgresoConTramite? = repository.obtenerProgresoConTramite(progresoId.toInt())



}