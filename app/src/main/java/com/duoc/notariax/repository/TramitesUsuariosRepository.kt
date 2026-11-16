package com.duoc.notariax.repository

import com.duoc.notariax.model.CambiarEstadoRequest
import com.duoc.notariax.model.TomarTramiteRequest
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.remote.RetrofitInstance

class TramitesUsuariosRepository {

    suspend fun crearTramiteUsuario(request: TramitesUsuariosDto){
        return RetrofitInstance.api.crearTramiteUsuario(request)
    }

    suspend fun obtenerTramiteUsuarioPorIdUsuario(idUsuario: Int): List<TramitesUsuariosDto>{
        return RetrofitInstance.api.obtenerTramiteUsuarioPorIdUsuario(idUsuario);
    }

    suspend fun obtenerTramiteUsuarioPorId(idTramite: Int): TramitesUsuariosDto{
        return RetrofitInstance.api.obtenerTramitePorId(idTramite)
    }

    suspend fun obtenerTramitesSinNotario(): List<TramitesUsuariosDto>{
        return RetrofitInstance.api.obtenerTramitesSinNotario();
    }

    suspend fun tomarTramite(idTramiteUsuario: Int, request: TomarTramiteRequest){
        return RetrofitInstance.api.tomarTramite(idTramiteUsuario, request);
    }

    suspend fun cambiarEstado(idTramiteUsuario: Int, request: CambiarEstadoRequest){
        return RetrofitInstance.api.cambiarEstado(idTramiteUsuario, request)
    }

    fun validarTituloVacio(inputTitulo: String): Boolean{
        if(inputTitulo.isEmpty()){
            return true
        }else{
            return false
        }
    }

    fun validarDescripcionVacio(inputDescripcion: String): Boolean{
        if(inputDescripcion.isEmpty()){
            return true
        }else{
            return false
        }
    }

    fun validarTipoMensajeVacio(inputTipo: String): Boolean{
        if(inputTipo.isEmpty()){
            return true
        }else{
            return false
        }
    }

}