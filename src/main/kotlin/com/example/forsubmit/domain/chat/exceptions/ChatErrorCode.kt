package com.example.forsubmit.domain.chat.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty
import com.example.forsubmit.global.exception.property.GlobalExceptionErrorCode

enum class ChatErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
) : ExceptionProperty {
    CHAT_NOT_FOUND(404, "Chat Not Found", "채팅을 찾을 수 없습니다.")
}