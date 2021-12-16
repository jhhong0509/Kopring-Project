package com.example.forsubmit.domain.chatroom.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty

enum class ChatRoomErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
) : ExceptionProperty {
    CHAT_ROOM_NOT_FOUND(404, "Chat Room Not Found", "채팅방을 찾을 수 없습니다."),
    CANNOT_UPDATE_ROOM(403, "User Cannot Delete ChatRoom", "사용자가 채팅방을 지울 권한이 없습니다.")
}