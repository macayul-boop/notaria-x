package com.duoc.notariax.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.duoc.notariax.ui.theme.cafeOscuro

@Composable
fun Tramites(
    onContinuar: (String) -> Unit,
    onRegresar: () -> Unit,
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cafeOscuro)
            .padding(horizontal = 35.dp, vertical = 70.dp)
    ) {

    }

}