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

    @Insert
    suspend fun insert(usuario: Usuario): Unit

    @Update
    suspend fun update(usuario: Usuario): Int

    @Delete
    suspend fun delete(usuario: Usuario): Int

}