package com.duoc.notariax.repository

import android.net.Uri

class ArchivosRepository {

    private val listaArchivo = mutableListOf<Uri>()

    fun obtenerArchivos(): List<Uri>{
        return listaArchivo.toList()
    }

    fun agregarArchivos(uris: List<Uri>): Unit{
        if(uris.isNotEmpty()){
            listaArchivo += uris
        }
    }

    fun eliminarArchivo(uri: Uri): Unit{
        listaArchivo.remove(uri)
    }

}