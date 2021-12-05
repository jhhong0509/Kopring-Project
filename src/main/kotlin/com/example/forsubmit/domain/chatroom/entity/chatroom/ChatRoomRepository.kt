package com.example.forsubmit.domain.chatroom.entity.chatroom

import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
}