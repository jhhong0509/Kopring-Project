package com.example.forsubmit.domain.chatroom.exceptions

import com.example.forsubmit.global.exception.GlobalException

class ChatRoomNotFoundException private constructor() : GlobalException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = ChatRoomNotFoundException()
    }
}