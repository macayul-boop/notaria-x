package com.duoc.notariax.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import com.duoc.notariax.viewmodel.InicioSesionViewModel
import com.duoc.notariax.viewmodel.SeleccionarTramiteViewModel
import com.duoc.notariax.viewmodel.UsuarioViewModel

@Composable
fun Navegacion(
    sessionManager: SessionManager,
    repository: UsuarioRepository
){
    val navController = rememberNavController()

    val viewModelUsuario by lazy { UsuarioViewModel(repository) }
    val viewModelInicioSesion by lazy { InicioSesionViewModel(repository, sessionManager) }
    val viewModelSeleccionarTramite by lazy { SeleccionarTramiteViewModel() }

    val usuarioLogueado = sessionManager.obtenerIdSesion() != null
    //val rutaInicial = if(usuarioLogueado) "inicio" else "login"

    NavHost(
        navController = navController,
        startDestination = "seleccionar_tramite"
    ) {
        // Pantalla de inicio
        composable("inicio"){
            Inicio(
                onCerrarSesion = {
                    sessionManager.cerrarSesion()
                    navController.navigate("login")
                }
            )
        }

        // Pantalla de login
        composable("login") {
            InicioSesion(
                viewModel = viewModelInicioSesion,
                onIrRegistro = {
                    navController.navigate("registro")
                },
                onIrInicio = {
                    navController.navigate("inicio")
                }
            )
        }

        // Pantalla de registro
        composable("registro") {
            FormularioUsuari(
                viewModel = viewModelUsuario,
                onIrInicioSesion = {
                    navController.navigate("login")
                }
            )
        }

        // Pantalla de seleccionar tramite
        composable("seleccionar_tramite"){
            SeleccionarTramite(
                onContinuar = {tramiteId ->
                    navController.navigate("detalle_tramite/$tramiteId")
                },
                onRegresar = {
                    navController.navigate("inicio")
                },
                viewModelSeleccionarTramite
            )
        }

        // Pantalla detalles tramite
        composable(
            route = "detalle_tramite/{tramiteId}",
            arguments = listOf(
                navArgument("tramiteId") { type = NavType.StringType }
            )
        ) {

        }


    }

}