package com.duoc.notariax.viewmodel

import com.duoc.notariax.model.TramiteDto
import com.duoc.notariax.repository.ITramiteRepository
import com.duoc.notariax.viewmodel.SeleccionarTramiteViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SeleccionarTramiteViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun preparar() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `carga los tramites correctamente al iniciar`() = runTest {
        println("INICIO: comprobar la carga inicial de tramites en el ViewModel.")
        println("PREPARACION: el repositorio falso devolvera una lista de 2 tramites; no se usa internet.")
        // Ajusta los campos de TramiteDto según tu data class
        val tramitesFalsos = listOf(
            TramiteDto(id = 1, created_at = "7612381316", nombre = "Licencia", descripcion = "Descripcion licencia", documentos = "carnet de indentidad, examen teorico, examen practico"),
            TramiteDto(id = 2, created_at = "7612381316", nombre = "Pasaporte", descripcion = "Descripcion pasaporte", documentos = "carnet de indentidad")
        )
        val viewModel = SeleccionarTramiteViewModel(
            FakeTramiteRepository { tramitesFalsos }
        )

        println("ACCION: ejecutar las corrutinas pendientes del bloque init.")
        advanceUntilIdle()

        println("COMPROBACION: el StateFlow listaTramites debe contener los 2 tramites falsos.")
        assertEquals(tramitesFalsos, viewModel.listaTramites.value)
        println("CORRECTO: se cargaron y publicaron los tramites esperados.")
    }

    @Test
    fun `seleccionarTramite selecciona y deselecciona al volver a tocar`() = runTest {
        println("INICIO: comprobar la seleccion y deseleccion de un tramite.")
        println("PREPARACION: inicializar el ViewModel con una lista vacia.")
        val viewModel = SeleccionarTramiteViewModel(
            FakeTramiteRepository { emptyList() }
        )
        advanceUntilIdle()

        println("ACCION: seleccionar el tramite con id 5.")
        viewModel.seleccionarTramite(5)
        println("COMPROBACION: tramiteSeleccionado debe ser 5.")
        assertEquals(5, viewModel.tramiteSeleccionado.value)

        println("ACCION: volver a seleccionar el mismo tramite con id 5.")
        viewModel.seleccionarTramite(5)
        println("COMPROBACION: tramiteSeleccionado debe volver a null.")
        assertEquals(null, viewModel.tramiteSeleccionado.value)
        println("CORRECTO: el tramite se selecciono y deselecciono correctamente.")
    }

    @Test
    fun `validar muestra error si no hay tramite seleccionado y limpia si lo hay`() = runTest {
        println("INICIO: comprobar la validacion de seleccion de tramite.")
        println("PREPARACION: inicializar el ViewModel sin ningun tramite seleccionado.")
        val viewModel = SeleccionarTramiteViewModel(
            FakeTramiteRepository { emptyList() }
        )
        advanceUntilIdle()

        println("ACCION: ejecutar validar() sin haber seleccionado un tramite.")
        viewModel.validar()
        println("COMPROBACION: esValido debe mostrar el mensaje de error 'Selecciona un tramite'.")
        assertEquals("Selecciona un tramite", viewModel.esValido.value)

        println("ACCION: seleccionar el tramite 1 y volver a ejecutar validar().")
        viewModel.seleccionarTramite(1)
        viewModel.validar()
        println("COMPROBACION: esValido debe quedar como texto vacio.")
        assertEquals("", viewModel.esValido.value)
        println("CORRECTO: la validacion controla correctamente ambos escenarios.")
    }

    @Test
    fun `si falla el repositorio la lista queda vacia sin crashear`() = runTest {
        println("INICIO: comprobar el manejo de excepciones cuando falla el repositorio.")
        println("PREPARACION: el repositorio falso lanzara una excepcion simulando un fallo de red.")
        val viewModel = SeleccionarTramiteViewModel(
            FakeTramiteRepository { throw Exception("Error de red") }
        )

        println("ACCION: ejecutar la carga inicial esperando que el try-catch capture el error.")
        advanceUntilIdle()

        println("COMPROBACION: la aplicacion no debe fallar y listaTramites debe mantenerse vacia.")
        assertEquals(emptyList<TramiteDto>(), viewModel.listaTramites.value)
        println("CORRECTO: el ViewModel controlo la excepcion sin detener la ejecucion.")
    }



    private class FakeTramiteRepository(
        private val respuesta: suspend () -> List<TramiteDto>
    ) : ITramiteRepository {
        override suspend fun obtenerTramites(): List<TramiteDto> = respuesta()

        override suspend fun obtenerTramite(idTramite: Int): TramiteDto {
            throw NotImplementedError("No se usa en este ViewModel")
        }
    }
}