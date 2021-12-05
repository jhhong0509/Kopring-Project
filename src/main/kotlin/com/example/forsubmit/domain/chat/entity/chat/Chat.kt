package com.example.forsubmit.domain.chat.entity.chat

import com.example.forsubmit.domain.chat.entity.chatreader.ChatReader
import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
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
    val id: Long = 0,

    @NotNull
    val content: String,

    @CreatedDate
    @NotNull
    val createdAt: LocalDateTime? = null,

    @NotNull
    val type: ChatType,

    @NotNull
    var readerCount: Int = 0,

    @OneToMany(mappedBy = "chatReaderId.chat")
    val chatReader: MutableList<ChatReader> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    val chatRoom: ChatRoom,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    val user: User

) {
    fun addReaderCount() {
        readerCount++
    }
}