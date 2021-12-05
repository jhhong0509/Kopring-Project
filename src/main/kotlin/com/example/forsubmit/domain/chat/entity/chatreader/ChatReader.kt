package com.example.forsubmit.domain.chat.entity.chatreader

import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
class ChatReader(
    @EmbeddedId
    val chatReaderId: ChatReaderId
)