package com.duoc.notariax.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface TipoEvento {
    val color: Color
    val icono: ImageVector

    object Solicitado : TipoEvento {
        override val color = Color(0xFF2196F3)
        override val icono = Icons.Default.Info
    }

    object EnRevision : TipoEvento {
        override val color = Color(0xFFFF9800)
        override val icono = Icons.Default.HourglassTop
    }

    object DocumentoFaltante : TipoEvento {
        override val color = Color(0xFFFFC107)
        override val icono = Icons.Default.Warning
    }

    object Finalizado : TipoEvento {
        override val color = Color(0xFF4CAF50)
        override val icono = Icons.Default.CheckCircle
    }

    object Rechazado : TipoEvento {
        override val color = Color(0xFFF44336)
        override val icono = Icons.Default.Error
    }
}