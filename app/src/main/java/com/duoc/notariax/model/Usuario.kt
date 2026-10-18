package com.duoc.notariax.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombreCompleto: String,
    var rut: String,
    var email: String,
    var contrasena: String
)