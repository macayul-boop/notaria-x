package com.duoc.notariax.repository

import com.duoc.notariax.model.EstadoTramite
import com.duoc.notariax.model.EventoHistorial
import com.duoc.notariax.model.ProgresoConTramite
import com.duoc.notariax.model.ProgresoTramite
import com.duoc.notariax.model.TipoEvento
import com.duoc.notariax.model.Tramite
import java.util.Date

class ProgresoTramiteRepository(){

    private val repositoryTramite = TramiteRepository()

    private val listaProgesoTramite = listOf(
        ProgresoTramite(
            1,
            1,
            EstadoTramite.Finalizado,
            Date(),
            listOf(
                EventoHistorial(
                    id = 1,
                    titulo = "Solicitud Ingresada",
                    descripcion = "El trámite está esperando a ser tomado por un notario.",
                    fechaHora = "24 Oct, 09:00 AM",
                    actor = "Sistema",
                    tipo = TipoEvento.Solicitado
                ),
                EventoHistorial(
                    id = 2,
                    titulo = "Trámite Asignado",
                    descripcion = "El notario Lic. Roberto Gómez tomó el trámite para revisión.",
                    fechaHora = "24 Oct, 09:30 AM",
                    actor = "Notario Roberto Gómez",
                    tipo = TipoEvento.EnRevision
                ),
                EventoHistorial(
                    id = 3,
                    titulo = "Documentación Faltante",
                    descripcion = "El notario solicita adjuntar fotocopia del documento de identidad por ambos lados.",
                    fechaHora = "24 Oct, 10:15 AM",
                    actor = "Notario Roberto Gómez",
                    tipo = TipoEvento.DocumentoFaltante
                ),
                EventoHistorial(
                    id = 4,
                    titulo = "Trámite Finalizado",
                    descripcion = "El documento ha sido denegado por falta de docuemntos solicitados",
                    fechaHora = "24 Oct, 11:00 AM",
                    actor = "Notario Roberto Gómez",
                    tipo = TipoEvento.Rechazado
                )
            )
        ),
        ProgresoTramite(
            2,
            2,
            EstadoTramite.Abierto,
            Date(),
            listOf(
                EventoHistorial(
                    id = 1,
                    titulo = "Solicitud Ingresada",
                    descripcion = "El trámite está esperando a ser tomado por un notario.",
                    fechaHora = "24 Oct, 09:00 AM",
                    actor = "Sistema",
                    tipo = TipoEvento.Solicitado
                ),
                EventoHistorial(
                    id = 2,
                    titulo = "Trámite Asignado",
                    descripcion = "El notario Lic. Roberto Gómez tomó el trámite para revisión.",
                    fechaHora = "24 Oct, 09:30 AM",
                    actor = "Notario Roberto Gómez",
                    tipo = TipoEvento.EnRevision
                )
            )
        ),
        ProgresoTramite(
            3,
            3,
            EstadoTramite.Finalizado,
            Date(),
            listOf(
                EventoHistorial(
                    id = 1,
                    titulo = "Solicitud Ingresada",
                    descripcion = "El trámite está esperando a ser tomado por un notario.",
                    fechaHora = "24 Oct, 09:00 AM",
                    actor = "Sistema",
                    tipo = TipoEvento.Solicitado
                ),
                EventoHistorial(
                    id = 2,
                    titulo = "Trámite Asignado",
                    descripcion = "El notario Lic. Roberto Gómez tomó el trámite para revisión.",
                    fechaHora = "24 Oct, 09:30 AM",
                    actor = "Notario Roberto Gómez",
                    tipo = TipoEvento.EnRevision
                ),
                EventoHistorial(
                    id = 4,
                    titulo = "Trámite Finalizado",
                    descripcion = "El tramite fue aprobado por el notario",
                    fechaHora = "24 Oct, 11:00 AM",
                    actor = "Notario Roberto Gómez",
                    tipo = TipoEvento.Finalizado
                )
            )
        )
    )

    fun obtenerVariosProgesoConTramite(limite: Int = 0): List<ProgresoConTramite>{

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

    fun obtenerProgresoConTramite(idProgreso: Int): ProgresoConTramite? {
        val progresoTramite: ProgresoTramite? = listaProgesoTramite.find { it.id == idProgreso }

        if(progresoTramite == null){ return null }

        val tramite: Tramite? = repositoryTramite.obtenerTramitePorId(progresoTramite.idTramite)

        if(tramite == null){ return null }

        return ProgresoConTramite(tramite, progresoTramite);
    }

}