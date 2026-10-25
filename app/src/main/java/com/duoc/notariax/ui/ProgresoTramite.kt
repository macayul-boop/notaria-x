package com.duoc.notariax.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariax.model.ProgresoTramite
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.cafeOscuroClaro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.ProgresoTramiteViewModel

@Composable
fun ProgresoTramite(
    viewModel: ProgresoTramiteViewModel,
    onRegresar: () -> Unit
){

    val progresoTramite = viewModel.pogresoConTramite

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cafeOscuroClaro)
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

        if(progresoTramite != null){

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = progresoTramite.tramite.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = mostaza
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = progresoTramite.progresoTramite.fechaFormateada,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Thin,
                    color = mostaza
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = progresoTramite.progresoTramite.estado.nombreDisplay,
                color = Color.White,
                modifier = Modifier
                    .background(
                        color = cafeOscuro,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Historial del Trámite",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp),
                color = mostaza
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn() {
                itemsIndexed(progresoTramite.progresoTramite.historial) { index, evento ->
                    val esUltimo = index == progresoTramite.progresoTramite.historial.lastIndex
                    LineaDeTiempo(
                        evento = evento,
                        esElUltimo = esUltimo
                    )
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