package com.duoc.notariax.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProgresoTramite(
    val id: Int,
    val idTramite: Int,
    var estado: EstadoTramite = EstadoTramite.Abierto,
    val fechaCreacion: Date,
    val historial: List<EventoHistorial> = emptyList()
) {

    val fechaFormateada: String get() {
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return formato.format(fechaCreacion)
    }


}