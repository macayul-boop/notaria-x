package com.duoc.notariax.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.duoc.notariax.model.Tramite
import com.duoc.notariax.repository.TramiteRepository
import kotlinx.coroutines.flow.StateFlow

class DetalleTramiteViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val repository = TramiteRepository()
    val tramiteId: String = checkNotNull(savedStateHandle["tramiteId"])
    val tramite: Tramite? = repository.obtenerTramitePorId(tramiteId.toInt())




}