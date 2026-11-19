package com.duoc.notariax.model

data class ActualizarUsuarioRequest(
    val nombre: String,
    val rut: String,
    val email: String,
    val contrasena: String?= null,
    val tipo_usuario: TipoUsuario
)