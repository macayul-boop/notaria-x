package com.duoc.notariax.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.duoc.notariax.R
import com.duoc.notariax.model.Usuario
import com.duoc.notariax.viewmodel.InicioSesionViewModel

@Composable
fun InicioSesion(viewModel: InicioSesionViewModel){

    val email by viewModel.email.collectAsState()
    val contrasena by viewModel.contrasena.collectAsState()

    val errorEmail by viewModel.errorEmail.collectAsState()
    val errorContrasena by viewModel.errorContrasena.collectAsState()
    val errorLogin by viewModel.errorLogin.collectAsState()

    var estaVisibleContrasena by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo notaria-x",
            modifier = Modifier
                .width(250.dp)
                .height(175.dp)
        )

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

        Text(text = errorLogin)
        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                viewModel.validarLogin()
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
            Text("Iniciar Sesion")
        }
    }

}