package com.duoc.notariax.repository

import com.duoc.notariax.model.EstadoTramite
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.model.ProgresoTramite
import com.duoc.notariax.model.Tramite
import java.util.Date

class ProgresoTramiteRepository(){

    private val repositoryTramite = TramiteRepository()

    private val listaProgesoTramite = listOf(
        ProgresoTramite(
            1,
            1,
            EstadoTramite.Abierto,
            Date()
        ),
        ProgresoTramite(
            2,
            2,
            EstadoTramite.Cancelado,
            Date()
        ),
        ProgresoTramite(
            3,
            3,
            EstadoTramite.Finalizado,
            Date()
        )
    )

    fun obtenerProgesoConTramite(limite: Int = 0): List<ProgresoConTramite>{

        val listaProgresoConTramite = mutableListOf<ProgresoConTramite>()

        if(limite != 0){
            for (i in 0..limite - 1){
                val progresoTramite: ProgresoTramite = listaProgesoTramite[i]
                val tramite: Tramite? = repositoryTramite.obtenerTramitePorId(progresoTramite.idTramite)

                if(tramite != null){
                    listaProgresoConTramite.add(ProgresoConTramite(tramite, progresoTramite))
                }
            }
        }else{
            for (item in listaProgesoTramite){
                val tramite: Tramite? = repositoryTramite.obtenerTramitePorId(item.idTramite)

                if(tramite != null){
                    listaProgresoConTramite.add(ProgresoConTramite(tramite, item))
                }
            }
        }

        return listaProgresoConTramite
    }

}