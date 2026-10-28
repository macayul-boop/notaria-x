package com.duoc.notariax.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.EditarPerfilViewModel


@Composable
fun EditarPerfil(
    onRegresar: () -> Unit,
    viewModel: EditarPerfilViewModel,
    navegacion: NavController
){

    val nombreCompleto by viewModel.nombreCompleto.collectAsState()
    val rut by viewModel.rut.collectAsState()
    val email by viewModel.email.collectAsState()
    val contrasena by viewModel.contrasena.collectAsState()
    val contrasenaRepetida by viewModel.contrasenaRepetida.collectAsState()
    val edicionExito by viewModel.edicionExito.collectAsState()

    val errorNombre by viewModel.errorNombre.collectAsState()
    val errorRut by viewModel.errorRut.collectAsState()
    val errorEmail by viewModel.errorEmail.collectAsState()
    val errorContrasena by viewModel.errorContrasena.collectAsState()
    val errorContrasenaRepetida by viewModel.errorContrasenaRepetida.collectAsState()

    var estaVisibleContrasena by remember { mutableStateOf(false) }
    var estaVisibleContrasenaRepetida by remember { mutableStateOf(false) }

    var mostrarModal by remember { mutableStateOf(false) }

    LaunchedEffect(edicionExito) {
        if (edicionExito) {
            viewModel.resetEstadoEdicion()
            onRegresar()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
    }

    if (mostrarModal){
        AlertDialog(
            onDismissRequest =  {mostrarModal = false},
            title = {
                Text(
                    text="Confirmar cambios",
                    color = mostaza,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "¿Estas seguro de los cambios realizados en tu perfil?",
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarModal = false
                        viewModel.guardarPerfil()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mostaza,
                        contentColor = Color.White
                    )
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {mostrarModal = false}
                ){
                    Text(
                        text = "Cancelar",
                        color = Color.Gray
                    )
                }
            },
            containerColor = cafeOscuro,
            shape = RoundedCornerShape(12.dp)
        )
    }



    Box(modifier = Modifier
        .fillMaxSize()
        .background(cafeOscuro)
    ){
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp)
                .zIndex(1f)
                .clickable { onRegresar() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retroceder",
                modifier = Modifier.size(35.dp),
                tint = mostaza,
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Editar Perfil",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = mostaza
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Input de nombre
            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = {
                    viewModel.onNombreChanged(it)
                },
                label = { Text("Nombre Completo") },
                isError = errorNombre.isNotEmpty(),
                supportingText = {
                    if (errorNombre.isNotEmpty()) {
                        Text(
                            text = errorNombre,
                            color = Color.Red
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = mostaza,
                    unfocusedIndicatorColor = mostaza,
                    focusedLabelColor = mostaza,
                    unfocusedLabelColor = mostaza,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            // Input de Rut
            OutlinedTextField(
                value = rut,
                onValueChange = {
                    viewModel.onRutChanged(it)
                },
                label = { Text("Rut") },
                isError = errorRut.isNotEmpty(),
                placeholder = { Text(text = "12.345.678-9") },
                supportingText = {
                    if (errorRut.isNotEmpty()) {
                        Text(
                            text = errorRut,
                            color = Color.Red
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = mostaza,
                    unfocusedIndicatorColor = mostaza,
                    focusedLabelColor = mostaza,
                    unfocusedLabelColor = mostaza,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            // Input de email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    viewModel.onEmailChanged(it)
                },
                label = { Text("Email") },
                isError = errorEmail.isNotEmpty(),
                placeholder = { Text(text = "ejemplo@gmail.com") },
                supportingText = {
                    if (errorEmail.isNotEmpty()) {
                        Text(
                            text = errorEmail,
                            color = Color.Red
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = mostaza,
                    unfocusedIndicatorColor = mostaza,
                    focusedLabelColor = mostaza,
                    unfocusedLabelColor = mostaza,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            // Input de contraseña
            OutlinedTextField(
                value = contrasena,
                onValueChange = {
                    viewModel.onContrasenaChanged(it)
                },
                label = { Text("Nueva Contraseña") },
                isError = errorContrasena.isNotEmpty(),
                placeholder = { Text(text = "Ingresa tu nueva contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (estaVisibleContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val imagenIcono =
                        if (estaVisibleContrasena) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val descripcion =
                        if (estaVisibleContrasena) "Ocultar contraseña" else "Mostrar contraseña"

                    IconButton(onClick = { estaVisibleContrasena = !estaVisibleContrasena }) {
                        Icon(imageVector = imagenIcono, contentDescription = descripcion)
                    }
                },
                supportingText = {
                    if (errorContrasena.isNotEmpty()) {
                        Text(
                            text = errorContrasena,
                            color = Color.Red
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = mostaza,
                    unfocusedIndicatorColor = mostaza,
                    focusedLabelColor = mostaza,
                    unfocusedLabelColor = mostaza,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            // Input repetir contraseña
            OutlinedTextField(
                value = contrasenaRepetida,
                onValueChange = {
                    viewModel.onContrasenaRepetidaChanged(it)
                },
                label = { Text("Nueva Contraseña repetida") },
                isError = errorContrasenaRepetida.isNotEmpty(),
                placeholder = { Text(text = "Repite la nueva contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (estaVisibleContrasenaRepetida) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val imagenIcono =
                        if (estaVisibleContrasenaRepetida) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val descripcion =
                        if (estaVisibleContrasenaRepetida) "Ocultar contraseña" else "Mostrar contraseña"

                    IconButton(onClick = {
                        estaVisibleContrasenaRepetida = !estaVisibleContrasenaRepetida
                    }) {
                        Icon(imageVector = imagenIcono, contentDescription = descripcion)
                    }
                },
                supportingText = {
                    if (errorContrasenaRepetida.isNotEmpty()) {
                        Text(
                            text = errorContrasenaRepetida,
                            color = Color.Red
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = mostaza,
                    unfocusedIndicatorColor = mostaza,
                    focusedLabelColor = mostaza,
                    unfocusedLabelColor = mostaza,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    if(viewModel.validarFormulario()){
                        mostrarModal = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mostaza,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .padding(horizontal = 55.dp)
            ) {
                Text("Editar")
            }
        }
    }
}