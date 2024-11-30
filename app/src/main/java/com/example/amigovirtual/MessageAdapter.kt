package com.example.amigovirtual

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message_bot.view.*
import kotlinx.android.synthetic.main.item_message_user.view.*

class MessageAdapter(private val messages: List<ChatRequest.Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_user, parent, false)
            UserMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_bot, parent, false)
            BotMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is UserMessageViewHolder) {
            holder.bind(message)
        } else if (holder is BotMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].role == "user") 1 else 0
    }

    inner class UserMessageViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatRequest.Message) {
            itemView.message_text.text = message.content
        }
    }

    inner class BotMessageViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatRequest.Message) {
            itemView.message_text.text = message.content
        }
    }
}
