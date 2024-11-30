package com.example.amigovirtual

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityRegistro : AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputConfirmEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputConfirmPassword: EditText
    private lateinit var inputUsername: EditText
    private lateinit var inputAge: EditText
    private lateinit var inputGender: EditText
    private lateinit var buttonContinue: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        inputEmail = findViewById(R.id.input_email)
        inputConfirmEmail = findViewById(R.id.input_confirm_email)
        inputPassword = findViewById(R.id.input_password)
        inputConfirmPassword = findViewById(R.id.input_confirm_password)
        inputUsername = findViewById(R.id.input_username)
        inputAge = findViewById(R.id.input_age)
        inputGender = findViewById(R.id.input_gender)
        buttonContinue = findViewById(R.id.button_continue)
        auth = FirebaseAuth.getInstance()

        buttonContinue.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val email = inputEmail.text.toString().trim()
        val confirmEmail = inputConfirmEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()
        val confirmPassword = inputConfirmPassword.text.toString().trim()

        if (email != confirmEmail) {
            Toast.makeText(this, "Los correos electrónicos no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserInfo()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveUserInfo() {
        val username = inputUsername.text.toString().trim()
        val age = inputAge.text.toString().trim()
        val gender = inputGender.text.toString().trim()

        if (username.isEmpty() || age.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val user = User(username, age, gender)
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("users").child(userId)
                .setValue(user)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, ActivityCreacionAmiko::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error al guardar información", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    data class User(val username: String, val age: String, val gender: String)
}
