package com.example.forsubmit.domain.room.exceptions

import com.example.forsubmit.global.exception.GlobalException

class CannotDeleteRoomException private constructor() : GlobalException(RoomErrorCode.CANNOT_DELETE_ROOM) {
    companion object {
        @JvmField
        val EXCEPTION = CannotDeleteRoomException()
    }
}