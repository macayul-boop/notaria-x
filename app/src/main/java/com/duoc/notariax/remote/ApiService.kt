package com.duoc.notariax.remote

import com.duoc.notariax.model.Usuario
import com.duoc.notariax.model.UsuarioDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("usuarios")
    suspend fun crearUsuario(@Body usuario: UsuarioDto) : UsuarioDto

}