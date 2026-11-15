package com.duoc.notariax.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.duoc.notariax.utils.SessionManager

@Composable
fun inicioNotario(
    navController: NavController,
    sessionManager: SessionManager
){
    Text(text = "Inicio notario")
    Button(
        onClick = {
            sessionManager.cerrarSesion()
            navController.navigate("login")
        }
    ) { Text(text = "Cerrar sesion")}
}