package com.example.amigovirtual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amigovirtual.databinding.ActivityChatBinding

class ActivityChat : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val messages = mutableListOf<Message>()  // Lista de mensajes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.messageList.layoutManager = LinearLayoutManager(this)
        val adapter = MessageAdapter(messages)
        binding.messageList.adapter = adapter

        // Simulación de mensajes
        messages.add(Message("Hola, ¿cómo estás?", isUser = true))
        messages.add(Message("¡Hola! Estoy bien, ¿y tú?", isUser = false))
        messages.add(Message("¿Qué puedes hacer?", isUser = true))
        adapter.notifyDataSetChanged()

        // Botón para enviar un mensaje
        binding.sendButton.setOnClickListener {
            val content = binding.messageInput.text.toString()
            if (content.isNotEmpty()) {
                messages.add(Message(content, isUser = true)) // Mensaje del usuario
                adapter.notifyItemInserted(messages.size - 1)
                binding.messageInput.text.clear()

                // Simular respuesta del bot
                messages.add(Message("Soy AMIKO, tu asistente virtual.", isUser = false))
                adapter.notifyItemInserted(messages.size - 1)
                binding.messageList.scrollToPosition(messages.size - 1)
            }
        }
    }
}

