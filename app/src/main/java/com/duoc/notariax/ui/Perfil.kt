package com.duoc.notariax.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.PerfilViewModel


@Composable
fun Perfil(
    onCerrarSesion: () -> Unit,
    onRegresar: () -> Unit,
    navegacion: NavController,
    viewModel: PerfilViewModel
){
    val nombreUsuario by viewModel.nombreUsuario.collectAsState()
    val rut by viewModel.rut.collectAsState()
    val email by viewModel.email.collectAsState()
    val contrasena by viewModel.contrasena.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cafeOscuro)
            .padding(horizontal = 25.dp, vertical = 70.dp)
    ) {
        Row() {
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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onCerrarSesion() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mostaza,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cerrar Sesion")
            }
        }


        Spacer(Modifier.height(50.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { },
                shape = CircleShape,
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = mostaza,
                    contentColor = Color.White
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${nombreUsuario}",
                fontSize = 20.sp,
                color = mostaza
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = mostaza,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ){
                Column(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Nombre: ${nombreUsuario ?: "Cargando..."}",
                        fontSize = 20.sp,
                        color = mostaza
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "RUT: ${rut ?: "Cargando..."}",
                        fontSize = 20.sp,
                        color = mostaza
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Email: ${email ?: "Cargando..."}",
                        fontSize = 20.sp,
                        color = mostaza
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Contraseña: ${contrasena ?: "Cargando..."}",
                        fontSize = 20.sp,
                        color = mostaza
                    )
                }

                Button(
                    onClick = {navegacion.navigate("editar_perfil")},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mostaza,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(text = "Editar")
                }

            }
        }
    }
}