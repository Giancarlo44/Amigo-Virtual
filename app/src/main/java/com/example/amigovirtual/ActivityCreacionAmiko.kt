package com.example.amigovirtual

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityCreacionAmiko : AppCompatActivity() {
    private lateinit var inputName: EditText
    private lateinit var inputPersonality: EditText
    private lateinit var inputDescription: EditText
    private lateinit var inputSalutation: EditText
    private lateinit var inputGender: EditText
    private lateinit var inputInterests: EditText
    private lateinit var buttonContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creacion_amiko)

        inputName = findViewById(R.id.editName)
        inputPersonality = findViewById(R.id.editPersonality)
        inputDescription = findViewById(R.id.editDescription)
        inputSalutation = findViewById(R.id.editSalutation)
        inputGender = findViewById(R.id.editGender)
        inputInterests = findViewById(R.id.editInterests)
        buttonContinue = findViewById(R.id.button_continue)

        buttonContinue.setOnClickListener { saveAmikoSettings() }
    }

    private fun saveAmikoSettings() {
        val name = inputName.text.toString().trim()
        val personality = inputPersonality.text.toString().trim()
        val description = inputDescription.text.toString().trim()
        val salutation = inputSalutation.text.toString().trim()
        val gender = inputGender.text.toString().trim()
        val interests = inputInterests.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val amiko = Amiko(name, personality, description, salutation, gender, interests)
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("amiko").child(userId)
                .setValue(amiko)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, ActivityChat::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error al guardar la configuración de AMIKO", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    data class Amiko(
        val name: String,
        val personality: String,
        val description: String,
        val salutation: String,
        val gender: String,
        val interests: String
    )
}
