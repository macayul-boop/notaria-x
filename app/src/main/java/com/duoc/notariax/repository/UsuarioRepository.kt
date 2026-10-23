package com.duoc.notariax.repository

import com.duoc.notariax.model.Usuario
import com.duoc.notariax.model.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.text.insert
import kotlin.text.matches

class UsuarioRepository(private val dao: UsuarioDao) {

    val rutRegex = Regex("""^\d{1,2}\.\d{3}\.\d{3}-[\dkK]$""")
    val emailRegex = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""")

    suspend fun getAll() = dao.getAll()
    suspend fun login(email: String, contrasena: String): Usuario? = dao.login(email, contrasena)
    suspend fun getUsuario(id: Int): Usuario? = dao.getUsuario(id)
    suspend fun insert(usuario: Usuario): Unit = dao.insert(usuario)
    suspend fun update(usuario: Usuario): Int = dao.update(usuario)
    suspend fun delete(usuario: Usuario): Int = dao.delete(usuario)

    // NOMBRE
    fun validarNombre(nombreCompleto: String): Boolean {
        return if (nombreCompleto.isBlank()) {
            true
        } else {
            false
        }
    }

    // RUT
    fun validarVacioRut(rut: String): Boolean{
        if(rut.isBlank()){
            return true
        }else{
            return false
        }
    }

    fun validarFormatoRut(rut: String): Boolean{
        if(!rutRegex.matches(rut)){
            return true
        }else{
            return false
        }
    }

    // EMAIL
    fun validarVacioEmail(email: String): Boolean{
        if(email.isBlank()){
            return true
        }else{
            return false
        }
    }

    fun validarFormatoEmail(email: String): Boolean{
        if(!emailRegex.matches(email)){
            return true
        }else{
            return false
        }
    }

    // CONTRASEÑA

    fun validarVacioContrasena(contrasena: String): Boolean{
        if(contrasena.isBlank()){
            return true
        }else{
            return false
        }
    }

    // CONTRASEÑA REPETIDA

    fun validarVacionContrasenaRepetida(contrasenaRepetida: String): Boolean{
        if(contrasenaRepetida.isBlank()){
            return true
        }else{
            return false
        }
    }

    fun validarCoincidenciaContrasena(contrasena: String, contrasenaRepetida: String): Boolean{
        if(contrasenaRepetida != contrasena){
            return true
        }else{
            return false
        }
    }

}