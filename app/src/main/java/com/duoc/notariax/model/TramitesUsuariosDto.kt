package com.duoc.notariax.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TramitesUsuariosDto(
    val id: Int = 0,
    val created_at: Long? = null,
    val cliente_id: Int,
    val notario_id: Int = 0,
    val tramite_id: Int,
    var estado: EstadoTramitesUsuarios,
    val _cliente: UsuarioDto? = null,
    val _tramite: TramiteDto? = null,
    val _notario: UsuarioDto? = null,
    val historial: List<EventoHistorialDto>? = null
) {

    fun obtenerFechaFormateada(): String {
        if(created_at != null){
            val fecha = Date(created_at)
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return formato.format(fecha)
        }else{
            return "cargando"
        }
    }

}