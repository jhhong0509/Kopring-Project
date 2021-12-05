package com.example.forsubmit.domain.chatroom.entity.member

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.validation.constraints.NotBlank

@Entity
class ChatRoomMember(
    @EmbeddedId
    val chatRoomMemberId: ChatRoomMemberId,

    @NotBlank
    val isAdmin: Boolean = false
)