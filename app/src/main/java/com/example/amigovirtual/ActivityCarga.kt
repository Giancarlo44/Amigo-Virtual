package com.example.amigovirtual

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ActivityCarga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga)

        // Verificar si el usuario está autenticado
        val currentUser = FirebaseAuth.getInstance().currentUser

        // Simula un pequeño retraso para que se vea la carga
        Thread.sleep(2000)  // 2 segundos de espera (puedes ajustar)

        if (currentUser != null) {
            // Si el usuario está autenticado, navegar a ActivityChat
            startActivity(Intent(this, ActivityChat::class.java))
        } else {
            // Si no está autenticado, navegar a ActivityLogin
            startActivity(Intent(this, ActivityLogin::class.java))
        }

        // Cerrar ActivityCarga para que no se quede en el stack
        finish()
    }
}
