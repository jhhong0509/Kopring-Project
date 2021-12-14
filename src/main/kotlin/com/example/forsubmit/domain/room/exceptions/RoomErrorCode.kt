package com.example.forsubmit.domain.room.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty

enum class RoomErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
) : ExceptionProperty {
    ROOM_NOT_FOUND(404, "Room Not Found", "방을 찾을 수 없습니다."),
    CANNOT_DELETE_ROOM(403, "Invalid Access To Remove Room", "방을 삭제할 권한이 없습니다."),
    CANNOT_UPDATE_ROOM(403, "Invalid Access To Update Room", "방을 수정할 권한이 없습니다.")
}