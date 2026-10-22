package com.duoc.notariax.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariax.ui.theme.cafeOscuro
import com.duoc.notariax.ui.theme.mostaza
import com.duoc.notariax.viewmodel.DetalleTramiteViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetalleTramite(
    onRegresar: ()->Unit,
    onTerminar: () -> Unit,
    viewmodel: DetalleTramiteViewModel
){

    val tramite = viewmodel.tramite
    val listaArchivos by viewmodel.archivos.collectAsState()
    val esValido by viewmodel.esvalido.collectAsState()
    val erroArchivo by viewmodel.errorArchivo.collectAsState()
    var cargando by remember { mutableStateOf(false) }

    val selectorLauncherMúltiple = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        viewmodel.agregarArchivos(uris)
    }

    LaunchedEffect(esValido) {
        if (esValido) {
            onTerminar()
            viewmodel.cambiarEsValido()
        }
    }

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

            Column() {
                Spacer(Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {selectorLauncherMúltiple.launch("*/*")},
                    border = BorderStroke(2.dp, mostaza),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = mostaza
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 50.dp)
                ){ Text(text = "Subir archivos") }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(listaArchivos){ archivo ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = archivo.lastPathSegment ?: "Archivo seleccionado",
                                    modifier = Modifier.weight(1f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                IconButton(onClick = {viewmodel.eliminarArchivo(archivo)}) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar"
                                    )
                                }
                            }
                        }


                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                if(erroArchivo.isNotEmpty()){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) { Text(text = erroArchivo, color = Color.Red) }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        cargando = true
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            viewmodel.validar()
                            cargando = false
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
                ) {

                    if (cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = cafeOscuro
                        )
                    } else {
                        Text(text = "Realizar tramite")
                    }

                }

            }

        }else{
            Text(text = "Cargando informacion del tramite...")
        }
    }

}