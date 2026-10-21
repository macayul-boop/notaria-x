package com.duoc.notariax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.duoc.notariax.utils.SessionManager
import com.duoc.notariax.ui.theme.NotariaXTheme
import kotlin.getValue
import com.duoc.notariax.model.AppDatabase
import com.duoc.notariax.repository.UsuarioRepository
import com.duoc.notariax.ui.InicioSesion
import com.duoc.notariax.ui.Navegacion
import com.duoc.notariax.viewmodel.InicioSesionViewModel
import com.duoc.notariax.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "usuarios_db"
        ).build()
    }

    private val sessionManager by lazy { SessionManager(applicationContext) }
    private val repository by lazy { UsuarioRepository(db.usuarioDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotariaXTheme {
                //FormularioUsuari(viewModelUsuario)
                //InicioSesion(viewModelInicioSesion)
                Navegacion(sessionManager, repository)
            }
        }
    }
}
