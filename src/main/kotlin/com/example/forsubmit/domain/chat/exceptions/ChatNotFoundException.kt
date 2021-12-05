package com.example.forsubmit.domain.chat.exceptions

import com.example.forsubmit.global.exception.GlobalException

class ChatNotFoundException private constructor() : GlobalException(ChatErrorCode.CHAT_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = ChatNotFoundException()
    }
}