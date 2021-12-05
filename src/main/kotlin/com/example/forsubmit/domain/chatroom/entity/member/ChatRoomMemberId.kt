package com.example.forsubmit.domain.chatroom.entity.member

import com.example.forsubmit.domain.chatroom.entity.chatroom.ChatRoom
import com.example.forsubmit.domain.user.entity.User
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
class ChatRoomMemberId(
    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    val chatRoom: ChatRoom,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) : Serializable