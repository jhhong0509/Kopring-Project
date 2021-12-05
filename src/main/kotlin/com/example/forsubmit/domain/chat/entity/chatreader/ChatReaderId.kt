package com.example.forsubmit.domain.chat.entity.chatreader

import com.example.forsubmit.domain.chat.entity.chat.Chat
import com.example.forsubmit.domain.user.entity.User
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
class ChatReaderId(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id")
    val user: User,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    val chat: Chat
) : Serializable