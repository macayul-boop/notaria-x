package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun limpiarErrores() {
        errorNombre.value = ""
        errorRut.value = ""
        errorEmail.value = ""
        errorContrasena.value = ""
        errorContrasenaRepetida.value = ""
    }

    fun limpiarErrorNombre() { errorNombre.value = "" }
    fun limpiarErrorRut() { errorRut.value = "" }
    fun limpiarErrorEmail() { errorEmail.value = "" }
    fun limpiarErrorContrasena() { errorContrasena.value = "" }
    fun limpiarErrorContrasenaRepetida() { errorContrasenaRepetida.value = "" }

    private fun validarNombre(): Boolean {
        return if (nombreCompleto.value.isBlank()) {
            errorNombre.value = "El nombre es obligatorio"
            false
        } else {
            errorNombre.value = ""
            true
        }
    }

    private fun validarRut(): Boolean {
        return when {
            rut.value.isBlank() -> {
                errorRut.value = "El RUT es obligatorio"
                false
            }
            !rutRegex.matches(rut.value) -> {
                errorRut.value = "Formato inválido (ej: 12.345.678-k)"
                false
            }
            else -> {
                errorRut.value = ""
                true
            }
        }
    }

    private fun validarEmail(): Boolean {
        return when {
            email.value.isBlank() -> {
                errorEmail.value = "El email es obligatorio"
                false
            }
            !emailRegex.matches(email.value) -> {
                errorEmail.value = "Formato de email inválido"
                false
            }
            else -> {
                errorEmail.value = ""
                true
            }
        }
    }

    private fun validarContrasena(): Boolean {
        return if (contrasena.value.isBlank()) {
            errorContrasena.value = "La contraseña es obligatoria"
            false
        } else {
            errorContrasena.value = ""
            true
        }
    }

    private fun validarContrasenaRepetida(): Boolean {
        return when {
            contrasenaRepetida.value.isBlank() -> {
                errorContrasenaRepetida.value = "Repetir la contraseña es obligatorio"
                false
            }
            contrasena.value != contrasenaRepetida.value -> {
                errorContrasenaRepetida.value = "Las contraseñas no coinciden"
                false
            }
            else -> {
                errorContrasenaRepetida.value = ""
                true
            }
        }
    }

    fun crearUsuario(usuario: Usuario){

        val esValidoNombre = validarNombre()
        val esValidoRut = validarRut()
        val esValidoEmail = validarEmail()
        val esValidoContrasena = validarContrasena()
        val esValidoContrasenaRepetida = validarContrasenaRepetida()

        val esFormularioValido = esValidoNombre && esValidoRut && esValidoEmail &&
                esValidoContrasena && esValidoContrasenaRepetida

        if (esFormularioValido) {
            viewModelScope.launch {
                repository.insert(usuario)
                limpiarInputs()
                limpiarErrores()
            }
        }

    }
}