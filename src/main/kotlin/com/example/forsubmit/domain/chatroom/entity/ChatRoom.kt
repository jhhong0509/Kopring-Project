package com.example.forsubmit.domain.chatroom.entity

import com.example.forsubmit.domain.chat.entity.chat.Chat
import com.example.forsubmit.domain.user.entity.User
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class ChatRoom(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotNull
    val name: String,

    @ManyToOne
    @JoinColumn(name = "admin_id")
    val admin: User,

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val chatList: MutableList<Chat>

)