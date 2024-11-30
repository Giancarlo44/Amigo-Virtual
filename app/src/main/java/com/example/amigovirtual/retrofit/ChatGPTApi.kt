package com.example.amigovirtual.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGPTApi {
    @Headers("Content-Type: application/json", "Authorization: api")  // Reemplaza con tu API Key
    @POST("v1/chat/completions")
    fun sendMessage(@Body request: ChatRequest): Call<ChatResponse>
}

