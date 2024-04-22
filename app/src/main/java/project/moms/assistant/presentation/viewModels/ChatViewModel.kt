package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.ChatRepository
import project.moms.assistant.data.repository.models.ChatMessage

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {
    fun getChatMessagesFlow(): Flow<List<ChatMessage>> = repository.getChatMessagesFlow()

    fun sendMessage(messageText: String) {
        viewModelScope.launch {
            repository.addUserMessage(messageText)

        }
    }
}