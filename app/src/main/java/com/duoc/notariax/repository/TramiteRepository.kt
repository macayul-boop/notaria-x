package com.duoc.notariax.repository

import com.duoc.notariax.model.Tramite
import com.duoc.notariax.model.TramiteDto
import com.duoc.notariax.remote.RetrofitInstance

interface ITramiteRepository {
    suspend fun obtenerTramites(): List<TramiteDto>
    suspend fun obtenerTramite(idTramite: Int): TramiteDto
}
class TramiteRepository : ITramiteRepository {

    // FUNCIONES QUE YA NO SE OCUPAN (LAS COSAS DE ABAJO NO PESCAR)
    private var listaTramite = listOf(
        Tramite(
            1,
            "Nombre tramite 1",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            listOf("Documento requerido 1", "Documento requerido 2", "Documento requerido 3")
        ),
        Tramite(
            2,
            "Nombre tramite 2",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            listOf("Documento requerido 1", "Documento requerido 2", "Documento requerido 3")
        ),
        Tramite(
            3,
            "Nombre tramite 3",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            listOf("Documento requerido 1", "Documento requerido 2", "Documento requerido 3")
        ),
    )

    fun obtenerTodosTramites(): List<Tramite> {
        return listaTramite
    }

    fun obtenerTramitePorId(idTramite: Int): Tramite? {
        return listaTramite.find { it.id == idTramite }
    }
    // FUNCIONES QUE YA NO SE OCUPAN (LAS COSAS DE ARRIBA NO PESCAR)

    override suspend fun obtenerTramites(): List<TramiteDto>{
        return RetrofitInstance.api.obtenerTramites();
    }

    override suspend fun obtenerTramite(idTramite: Int): TramiteDto {
        return RetrofitInstance.api.obtenerTramite(idTramite)
    }
}