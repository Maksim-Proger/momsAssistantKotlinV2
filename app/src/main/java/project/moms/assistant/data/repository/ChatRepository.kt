package project.moms.assistant.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import project.moms.assistant.data.repository.models.ChatMessage
import project.moms.assistant.presentation.LogicAssistant
import java.util.UUID

class ChatRepository(private val context: Context) {
    private val chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())

    fun getChatMessagesFlow(): Flow<List<ChatMessage>> = chatMessages

    fun addUserMessage(messageText: String) {
        val currentMessages = chatMessages.value.toMutableList() // Получаем текущий список сообщений и превращаем его в MutableList
        val userMessageId = UUID.randomUUID().toString()
        val userMessage = ChatMessage(userMessageId, messageText, true, false)

        val botMessage = generateBotResponse(messageText)
        val emptyLineId = UUID.randomUUID().toString()

        // Добавляем новые сообщения в список
        currentMessages += userMessage
        currentMessages += ChatMessage(emptyLineId, "", false, true)
        currentMessages += botMessage
        currentMessages += ChatMessage(emptyLineId, "", false, true)

        chatMessages.value = currentMessages // Обновляем значение StateFlow новым списком сообщений
    }


    private fun generateBotResponse(userMessage: String): ChatMessage {
        val logicAssistant = LogicAssistant()
        val assistantResponse = logicAssistant.assistantMethod(context, userMessage)
        val messageId = UUID.randomUUID().toString()
        return ChatMessage(messageId, assistantResponse, false, false)
    }
}




