package com.duoc.notariax.remote

import com.duoc.notariax.model.LoginRequest
import com.duoc.notariax.model.Usuario
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


}