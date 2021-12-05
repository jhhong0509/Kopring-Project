package com.example.forsubmit.domain.chatroom.entity.chatroom.manager

import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoomRepository
import com.example.forsubmit.domain.chatroom.exceptions.ChatRoomNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ChatRoomRepositoryManager(
    private val chatRoomRepository: ChatRoomRepository
) {
    fun findById(id: Long): ChatRoom {
        return chatRoomRepository.findByIdOrNull(id) ?: throw ChatRoomNotFoundException.EXCEPTION
    }
}