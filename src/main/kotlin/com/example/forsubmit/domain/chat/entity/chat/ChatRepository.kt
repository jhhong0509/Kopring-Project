package com.example.forsubmit.domain.chat.entity.chat

import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, Long> {
}