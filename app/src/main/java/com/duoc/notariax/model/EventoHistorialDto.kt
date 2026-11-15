package com.duoc.notariax.model

class EventoHistorialDto(
    val id: Int = 0,
    val created_at: String = "",
    val tramite_usuario_id: Int,
    val titulo: String,
    val descripcion: String,
    val tipo: String
) {
}