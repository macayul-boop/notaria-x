package com.duoc.notariax.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.viewmodel.UsuarioViewModel

@Composable
fun FormularioUsuari(viewModel: UsuarioViewModel){

    val nombreCompleto by viewModel.nombreCompleto.collectAsState()
    val rut by viewModel.rut.collectAsState()
    val email by viewModel.email.collectAsState()
    val contrasena by viewModel.contrasena.collectAsState()
    val contrasenaRepetida by viewModel.contrasenaRepetida.collectAsState()

    val errorNombre by viewModel.errorNombre.collectAsState()
    val errorRut by viewModel.errorRut.collectAsState()
    val errorEmail by viewModel.errorEmail.collectAsState()
    val errorContrasena by viewModel.errorContrasena.collectAsState()
    val errorContrasenaRepetida by viewModel.errorContrasenaRepetida.collectAsState()

    var estaVisibleContrasena by remember { mutableStateOf(false) }
    var estaVisibleContrasenaRepetida by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.fillMaxSize(),
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
                text = "Crear cuenta",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Input de nombre
        OutlinedTextField(
            value = nombreCompleto,
            onValueChange = {
                viewModel.nombreCompleto.value = it
                if(viewModel.errorNombre.value.isNotEmpty()){
                    viewModel.limpiarErrorNombre()
                }
            },
            label = { Text("Nombre Completo") },
            isError = errorNombre.isNotEmpty(),

            supportingText = {
                if (errorNombre.isNotEmpty()) {
                    Text(
                        text = errorNombre,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp)
        )

        // Input de Rut
        OutlinedTextField(
            value = rut,
            onValueChange = {
                viewModel.rut.value = it
                if(viewModel.errorRut.value.isNotEmpty()){
                    viewModel.limpiarErrorRut()
                }
            },
            label = { Text("Rut") },
            isError = errorRut.isNotEmpty(),
            placeholder = {Text(text = "12.345.678-9")},
            supportingText = {
                if (errorRut.isNotEmpty()) {
                    Text(
                        text = errorRut,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp)
        )

        // Input de email
        OutlinedTextField(
            value = email,
            onValueChange = {
                viewModel.email.value = it
                if(viewModel.errorEmail.value.isNotEmpty()){
                    viewModel.limpiarErrorEmail()
                }
            },
            label = { Text("Email") },
            isError = errorEmail.isNotEmpty(),
            placeholder = {Text(text = "ejemplo@gmail.com")},
            supportingText = {
                if (errorEmail.isNotEmpty()) {
                    Text(
                        text = errorEmail,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp)
        )

        // Input de contraseña
        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                viewModel.contrasena.value = it
                if(viewModel.errorContrasena.value.isNotEmpty()){
                    viewModel.limpiarErrorContrasena()
                }
            },
            label = { Text("Contraseña") },
            isError = errorContrasena.isNotEmpty(),
            placeholder = {Text(text = "Ingresa tu contraseña")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (estaVisibleContrasena) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val imagenIcono = if (estaVisibleContrasena) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val descripcion = if (estaVisibleContrasena) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { estaVisibleContrasena = !estaVisibleContrasena }) {
                    Icon(imageVector = imagenIcono, contentDescription = descripcion)
                }
            },
            supportingText = {
                if (errorContrasena.isNotEmpty()) {
                    Text(
                        text = errorContrasena,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp)
        )

        // Input repetir contraseña
        OutlinedTextField(
            value = contrasenaRepetida,
            onValueChange = {
                viewModel.contrasenaRepetida.value = it
                if(viewModel.errorContrasenaRepetida.value.isNotEmpty()){
                    viewModel.limpiarErrorContrasenaRepetida()
                }
            },
            label = { Text("Contraseña repetida") },
            isError = errorContrasenaRepetida.isNotEmpty(),
            placeholder = {Text(text = "Repite la contraseña")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (estaVisibleContrasenaRepetida) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val imagenIcono = if (estaVisibleContrasenaRepetida) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val descripcion = if (estaVisibleContrasenaRepetida) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { estaVisibleContrasenaRepetida = !estaVisibleContrasenaRepetida }) {
                    Icon(imageVector = imagenIcono, contentDescription = descripcion)
                }
            },
            supportingText = {
                if (errorContrasenaRepetida.isNotEmpty()) {
                    Text(
                        text = errorContrasenaRepetida,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                viewModel.crearUsuario(Usuario(nombreCompleto = nombreCompleto, rut = rut, email = email, contrasena = contrasena,))
            },
            // Colores
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            // Border radius
            shape = RoundedCornerShape(8.dp),
            // Tamaño
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .padding(horizontal = 55.dp)
        ) {
            Text("Registrarse")
        }
    }



}