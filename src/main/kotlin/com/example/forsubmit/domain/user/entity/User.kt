package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.domain.chat.entity.chat.Chat
import com.example.forsubmit.domain.chat.entity.chatreader.ChatReader
import com.example.forsubmit.domain.chatroom.entity.member.ChatRoomMember
import org.hibernate.annotations.NaturalId
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NaturalId
    val email: String,

    @NotNull
    val name: String,

    @NotNull
    val password: String,

    @OneToMany(mappedBy = "user")
    val chat: MutableList<Chat> = mutableListOf(),

    @OneToMany(mappedBy = "chatReaderId.user")
    val chatReader: MutableList<ChatReader> = mutableListOf(),

    @OneToMany(mappedBy = "chatRoomMemberId.user")
    val chatRoomMember: MutableList<ChatRoomMember> = mutableListOf()
)