package com.example.forsubmit.domain.chat.entity.chat

import com.example.forsubmit.domain.chatroom.entity.ChatRoom
import com.example.forsubmit.domain.user.entity.User
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Chat(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @NotNull
    val message: String,

    @CreatedDate
    @NotNull
    val createdAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    val chatRoom: ChatRoom,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    val user: User,

)