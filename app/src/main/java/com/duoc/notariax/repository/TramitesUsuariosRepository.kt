package com.duoc.notariax.repository

import com.duoc.notariax.model.TramitesUsuariosDto
import com.duoc.notariax.remote.RetrofitInstance

class TramitesUsuariosRepository {

    suspend fun crearTramiteUsuario(request: TramitesUsuariosDto){
        return RetrofitInstance.api.crearTramiteUsuario(request)
    }

}