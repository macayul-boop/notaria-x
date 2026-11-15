package com.duoc.notariax.remote

import com.duoc.notariax.model.LoginRequest
import com.duoc.notariax.model.TramiteDto
import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.model.UsuarioDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("usuarios")
    suspend fun crearUsuario(@Body usuario: UsuarioDto) : UsuarioDto

    @POST("login")
    suspend fun login(@Body request: LoginRequest) : UsuarioDto

    @GET("usuarios/{usuarios_id}")
    suspend fun obtenerUsuario(@Path("usuarios_id") id: Int) : UsuarioDto

    @GET("tramites")
    suspend fun obtenerTramites() : List<TramiteDto>

    @GET("tramites/{tramites_id}")
    suspend fun obtenerTramite(@Path("tramites_id") id: Int) : TramiteDto

    @POST("tramites_usuarios")
    suspend fun crearTramiteUsuario(@Body tramite: TramitesUsuariosDto)
}