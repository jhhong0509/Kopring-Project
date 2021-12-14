package com.example.forsubmit.domain.room.exceptions

import com.example.forsubmit.global.exception.GlobalException

class RoomNotFoundException private constructor() : GlobalException(RoomErrorCode.ROOM_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = RoomNotFoundException()
    }
}