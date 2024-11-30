package com.example.amigovirtual

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityInformacion : AppCompatActivity() {
    private lateinit var userName: TextView
    private lateinit var userAge: TextView
    private lateinit var userGender: TextView
    private lateinit var amikoName: TextView
    private lateinit var amikoPersonality: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion)

        userName = findViewById(R.id.input_username)
        userAge = findViewById(R.id.input_age)
        userGender = findViewById(R.id.input_gender)
        amikoName = findViewById(R.id.karen_ramirez)
        amikoPersonality = findViewById(R.id.editPersonality)

        loadUserInfo()
        loadAmikoInfo()
    }

    private fun loadUserInfo() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            FirebaseDatabase.getInstance().getReference("users").child(it).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result.exists()) {
                        userName.text = task.result.child("username").value.toString()
                        userAge.text = task.result.child("age").value.toString()
                        userGender.text = task.result.child("gender").value.toString()
                    }
                }
        }
    }

    private fun loadAmikoInfo() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            FirebaseDatabase.getInstance().getReference("amiko").child(it).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result.exists()) {
                        amikoName.text = task.result.child("name").value.toString()
                        amikoPersonality.text = task.result.child("personality").value.toString()
                    }
                }
        }
    }
}
