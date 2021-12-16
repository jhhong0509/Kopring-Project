package com.example.forsubmit.domain.chatroom.exceptions

import com.example.forsubmit.global.exception.GlobalException

class CannotUpdateChatRoomException private constructor() : GlobalException(ChatRoomErrorCode.CANNOT_UPDATE_ROOM) {
    companion object {
        @JvmField
        val EXCEPTION = CannotUpdateChatRoomException()
    }
}