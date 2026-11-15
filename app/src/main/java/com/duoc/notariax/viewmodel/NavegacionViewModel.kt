package com.duoc.notariax.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariax.model.TipoUsuario
import com.duoc.notariax.model.UsuarioDto
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavegacionViewModel(
    sessionManager: SessionManager
) : ViewModel() {
    val repository: UsuarioRepository = UsuarioRepository()

    val usuarioLogueado = sessionManager.obtenerIdSesion()?.toInt()

    var _rutaInicial = MutableStateFlow<String?>(null)
    var  rutaIncial: StateFlow<String?> = _rutaInicial.asStateFlow()

    fun escogerRuta(){
        if(usuarioLogueado != null){
            viewModelScope.launch {
                try {
                    val usuario: UsuarioDto = repository.obtenerUsuario(usuarioLogueado);

                    if(usuario.tipo_usuario.equals(TipoUsuario.cliente)){
                        _rutaInicial.value = "inicio"
                    }else if(usuario.tipo_usuario.equals(TipoUsuario.notario)){
                        _rutaInicial.value = "inicio_notario"
                    }else{
                        _rutaInicial.value = "login"
                    }
                }catch (e: Exception){
                    print(e)
                }
            }
        }else{
            _rutaInicial.value = "login"
        }
    }
}