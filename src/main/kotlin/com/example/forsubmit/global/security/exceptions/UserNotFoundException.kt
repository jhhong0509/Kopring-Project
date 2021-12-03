package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.GlobalException

class UserNotFoundException private constructor() : GlobalException(AuthErrorCode.USER_NOT_FOUND){
    companion object {
        @JvmField
        val EXCEPTION = UserNotFoundException()
    }
}