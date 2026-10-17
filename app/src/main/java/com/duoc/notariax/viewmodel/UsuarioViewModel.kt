package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel (private val repository: UsuarioRepository) : ViewModel() {
    // Expresiones regualres
    val rutRegex = Regex("""^\d{1,2}\.\d{3}\.\d{3}-[\dkK]$""")
    val emailRegex = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""")

    // Variables
    var nombreCompleto = MutableStateFlow("")
    var rut = MutableStateFlow("")
    var email = MutableStateFlow("")
    var contrasena = MutableStateFlow("")
    var contrasenaRepetida = MutableStateFlow("")

    // Mensajes de error
    var errorNombre = MutableStateFlow("")
    var errorRut = MutableStateFlow("")
    var errorEmail = MutableStateFlow("")
    var errorContrasena = MutableStateFlow("")
    var errorContrasenaRepetida = MutableStateFlow("")

    fun limpiarInputs(){
        nombreCompleto.value = ""
        rut.value = ""
        email.value = ""
        contrasena.value = ""
        contrasenaRepetida.value = ""
    }

    fun limpiarErrores(){
        errorNombre.value = ""
        errorRut.value = ""
        errorEmail.value = ""
        errorContrasena.value = ""
        errorContrasenaRepetida.value = ""
    }

    fun crearUsuario(usuario: Usuario){

        var valido: Boolean = true;

        limpiarErrores()

        if(nombreCompleto.value.isBlank()){
            errorNombre.value = "El nombre es obligatorio"
            valido = false
        }

        if(rut.value.isBlank()){
            errorRut.value = "El rut es obligatorio"
            valido = false
        }else if(rutRegex.matches(rut.value)){
            errorRut.value = "Formato invalido"
            valido = false
        }

        if(email.value.isBlank()){
            errorEmail.value = "El email es obligatorio"
            valido = false
        }else if(emailRegex.matches(email.value)){
            errorEmail.value = "Formato invalido"
            valido = false
        }

        if(contrasena.value.isBlank()){
            errorContrasena.value = "La contraseña es obligatorio"
            valido = false
        }

        if(contrasenaRepetida.value.isBlank()){
            errorContrasenaRepetida.value = "La contraseña repetida es obligatorio"
            valido = false
        }else if(!contrasena.value.equals(contrasenaRepetida.value)){
            errorContrasenaRepetida.value = "La contraseña repetida es incorrecta"
            valido = false
        }

        if(valido){

            limpiarInputs()
            limpiarErrores()

            viewModelScope.launch {
                repository.insert(usuario)
            }
        }

    }
}