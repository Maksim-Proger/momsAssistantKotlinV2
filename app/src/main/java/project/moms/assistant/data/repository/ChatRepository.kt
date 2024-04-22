package project.moms.assistant.data.repository

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import project.moms.assistant.data.repository.models.ChatMessage
import project.moms.assistant.presentation.LogicAssistant
import java.util.UUID

class ChatRepository(private val context: Context) {
    private val chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())

    fun getChatMessagesFlow(): Flow<List<ChatMessage>> = chatMessages

    fun addUserMessage(messageText: String) {
        val userMessageId = UUID.randomUUID().toString()
        val userMessage = ChatMessage(userMessageId, messageText, true, false)

        val botMessage = generateBotResponse(messageText)
        val botMessageId = UUID.randomUUID().toString()
        val emptyLineId = UUID.randomUUID().toString()

        chatMessages.value =
            listOf(userMessage,
                ChatMessage(emptyLineId, "", false, true), botMessage,
                ChatMessage(botMessageId, "", false, true))
    }

    private fun generateBotResponse(userMessage: String): ChatMessage {
        val logicAssistant = LogicAssistant()
        val assistantResponse = logicAssistant.assistantMethod(context, userMessage)
        val messageId = UUID.randomUUID().toString()
        return ChatMessage(messageId, assistantResponse, false, false)
    }
}




