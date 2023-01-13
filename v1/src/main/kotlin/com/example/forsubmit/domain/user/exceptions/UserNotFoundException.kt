package com.example.forsubmit.domain.user.exceptions

import com.example.forsubmit.global.exception.GlobalException

class UserNotFoundException private constructor() : GlobalException(UserErrorCode.USER_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = UserNotFoundException()
    }
}