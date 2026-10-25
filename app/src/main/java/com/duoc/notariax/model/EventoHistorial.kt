package com.duoc.notariax.model

class EventoHistorial(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fechaHora: String,
    val actor: String,
    val tipo: TipoEvento
) {
}