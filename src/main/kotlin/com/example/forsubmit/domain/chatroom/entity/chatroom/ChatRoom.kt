package com.example.forsubmit.domain.chatroom.entity.chatroom

import com.example.forsubmit.domain.chat.entity.chat.Chat
import com.example.forsubmit.domain.chatroom.entity.member.ChatRoomMember
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table(name = "chatroom")
class ChatRoom(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotNull
    val name: String,

    @OneToMany(mappedBy = "chatRoomMemberId.chatRoom", cascade = [CascadeType.REMOVE])
    val chatRoomMember: MutableList<ChatRoomMember> = mutableListOf(),

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val chat: MutableList<Chat> = mutableListOf()

)