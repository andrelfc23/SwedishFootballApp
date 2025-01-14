package com.abav.footballfranzy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abav.footballfranzy.APIFootball.ChatMessage

class ChatAdapter(
    private val chatMessages: List<ChatMessage>,
    private val currentUserId: String // Unique ID for the current user
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // ViewHolder for Sender
    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.message_text)
        val senderTextView: TextView = itemView.findViewById(R.id.sender_name)
        val timeTextView: TextView = itemView.findViewById(R.id.time_text)
    }

    // ViewHolder for Receiver
    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.message_text)
        val senderTextView: TextView = itemView.findViewById(R.id.sender_name)
        val timeTextView: TextView = itemView.findViewById(R.id.time_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENDER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_layout, parent, false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_receiver_layout, parent, false)
            ReceiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = chatMessages[position]

        if (holder is SenderViewHolder) {
            holder.messageTextView.text = chatMessage.message
            holder.senderTextView.text = chatMessage.sender
            holder.timeTextView.text = chatMessage.time
        } else if (holder is ReceiverViewHolder) {
            holder.messageTextView.text = chatMessage.message
            holder.senderTextView.text = chatMessage.sender
            holder.timeTextView.text = chatMessage.time
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = chatMessages[position]
        Log.d("CurrentUserId", currentUserId.toString())
        return if (chatMessage.id == currentUserId) VIEW_TYPE_SENDER else VIEW_TYPE_RECEIVER
    }

    override fun getItemCount(): Int = chatMessages.size

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }
}
