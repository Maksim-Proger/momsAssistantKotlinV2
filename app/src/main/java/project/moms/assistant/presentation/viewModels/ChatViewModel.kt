package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.room.ChatRepository
import project.moms.assistant.data.models.ChatMessageModel

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {
    fun getChatMessagesFlow(): Flow<List<ChatMessageModel>> = repository.getChatMessagesFlow()

    fun sendMessage(messageText: String) {
        viewModelScope.launch {
            repository.addUserMessage(messageText)
        }
    }
}