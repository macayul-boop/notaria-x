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


    // Variables
    var nombreCompleto = MutableStateFlow("")
    var rut = MutableStateFlow("")
    var email = MutableStateFlow("")
    var contrasena = MutableStateFlow("")
    var contrasenaRepetida = MutableStateFlow("")
    var registroExito = MutableStateFlow(false)

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

    private fun validarNombre(): Boolean{
        if (repository.validarNombre(nombreCompleto.value)){
            errorNombre.value = "El nombre es obligatorio"
            return false
        }else{
            errorNombre.value = ""
            return true
        }
    }

    private fun validarRut(): Boolean {
        /*return when {
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
        }*/

        if(repository.validarVacioRut(rut.value)){
            errorRut.value = "El RUT es obligatorio"
            return false
        }else if(repository.validarFormatoRut(rut.value)){
            errorRut.value = "Formato inválido (ej: 12.345.678-k)"
            return false
        }else{
            errorRut.value = ""
            return true
        }
    }

    private fun validarEmail(): Boolean {
        /*return when {
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
        }*/

        if(repository.validarVacioEmail(email.value)){
            errorEmail.value = "El email es obligatorio"
            return false
        }else if(repository.validarFormatoEmail(email.value)){
            errorEmail.value = "Formato de email inválido"
            return false
        }else{
            errorEmail.value = ""
            return true
        }
    }

    private fun validarContrasena(): Boolean {
        /*return if (contrasena.value.isBlank()) {
            errorContrasena.value = "La contraseña es obligatoria"
            false
        } else {
            errorContrasena.value = ""
            true
        }*/

        if(repository.validarVacioContrasena(contrasena.value)){
            errorContrasena.value = "La contraseña es obligatoria"
            return false
        }else{
            errorContrasena.value = ""
            return true
        }
    }

    private fun validarContrasenaRepetida(): Boolean {
        /*return when {
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
        }*/

        if(repository.validarVacionContrasenaRepetida(contrasenaRepetida.value)){
            errorContrasenaRepetida.value = "Repetir la contraseña es obligatorio"
            return false
        }else if(repository.validarCoincidenciaContrasena(contrasena.value, contrasenaRepetida.value)){
            errorContrasenaRepetida.value = "Las contraseñas no coinciden"
            return false
        }else{
            errorContrasenaRepetida.value = ""
            return true
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
            registroExito.value = true
            viewModelScope.launch {
                repository.insert(usuario)
                limpiarInputs()
                limpiarErrores()
            }
        }

    }
}