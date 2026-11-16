package com.duoc.notariax.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.utils.SessionManager
import com.duoc.notariax.viewmodel.InicioNotarioViewModel
import com.duoc.notariax.viewmodel.InicioViewModel

@Composable
fun inicioNotario(
    viewModel: InicioNotarioViewModel,
    navController: NavController,
    onCerrarSesion: () -> Unit,
){

    val nombreUsuario by viewModel.nombreUsuario.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cafeOscuro)
            .padding(horizontal = 25.dp, vertical = 100.dp)
    ){
        Text(
            text = "Bienvenido",
            color = mostaza,
            fontSize = 21.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "${nombreUsuario ?: "Desconocido/a"}",
            color = mostaza,
            fontSize = 21.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController.navigate("tomar_tramite") },
            colors = ButtonDefaults.buttonColors(
                containerColor = mostaza,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 80.dp)
        ) {
            Text(
                text = "Tomar tramites",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = mostaza,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 80.dp)
        ) {
            Text(
                text = "Revisar Tramites",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = { onCerrarSesion() },
            border = BorderStroke(2.dp, mostaza),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = mostaza
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
        ){ Text(text = "Cerrar Sesion") }

    }
}