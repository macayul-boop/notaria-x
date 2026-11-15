package com.duoc.notariax.model

class TramitesUsuariosDto(
    val id: Int = 0,
    val created_at: String = "",
    val cliente_id: Int,
    val notario_id: Int = 0,
    val tramite_id: Int,
    var estado: EstadoTramitesUsuarios,
    val _cliente: UsuarioDto? = null,
    val _tramite: TramiteDto? = null,
    val _notario: UsuarioDto? = null
) {
}