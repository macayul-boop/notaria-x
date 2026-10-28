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

class EditarPerfilViewModel (
    private val repository: UsuarioRepository,
    private val sessionManager: SessionManager
) : ViewModel(){

    private val idUsuario: String? = sessionManager.obtenerIdSesion();
    private val _nombreCompleto = MutableStateFlow("")
    private val _rut = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _contrasena = MutableStateFlow("")
    private val _contrasenaRepetida = MutableStateFlow("")



    val nombreCompleto: StateFlow<String> = _nombreCompleto.asStateFlow()
    var rut: StateFlow<String> = _rut.asStateFlow()

    var email: StateFlow<String> = _email.asStateFlow()

    var contrasena: StateFlow<String> = _contrasena.asStateFlow()
    var contrasenaRepetida: StateFlow<String> = _contrasenaRepetida.asStateFlow()
    var edicionExito = MutableStateFlow(false)


    var errorNombre = MutableStateFlow("")
    var errorRut = MutableStateFlow("")
    var errorEmail = MutableStateFlow("")
    var errorContrasena = MutableStateFlow("")
    var errorContrasenaRepetida = MutableStateFlow("")



    fun onNombreChanged(nuevo: String) {
        _nombreCompleto.value = nuevo
        limpiarErrorNombre()
    }

    fun onRutChanged(nuevo: String) {
        _rut.value = nuevo
        limpiarErrorRut()
    }

    fun onEmailChanged(nuevo: String) {
        _email.value = nuevo
        limpiarErrorEmail()
    }

    fun onContrasenaChanged(nuevo: String) {
        _contrasena.value = nuevo
        limpiarErrorContrasena()
    }

    fun onContrasenaRepetidaChanged(nuevo: String) {
        _contrasenaRepetida.value = nuevo
        limpiarErrorContrasenaRepetida()
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


    fun resetEstadoEdicion(){
        edicionExito.value=false
    }


    init {
        obtenerUsuario()
    }

    fun obtenerUsuario(){
        viewModelScope.launch {
            if(idUsuario != null) {
                val resultado = repository.getUsuario(idUsuario.toInt())
                if(resultado !=null) {
                    _nombreCompleto.value = resultado.nombreCompleto
                    _rut.value = resultado.rut
                    _email.value = resultado.email
                    _contrasena.value = resultado.contrasena
                    _contrasenaRepetida.value=resultado.contrasena
                }
            }
        }
    }

    private fun validarNombre(): Boolean{
        return if (repository.validarNombre(_nombreCompleto.value)){
            errorNombre.value = "El nombre es obligatorio"
            false
        }else{
            errorNombre.value = ""
            true
        }
    }

    private fun validarRut(): Boolean {
        return if(repository.validarVacioRut(_rut.value)){
            errorRut.value = "El RUT es obligatorio"
            false
        }else if(repository.validarFormatoRut(_rut.value)){
            errorRut.value = "Formato inválido (ej: 12.345.678-k)"
            false
        }else{
            errorRut.value = ""
            true
        }
    }

    private fun validarEmail(): Boolean {
        return if(repository.validarVacioEmail(_email.value)){
            errorEmail.value = "El email es obligatorio"
            false
        }else if(repository.validarFormatoEmail(_email.value)){
            errorEmail.value = "Formato de email inválido"
            false
        }else{
            errorEmail.value = ""
            true
        }
    }

    private fun validarContrasena(): Boolean {
        return if(repository.validarVacioContrasena(_contrasena.value)){
            errorContrasena.value = "La contraseña es obligatoria"
            false
        }else{
            errorContrasena.value = ""
            true
        }
    }

    private fun validarContrasenaRepetida(): Boolean {
        return if(repository.validarVacionContrasenaRepetida(_contrasenaRepetida.value)){
            errorContrasenaRepetida.value = "Repetir la contraseña es obligatorio"
            false
        }else if(repository.validarCoincidenciaContrasena(_contrasena.value, _contrasenaRepetida.value)){
            errorContrasenaRepetida.value = "Las contraseñas no coinciden"
            false
        }else{
            errorContrasenaRepetida.value = ""
            true
        }
    }


    fun validarFormulario() :Boolean{
        val esValidoNombre = validarNombre()
        val esValidoRut = validarRut()
        val esValidoEmail = validarEmail()
        val esValidoContrasena = validarContrasena()
        val esValidoContrasenaRepetida = validarContrasenaRepetida()

        return esValidoNombre && esValidoRut && esValidoEmail &&
                esValidoContrasena && esValidoContrasenaRepetida
    }

    fun guardarPerfil(){

        if (idUsuario !=null) {
            viewModelScope.launch {
                val usuarioEditado = Usuario(
                    id = idUsuario.toInt(),
                    nombreCompleto = _nombreCompleto.value,
                    rut = _rut.value,
                    email = _email.value,
                    contrasena = _contrasena.value
                )
                repository.update(usuarioEditado)
                edicionExito.value=true
            }
        }

    }
}