package com.duoc.notariax.repository

import com.duoc.notariax.model.Usuario
import com.duoc.notariax.model.UsuarioDao
import kotlin.text.insert

class UsuarioRepository(private val dao: UsuarioDao) {

    suspend fun getAll() = dao.getAll()
    suspend fun insert(usuario: Usuario): Unit = dao.insert(usuario)
    suspend fun update(usuario: Usuario): Int = dao.update(usuario)
    suspend fun delete(usuario: Usuario): Int = dao.delete(usuario)

}