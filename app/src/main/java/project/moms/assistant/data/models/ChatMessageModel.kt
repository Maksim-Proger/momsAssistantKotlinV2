package project.moms.assistant.data.models

data class ChatMessageModel(
    val id: String,
    val message: String,
    val userMessage: Boolean,
    val isEmptyLine: Boolean
)
