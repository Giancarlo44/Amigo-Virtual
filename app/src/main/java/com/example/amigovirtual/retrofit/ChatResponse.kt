package com.example.amigovirtual.retrofit

data class ChatResponse(val choices: List<Choice>) {
    data class Choice(val message: Message) {
        data class Message(val role: String, val content: String)
    }
}
