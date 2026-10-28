package com.duoc.notariax.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import com.duoc.notariax.viewmodel.DetalleTramiteViewModel
import com.duoc.notariax.viewmodel.InicioSesionViewModel
import com.duoc.notariax.viewmodel.InicioViewModel
import com.duoc.notariax.viewmodel.PerfilViewModel
import com.duoc.notariax.viewmodel.EditarPerfilViewModel
import com.duoc.notariax.viewmodel.ProgresoTramiteViewModel
import com.duoc.notariax.viewmodel.SeleccionarTramiteViewModel
import com.duoc.notariax.viewmodel.TramitesViewModel
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
    val viewModelInicio by lazy { InicioViewModel(repository,sessionManager) }
    val viewModelTramites by lazy { TramitesViewModel() }
    val viewModelPerfil by lazy { PerfilViewModel(repository, sessionManager) }
    val viewModelEditarPerfil by lazy { EditarPerfilViewModel(repository, sessionManager)}

    val usuarioLogueado = sessionManager.obtenerIdSesion() != null
    val rutaInicial = if(usuarioLogueado) "inicio" else "login"

    NavHost(
        navController = navController,
        startDestination = rutaInicial
    ) {
        // Pantalla de inicio
        composable("inicio"){
            Inicio(
                navController,
                viewModelInicio
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

            val viewModel: DetalleTramiteViewModel = viewModel()

            DetalleTramite(
                onRegresar = {
                    navController.navigate("seleccionar_tramite")
                },
                onTerminar = {
                    navController.navigate("inicio")
                },
                viewModel
            )
        }

        // Pantalla de tramites
        composable(
            "tramites"
        ){
            Tramites(
                navController,
                onRegresar = {
                    navController.navigate("inicio")
                },
                viewModelTramites
            )
        }

        // Pantalla de progreso tramite
        composable(
            "progreso_tramite/{progresoTramiteId}",
            arguments = listOf(
                navArgument("progresoTramiteId") { type = NavType.StringType }
            )
        ){

            val viewModel: ProgresoTramiteViewModel = viewModel()

            ProgresoTramite(
                viewModel,
                onRegresar = {
                    navController.navigate("tramites")
                }
            )

        }

        // Perfil usuario
        composable(
            "perfil"
        ) {
            Perfil(
                onCerrarSesion = {
                    sessionManager.cerrarSesion()
                    navController.navigate("login")
                },

                onRegresar=  {
                    navController.navigate("inicio")
                },
                navController,
                viewModelPerfil
                )
        }

        composable(
            "editar_perfil"
        ){
            EditarPerfil(
                onRegresar=  {
                    navController.navigate("perfil")
                },
                viewModelEditarPerfil,
                navController

            )
        }


    }

}