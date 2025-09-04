package project.moms.assistant.data.repository.room

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import project.moms.assistant.data.models.ChatMessageModel
import project.moms.assistant.presentation.unit.LogicAssistant
import java.util.UUID

class ChatRepository(private val context: Context) {
    private val chatMessagesModel = MutableStateFlow<List<ChatMessageModel>>(emptyList())

    fun getChatMessagesFlow(): Flow<List<ChatMessageModel>> = chatMessagesModel

    fun addUserMessage(messageText: String) {
        val currentMessages =
            chatMessagesModel.value.toMutableList() // Получаем текущий список сообщений и превращаем его в MutableList
        val userMessageId = UUID.randomUUID().toString()
        val userMessage = ChatMessageModel(userMessageId, messageText, true, false)

        val botMessage = generateBotResponse(messageText)
        val emptyLineId = UUID.randomUUID().toString()

        // Добавляем новые сообщения в список
        currentMessages += userMessage
        currentMessages += ChatMessageModel(emptyLineId, "", false, true)
        currentMessages += botMessage
        currentMessages += ChatMessageModel(emptyLineId, "", false, true)

        chatMessagesModel.value =
            currentMessages // Обновляем значение StateFlow новым списком сообщений
    }


    private fun generateBotResponse(userMessage: String): ChatMessageModel {
        val logicAssistant = LogicAssistant()
        val assistantResponse = logicAssistant.assistantMethod(context, userMessage)
        val messageId = UUID.randomUUID().toString()
        return ChatMessageModel(messageId, assistantResponse, false, false)
    }
}




