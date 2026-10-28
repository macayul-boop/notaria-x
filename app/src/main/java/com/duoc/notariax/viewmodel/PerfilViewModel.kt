package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repository: UsuarioRepository,
    private val sessionManager: SessionManager
): ViewModel() {
     private val idUsuario: String? = sessionManager.obtenerIdSesion();
    private val _nombreUsuario = MutableStateFlow<String?>("Cargando...")
    private val _rut = MutableStateFlow<String?>("Cargando...")
    private val _email = MutableStateFlow<String?>("Cargando...")
    private val _contraseña = MutableStateFlow<String?>("Cargando...")

    val nombreUsuario: StateFlow<String?> = _nombreUsuario.asStateFlow()
    var rut: StateFlow<String?> = _rut.asStateFlow()

    var email: StateFlow<String?> = _email.asStateFlow()

    var contrasena: StateFlow<String?> = _contraseña.asStateFlow()


    init {
        obtenerUsuario()
    }

    fun obtenerUsuario(){
        viewModelScope.launch {
            if(idUsuario != null) {
                val resultado = repository.getUsuario(idUsuario.toInt())
                _nombreUsuario.value = resultado?.nombreCompleto ?: "Sin nombre"
                _rut.value = resultado?.rut ?: "Sin RUT"
                _email.value = resultado?.email ?: "Sin correo"
                _contraseña.value = resultado?.contrasena ?: "••••••••"
            }
        }
    }

}