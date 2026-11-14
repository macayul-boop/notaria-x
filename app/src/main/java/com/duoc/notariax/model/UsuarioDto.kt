package com.duoc.notariax.model

class UsuarioDto (
    val id: Int = 0,
    val created_at: String = "",
    var nombre: String,
    var rut: String,
    var email: String,
    var contrasena: String,
    var tipo_usuario: TipoUsuario
)