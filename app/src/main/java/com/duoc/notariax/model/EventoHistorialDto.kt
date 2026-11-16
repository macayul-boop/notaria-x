package com.duoc.notariax.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventoHistorialDto(
    val id: Int = 0,
    val created_at: Long? = null,
    val tramite_usuario_id: Int,
    val titulo: String,
    val descripcion: String,
    val tipo: String
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