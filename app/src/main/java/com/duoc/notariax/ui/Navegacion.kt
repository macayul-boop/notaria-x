package com.duoc.notariax.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duoc.notariax.model.UsuarioDto
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.utils.SessionManager
import com.duoc.notariax.viewmodel.DetalleTramiteViewModel
import com.duoc.notariax.viewmodel.InicioSesionViewModel
import com.duoc.notariax.viewmodel.InicioViewModel
import com.duoc.notariax.viewmodel.PerfilViewModel
import com.duoc.notariax.viewmodel.EditarPerfilViewModel
import com.duoc.notariax.viewmodel.InicioNotarioViewModel
import com.duoc.notariax.viewmodel.NavegacionViewModel
import com.duoc.notariax.viewmodel.ProgresoTramiteNotarioViewlModel
import com.duoc.notariax.viewmodel.ProgresoTramiteViewModel
import com.duoc.notariax.viewmodel.SeleccionarTramiteViewModel
import com.duoc.notariax.viewmodel.TomarTramiteViewlModel
import com.duoc.notariax.viewmodel.TramitesNotarioViewModel
import com.duoc.notariax.viewmodel.TramitesViewModel
import com.duoc.notariax.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun Navegacion(
    sessionManager: SessionManager,
    viewModel: NavegacionViewModel
){
    val navController = rememberNavController()

    val viewModelUsuario by lazy { UsuarioViewModel() }
    val viewModelInicioSesion by lazy { InicioSesionViewModel(sessionManager) }
    val viewModelSeleccionarTramite by lazy { SeleccionarTramiteViewModel() }
    val viewModelInicio by lazy { InicioViewModel(sessionManager) }
    val viewModelTramites by lazy { TramitesViewModel(sessionManager) }
    val viewModelPerfil by lazy { PerfilViewModel(sessionManager) }
    val viewModelEditarPerfil by lazy { EditarPerfilViewModel(sessionManager)}
    val viewModelInicioNotario by lazy { InicioNotarioViewModel(sessionManager) }
    val viewModelTomarTramite by lazy { TomarTramiteViewlModel(sessionManager) }
    val viewModelTramitesNotarioViewModel by lazy { TramitesNotarioViewModel(sessionManager) }

    LaunchedEffect(Unit) {
        viewModel.escogerRuta()
    }

    val rutaIncial by viewModel.rutaIncial.collectAsState()

    rutaIncial?.let {
        NavHost(
            navController = navController,
            startDestination = it
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
                    navController
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

                val viewModel: DetalleTramiteViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            // 1. Creamos el SavedStateHandle para que el ViewModel reciba los argumentos de la ruta
                            val savedStateHandle = createSavedStateHandle()

                            // 2. Instanciamos el ViewModel pasándole ambos parámetros
                            DetalleTramiteViewModel(
                                savedStateHandle = savedStateHandle,
                                sessionManager = sessionManager // <-- Aquí pasas tu instancia
                            )
                        }
                    }
                )

                DetalleTramite(
                    onRegresar = {
                        navController.navigate("seleccionar_tramite")
                    },
                    onTerminar = {
                        navController.navigate("inicio")
                    },
                    viewmodel = viewModel
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

            composable(
                "inicio_notario"
            ) {
                inicioNotario(
                    viewModel =  viewModelInicioNotario,
                    navController = navController,
                    onCerrarSesion = {
                        navController.navigate("login")
                        sessionManager.cerrarSesion()
                    }
                )
            }

            composable (
                "tomar_tramite"
            ){
                TomarTramite(
                    viewModelTomarTramite,
                    onRegresar = {
                        navController.navigate("inicio_notario")
                    }
                )
            }

            composable(
                "tramites_notario"
            ) {
                TramitesNotario(
                    viewModelTramitesNotarioViewModel,
                    onRegresar = {
                        navController.navigate("inicio_notario")
                    },
                    navController
                )
            }

            composable(
                "progreso_tramite_notario/{progresoTramiteId}",
                arguments = listOf(
                    navArgument("progresoTramiteId") { type = NavType.StringType }
                )
            ){

                val viewModel: ProgresoTramiteNotarioViewlModel = viewModel()

                ProgresoTramiteNotario(
                    viewModel,
                    onRegresar = {
                        navController.navigate("tramites_notario")
                    }
                )

            }

        }
    }

}