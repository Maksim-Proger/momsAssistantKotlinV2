package project.moms.assistant.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.moms.assistant.R
import project.moms.assistant.data.repository.models.ChatMessage

class ChatAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(MessageDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            USER_MESSAGE -> {
                val userMessageView = inflater.inflate(R.layout.user_message, parent, false)
                UserMessageViewHolder(userMessageView)
            }
            ASSISTANT_MESSAGE -> {
                val assistantMessageView = inflater.inflate(R.layout.assistant_message, parent, false)
                AssistantMessageViewHolder(assistantMessageView)
            }
            EMPTY_LINE_MESSAGE -> {
                val emptyLineView = inflater.inflate(R.layout.empty_line, parent, false)
                EmptyLineViewHolder(emptyLineView)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = getItem(position)

        when (holder) {
            is UserMessageViewHolder -> holder.bind(chatMessage)
            is AssistantMessageViewHolder -> holder.bind(chatMessage)
            is EmptyLineViewHolder -> holder.bind(chatMessage)
        }
    }
    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return when {
            message.userMessage -> USER_MESSAGE
            message.isEmptyLine -> EMPTY_LINE_MESSAGE
            else -> ASSISTANT_MESSAGE
        }
    }
    class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userMessageText: TextView = itemView.findViewById(R.id.userMessageText)
        fun bind(chatMessage: ChatMessage) {
            userMessageText.text = chatMessage.message
        }
    }
    class AssistantMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val assistantMessageText: TextView = itemView.findViewById(R.id.assistantMessageText)
        fun bind(chatMessage: ChatMessage) {
            assistantMessageText.text = chatMessage.message
        }
    }
    class EmptyLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val emptyLine: TextView = itemView.findViewById(R.id.emptyLine)
        fun bind(chatMessage: ChatMessage) {
            emptyLine.text = chatMessage.message
            emptyLine.setTextColor(Color.GRAY)
        }
    }
    companion object {
        private const val USER_MESSAGE = 1
        private const val ASSISTANT_MESSAGE = 2
        private const val EMPTY_LINE_MESSAGE = 3
    }
}
class MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem == newItem
    }
}






