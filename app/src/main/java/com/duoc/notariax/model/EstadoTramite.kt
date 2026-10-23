package com.duoc.notariax.model

sealed class EstadoTramite {
    abstract val nombreDisplay: String

    object Abierto : EstadoTramite() {
        override val nombreDisplay: String = "Abierto"
    }

    object Finalizado : EstadoTramite() {
        override val nombreDisplay: String = "Finalizado"
    }

    object Cancelado : EstadoTramite() {
        override val nombreDisplay: String = "Cancelado"
    }
}