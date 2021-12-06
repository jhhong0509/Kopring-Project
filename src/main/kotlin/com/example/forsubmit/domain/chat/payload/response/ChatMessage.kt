package com.example.forsubmit.domain.chat.payload.response

class ChatMessage(
    val content: String,
    val isMine: Boolean,
    val senderName: String,
    val senderId: Long
)