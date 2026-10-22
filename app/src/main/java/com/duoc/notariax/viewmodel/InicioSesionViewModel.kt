package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InicioSesionViewModel(
    private val repository: UsuarioRepository,
    private val sessionManager: SessionManager
): ViewModel() {

    val emailRegex = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""")

    var email = MutableStateFlow("")
    var contrasena = MutableStateFlow("")
    var exitoLogin = MutableStateFlow(false)

    var errorEmail = MutableStateFlow("")
    var errorContrasena = MutableStateFlow("")
    var errorLogin = MutableStateFlow("")

    fun limpiarErrorEmail() {errorEmail.value = ""}
    fun limpiarErrorContrasena() {errorContrasena.value = ""}
    fun limpiarErrorLogin() {errorLogin.value = ""}

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

    fun validarLogin(){

        limpiarErrorEmail()
        limpiarErrorContrasena()
        limpiarErrorLogin()

        val esValidoEmail = validarEmail()
        val esValidoContrase = validarContrasena()

        if(esValidoEmail && esValidoContrase){

            viewModelScope.launch {
                val user: Usuario? = repository.login(email.value, contrasena.value)
                if(user != null){
                    sessionManager.guardarSesion(user.id)
                    exitoLogin.value = true
                }else{
                    errorLogin.value = "Email o contraseña incorrecta"
                    exitoLogin.value = false
                }
            }

        }

    }

}