package com.duoc.notariax.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE)

    companion object {
        private const val llave_id = "id_usuario_activo"
    }

    fun guardarSesion(id: Int) {
        val editor = preferences.edit()
        editor.putString(llave_id, id.toString())
        editor.apply()
    }

    fun obtenerIdSesion(): String? {
        return preferences.getString(llave_id, null)
    }

    fun cerrarSesion() {
        val editor = preferences.edit()
        editor.remove(llave_id)
        editor.apply()
    }

}