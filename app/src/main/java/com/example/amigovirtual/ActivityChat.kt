package com.example.amigovirtual

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityChat : AppCompatActivity() {
    private lateinit var messageList: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatGPTApi: ChatGPTApi
    private val messages = mutableListOf<ChatRequest.Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageList = findViewById(R.id.message_list)
        messageInput = findViewById(R.id.message_input)
        sendButton = findViewById(R.id.send_button)

        val gson: Gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        chatGPTApi = retrofit.create(ChatGPTApi::class.java)

        messageList.layoutManager = LinearLayoutManager(this)
        val adapter = MessageAdapter(messages)
        messageList.adapter = adapter

        sendButton.setOnClickListener {
            sendMessage(adapter)
        }
    }

    private fun sendMessage(adapter: MessageAdapter) {
        val content = messageInput.text.toString().trim()
        if (content.isEmpty()) {
            Toast.makeText(this, "Escribe un mensaje", Toast.LENGTH_SHORT).show()
            return
        }

        messages.add(ChatRequest.Message("user", content))
        adapter.notifyItemInserted(messages.size - 1)

        val request = ChatRequest(messages)

        chatGPTApi.sendMessage(request).enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val reply = response.body()!!.choices[0].message.content
                    messages.add(ChatRequest.Message("assistant", reply))
                    adapter.notifyItemInserted(messages.size - 1)
                    messageInput.text.clear()
                } else {
                    Toast.makeText(this@ActivityChat, "Error en la respuesta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Toast.makeText(this@ActivityChat, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
