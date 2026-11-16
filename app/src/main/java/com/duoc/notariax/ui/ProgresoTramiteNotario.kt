package com.duoc.notariax.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariax.model.EstadoTramitesUsuarios
import com.duoc.notariax.model.TipoUsuario
import com.duoc.notariax.model.UsuarioDto
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.cafeOscuroClaro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.ProgresoTramiteNotarioViewlModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgresoTramiteNotario(
    viewlModel: ProgresoTramiteNotarioViewlModel,
    onRegresar: ()-> Unit
){
    val tramiteUsuario by viewlModel.tramiteUsuario.collectAsState()
    val historial = tramiteUsuario?.historial ?: emptyList()

    var dropdownExpandido by remember { mutableStateOf(false) }
    val dropdownSeleccionado by viewlModel.opcionSeleccionada.collectAsState()
    val opcionesDropdown = viewlModel.opcionesDropdown

    val titulo by viewlModel.titulo.collectAsState()
    val descripcion by viewlModel.descripcion.collectAsState()

    val errorTitulo by viewlModel.errorTitulo.collectAsState()
    val errorDescripcion by viewlModel.errorDescripcion.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cafeOscuroClaro)
            .padding(horizontal = 25.dp, vertical = 70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clickable { onRegresar() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retroceder",
                    modifier = Modifier.size(35.dp),
                    tint = mostaza,
                )
            }

            Button(
                onClick = {viewlModel.cambiarEstado()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = mostaza,
                    contentColor = Color.White
                )
            ){
                Text(
                    text = "Finalizar tramite"
                )
            }
        }

        Spacer(Modifier.height(50.dp))

        if(tramiteUsuario != null){

            Column(
            ) {
                Text(
                    text = tramiteUsuario!!._tramite?.nombre ?: "Cargando...",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = mostaza
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = tramiteUsuario!!.obtenerFechaFormateada(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Thin,
                    color = mostaza
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Cliente: ${tramiteUsuario!!._cliente?.nombre ?: "Cargando..."}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Thin,
                color = mostaza
            )

            Spacer(modifier = Modifier.height(10.dp))

            val estado: String = when(tramiteUsuario!!.estado){
                EstadoTramitesUsuarios.abierto -> "Abierto"
                EstadoTramitesUsuarios.finalizado -> "Finalizado"
            }

            Text(
                text = estado,
                color = Color.White,
                modifier = Modifier
                    .background(
                        color = cafeOscuro,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )



            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = mostaza,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {

                OutlinedTextField(
                    value = titulo,
                    onValueChange = {
                        viewlModel.modificarTitulo(it)
                        if(errorTitulo.isNotEmpty()){
                            viewlModel.limpiarTituloError()
                        }
                    },
                    label = { Text("Titulo") },
                    isError = errorTitulo.isNotEmpty(),
                    supportingText = {
                        if (errorTitulo.isNotEmpty()) {
                            Text(
                                text = errorTitulo,
                                color = androidx.compose.ui.graphics.Color.Red
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = mostaza,
                        unfocusedIndicatorColor = mostaza,
                        focusedLabelColor = mostaza,
                        unfocusedLabelColor = mostaza,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        viewlModel.modificarDescripcion(it)
                        if(errorDescripcion.isNotEmpty()){
                            viewlModel.limpiarDescripcionError()
                        }
                    },
                    label = { Text("Descripcion") },
                    isError = errorDescripcion.isNotEmpty(),
                    supportingText = {
                        if (errorDescripcion.isNotEmpty()) {
                            Text(
                                text = errorDescripcion,
                                color = androidx.compose.ui.graphics.Color.Red
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = mostaza,
                        unfocusedIndicatorColor = mostaza,
                        focusedLabelColor = mostaza,
                        unfocusedLabelColor = mostaza,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = dropdownExpandido,
                    onExpandedChange = { dropdownExpandido = !dropdownExpandido }
                ) {
                    OutlinedTextField(
                        value = dropdownSeleccionado,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Dropdown") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = mostaza,
                            unfocusedIndicatorColor = mostaza,
                            focusedLabelColor = mostaza,
                            unfocusedLabelColor = mostaza,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = dropdownExpandido,
                        onDismissRequest = { dropdownExpandido = false }
                    ) {
                        opcionesDropdown.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    viewlModel.seleccionarOpcion(opcion)
                                    dropdownExpandido = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        viewlModel.crearEvento()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mostaza,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 50.dp)
                ) {
                    Text("Crear Evento")
                }

            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Historial del Trámite",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp),
                color = mostaza
            )

            Spacer(modifier = Modifier.height(30.dp))


            if(historial.isEmpty()){
                Text(text = "Aun no hay registros", color = mostaza)
            }else{
                LazyColumn() {
                    itemsIndexed(historial) { index, evento ->
                        val esUltimo = index == tramiteUsuario!!.historial?.lastIndex
                        LineaDeTiempo(
                            evento = evento,
                            esElUltimo = esUltimo
                        )
                    }
                }
            }

        }else{
            Text(
                text = "Cargando informacion...",
                color = mostaza,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}