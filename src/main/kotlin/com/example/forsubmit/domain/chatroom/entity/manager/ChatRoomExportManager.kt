package com.example.forsubmit.domain.chatroom.entity.manager

import com.example.forsubmit.domain.chatroom.entity.ChatRoom
import com.example.forsubmit.domain.chatroom.entity.ChatRoomRepository
import com.example.forsubmit.domain.chatroom.exceptions.ChatRoomNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ChatRoomExportManager(
    private val chatRoomRepository: ChatRoomRepository
) {
    fun findById(id: Long): ChatRoom {
        return chatRoomRepository.findByIdOrNull(id) ?: throw ChatRoomNotFoundException.EXCEPTION
    }
}