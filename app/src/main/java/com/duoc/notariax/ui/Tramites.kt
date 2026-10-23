package com.duoc.notariax.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.duoc.notariax.ui.theme.cafeOscuroClaro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.TramitesViewModel

@Composable
fun Tramites(
    onContinuar: (String) -> Unit,
    onRegresar: () -> Unit,
    viewmodel: TramitesViewModel
){

    val listaTramites by viewmodel.listaTramites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cafeOscuro)
            .padding(horizontal = 25.dp, vertical = 70.dp)
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

        Spacer(Modifier.height(50.dp))

        Text(
            text = "Tramites",
            color = mostaza,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        LazyColumn(

        ){
            items(listaTramites){ tramite ->

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
                                onClick = {},
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

}