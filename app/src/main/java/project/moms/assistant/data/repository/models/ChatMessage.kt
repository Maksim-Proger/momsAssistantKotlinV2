package project.moms.assistant.data.repository.models

data class ChatMessage(
    val id: String,
    val message: String,
    val userMessage: Boolean,
    val isEmptyLine: Boolean
)
