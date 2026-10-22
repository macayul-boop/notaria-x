package com.duoc.notariax.repository

import com.duoc.notariax.model.Tramite

class TramiteRepository {

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

}