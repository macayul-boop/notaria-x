package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.Usuario
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
    private val _nombreUsuario = MutableStateFlow<String?>("Desconocido/a")
    val nombreUsuario: StateFlow<String?> = _nombreUsuario.asStateFlow()

    private val idUsuario: String? = sessionManager.obtenerIdSesion();

    init {
        obtenerUsuario()
    }

    private fun obtenerUsuario(){
        viewModelScope.launch {
            if(idUsuario != null) {
                val resultado = repository.getUsuario(idUsuario.toInt())
                _nombreUsuario.value = resultado?.nombreCompleto ?: "Desconocido/a"
            }
        }
    }


}