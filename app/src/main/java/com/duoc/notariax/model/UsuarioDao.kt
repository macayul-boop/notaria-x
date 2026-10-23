package com.duoc.notariax.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE email = :email AND contrasena = :contrasena")
    suspend fun login(email: String, contrasena: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun getUsuario(id: Int) : Usuario?

    @Insert
    suspend fun insert(usuario: Usuario): Unit

    @Update
    suspend fun update(usuario: Usuario): Int

    @Delete
    suspend fun delete(usuario: Usuario): Int

}