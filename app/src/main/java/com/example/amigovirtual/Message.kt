package com.example.amigovirtual

data class Message(
    val content: String, // Contenido del mensaje
    val isUser: Boolean  // `true` si es del usuario, `false` si es del bot
)
