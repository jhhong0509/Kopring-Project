package com.example.forsubmit.domain.room.exceptions

import com.example.forsubmit.global.exception.GlobalException

class CannotUpdateRoomException private constructor() : GlobalException(RoomErrorCode.CANNOT_UPDATE_ROOM) {
    companion object {
        @JvmField
        val EXCEPTION = CannotUpdateRoomException()
    }
}