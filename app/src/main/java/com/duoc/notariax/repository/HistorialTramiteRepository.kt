package com.duoc.notariax.repository

import com.duoc.notariax.model.HistorialTramiteRequest
import com.duoc.notariax.remote.RetrofitInstance

class HistorialTramiteRepository {

    suspend fun crearHistorialTramite(request: HistorialTramiteRequest){
        return RetrofitInstance.api.crearEvento(request)
    }

}