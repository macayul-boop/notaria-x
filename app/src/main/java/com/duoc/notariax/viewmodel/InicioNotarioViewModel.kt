package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InicioNotarioViewModel(
    sessionManager: SessionManager
) : ViewModel() {
    private val repository: UsuarioRepository = UsuarioRepository()
    private val idUsuario: String? = sessionManager.obtenerIdSesion();

    private val _nombreUsuario = MutableStateFlow<String?>("Desconocido/a")
    val nombreUsuario: StateFlow<String?> = _nombreUsuario.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos(){
        if(idUsuario != null){
            try {
                viewModelScope.launch {
                    _nombreUsuario.value = repository.obtenerUsuario(idUsuario.toInt()).nombre
                }
            }catch (e: Exception){
                print(e)
            }
        }
    }

}