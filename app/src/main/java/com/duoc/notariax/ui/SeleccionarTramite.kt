package com.duoc.notariax.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.SeleccionarTramiteViewModel

@Composable
fun SeleccionarTramite(
    onContinuar: (String) -> Unit,
    onRegresar: () -> Unit,
    viewModel: SeleccionarTramiteViewModel
){

    val listaTramites by viewModel.listaTramites.collectAsState()
    val seleccionadoId by viewModel.tramiteSeleccionado.collectAsState()
    val esValido by viewModel.esValido.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cafeOscuro)
            .padding(horizontal = 35.dp, vertical = 70.dp)
    ) {

        Row() {
            IconButton(
                onClick = {onRegresar()}
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Retroceder",
                    modifier = Modifier.size(35.dp),
                    tint = mostaza,

                )
            }
        }

        Spacer(Modifier.height(50.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Tipos de tramites",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = mostaza
            )

            Spacer(Modifier.height(10.dp))

            Text(text = "Selecciona el tipo de tramite que neceistas realizar", color = mostaza)
        }

        Spacer(Modifier.height(20.dp))

        Column() {
            listaTramites.forEach { tramite ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tramite.id == seleccionadoId,
                        onCheckedChange = {
                            viewModel.seleccionarTramite(tramite.id)
                        }

                    )

                    Text(
                        text = tramite.nombre,
                        modifier = Modifier.padding(start = 8.dp),
                        color = mostaza
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if(esValido.isNotEmpty()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {Text(text = esValido, color = Color.Red) }
        }

        Button(
            onClick = {
                viewModel.validar()
                if(viewModel.tramiteSeleccionado.value != null){
                    onContinuar(seleccionadoId.toString())
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
        ) { Text(text = "Continuar") }

    }

}