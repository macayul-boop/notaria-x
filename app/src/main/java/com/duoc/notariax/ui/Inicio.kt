package com.duoc.notariax.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.translationMatrix
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.cafeOscuroClaro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.InicioViewModel

@Composable
fun Inicio(
    onCerrarSesion: () -> Unit,
    navegacion: NavController,
    viewModel: InicioViewModel
){

    val nombreUsuario by viewModel.nombreUsuario.collectAsState()
    val listaTramite by viewModel.listaTramitesProgreso.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cafeOscuro)
            .padding(horizontal = 35.dp, vertical = 70.dp)
    ) {

        Text(
            text = "Bienvenido ${nombreUsuario ?: "Desconocido/a"}",
            color = mostaza,
            fontSize = 21.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {navegacion.navigate("seleccionar_tramite")},
            colors = ButtonDefaults.buttonColors(
                containerColor = mostaza,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 70.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "Documento"
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Tramite natorial",
                    fontSize = 18.sp
                )
            }

        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column() {
                Button(
                    onClick = { navegacion.navigate("tramites") },
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mostaza,
                        contentColor = Color.White
                    ),
                ){
                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Carpeta"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tramites",
                    fontSize = 14.sp,
                    color = mostaza
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {  },
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mostaza,
                        contentColor = Color.White
                    ),
                ){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Usuario"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Perfil",
                    fontSize = 14.sp,
                    color = mostaza
                )
            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Ultimos tramites", color = mostaza, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Column() {
                listaTramite.forEach { tramite ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cafeOscuroClaro)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .padding(horizontal = 25.dp, vertical = 25.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.7f)
                            ) {
                                Row() {
                                    Text(
                                        text = tramite.tramite.nombre,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = mostaza
                                    )
                                    Spacer(modifier = Modifier.width(15.dp))
                                    Text(
                                        text = tramite.progresoTramite.fechaFormateada,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Thin,
                                        color = mostaza
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = tramite.progresoTramite.estado.nombreDisplay,
                                    color = Color.White,
                                    modifier = Modifier
                                        .background(
                                            color = cafeOscuro,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.3f)
                            ) {
                                Button(
                                    onClick = {navegacion.navigate("progreso_tramite/${tramite.progresoTramite.id}")},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = mostaza,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(text = "Ver detalles")
                                }
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        Button(onClick = {onCerrarSesion()}) {
            Text(text = "Cerrar Sesion")
        }
    }


}