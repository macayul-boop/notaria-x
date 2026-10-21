package com.duoc.notariax.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Inicio(
    onCerrarSesion: () -> Unit
){
    Button(onClick = {onCerrarSesion()}) {
        Text(text = "Cerrar Sesion")
    }
}