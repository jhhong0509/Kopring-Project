package com.example.forsubmit.domain.chatroom.entity.member

import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
class ChatRoomMember(
    @EmbeddedId
    val chatRoomMemberId: ChatRoomMemberId
)