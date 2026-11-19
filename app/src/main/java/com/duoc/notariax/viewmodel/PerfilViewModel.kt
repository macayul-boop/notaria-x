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
    private val sessionManager: SessionManager
): ViewModel() {

    private val repository = UsuarioRepository();

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
        val idUsuario = sessionManager.obtenerIdSesion()

        viewModelScope.launch {
                if (idUsuario != null) {
                    try{
                        val resultado = repository.obtenerUsuario(idUsuario.toInt())
                        _nombreUsuario.value = resultado?.nombre ?: "Sin nombre"
                        _rut.value = resultado?.rut ?: "Sin RUT"
                        _email.value = resultado?.email ?: "Sin correo"
                        _contraseña.value = resultado?.contrasena ?: "••••••••"
                    }catch(e: Exception){
                        _nombreUsuario.value =  "Error al cargar"
                        _rut.value = "-"
                        _email.value = "-"
                        _contraseña.value = "-"
                        e.printStackTrace()
                    }
                }

        }
    }

}