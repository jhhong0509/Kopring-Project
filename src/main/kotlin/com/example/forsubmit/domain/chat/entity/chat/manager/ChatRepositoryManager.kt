package com.example.forsubmit.domain.chat.entity.chat.manager

import com.example.forsubmit.domain.chat.entity.chat.Chat
import com.example.forsubmit.domain.chat.entity.chat.ChatRepository
import com.example.forsubmit.domain.chat.exceptions.ChatNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ChatRepositoryManager(
    private val chatRepository: ChatRepository
) {
    fun findById(id: Long): Chat {
        return chatRepository.findByIdOrNull(id) ?: throw ChatNotFoundException.EXCEPTION
    }

    fun save(chat: Chat): Chat {
        return chatRepository.save(chat)
    }
}