package com.example.amigovirtual

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ActivityCarga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga)
        FirebaseAuth.getInstance().signOut()

        // Simula un pequeño retraso para la carga (puedes usar animaciones en lugar de esto)
        Thread.sleep(2000)

        // Verifica si el usuario está autenticado
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // Usuario autenticado, navega a la actividad de chat
            startActivity(Intent(this, ActivityChat::class.java))
        } else {
            // Usuario no autenticado, navega a la actividad de login
            startActivity(Intent(this, ActivityLogin::class.java))
        }

        // Finaliza esta actividad
        finish()
    }
}
