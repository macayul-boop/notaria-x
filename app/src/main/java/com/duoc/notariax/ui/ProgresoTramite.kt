package com.duoc.notariax.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.duoc.notariax.model.ProgresoTramite
import com.duoc.notariax.viewmodel.ProgresoTramiteViewModel

@Composable
fun ProgresoTramite(
    viewModel: ProgresoTramiteViewModel,
    onRegresar: () -> Unit
){

    Text(text = "Vista progreso tramite")
}