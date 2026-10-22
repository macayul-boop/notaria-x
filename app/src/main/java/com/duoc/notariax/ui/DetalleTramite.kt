package com.duoc.notariax.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.DetalleTramiteViewModel

@Composable
fun DetalleTramite(
    onRegresar: ()->Unit,
    onTerminar: () -> Unit,
    viewmodel: DetalleTramiteViewModel
){

    val tramite = viewmodel.tramite

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cafeOscuro)
            .padding(horizontal = 35.dp, vertical = 70.dp)
    ){
        Row() {
            IconButton(
                onClick = {onRegresar()}
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Retroceder",
                    modifier = Modifier.size(35.dp),
                    tint = mostaza
                )
            }
        }

        Spacer(Modifier.height(25.dp))

        if(tramite != null){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = tramite.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = mostaza
                )

                Spacer(Modifier.height(10.dp))

                Text(text = tramite.descripcion, color = mostaza)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Spacer(Modifier.height(10.dp))
                    Text(text = "Documentos requeridos", fontSize = 18.sp, color = mostaza, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))

                    tramite.documentosRequeridos.forEach { documento ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Text(text = "• ", fontSize = 18.sp, color = mostaza)
                            Text(text = documento, fontSize = 16.sp, color = mostaza)
                        }
                    }
                }
            }


        }else{
            Text(text = "Cargando informacion del tramite...")
        }
    }

}