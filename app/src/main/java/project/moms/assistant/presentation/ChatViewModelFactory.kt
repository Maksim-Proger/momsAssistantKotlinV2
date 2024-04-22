package project.moms.assistant.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.moms.assistant.data.repository.ChatRepository
import project.moms.assistant.presentation.viewModels.ChatViewModel

class ChatViewModelFactory(private val repository: ChatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


